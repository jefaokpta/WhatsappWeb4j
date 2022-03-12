package it.auties.whatsapp.binary;

import it.auties.bytes.Bytes;
import it.auties.whatsapp.controller.WhatsappKeys;
import it.auties.whatsapp.crypto.AesGmc;
import it.auties.whatsapp.model.request.Node;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Value
@Accessors(fluent = true)
public class BinaryMessage {
    private static final BinaryDecoder DECODER = new BinaryDecoder();

    @NonNull
    Bytes raw;

    @NonNull
    LinkedList<Bytes> decoded;

    public BinaryMessage(@NonNull Bytes raw) {
        this.raw = raw;
        var decoded = new LinkedList<Bytes>();
        while (raw.readableBytes() >= 3) {
            var size = decodeLength(raw);
            if(size < 0){
                continue;
            }

            decoded.add(raw.readBuffer(size));
        }

        this.decoded = decoded;
    }

    private int decodeLength(Bytes buffer) {
        return (buffer.readByte() << 16) | buffer.readUnsignedShort();
    }

    public BinaryMessage(byte @NonNull [] array) {
        this(Bytes.of(array));
    }

    public List<Node> toNodes(@NonNull WhatsappKeys keys) {
        return decoded.stream()
                .map(encoded -> toNode(encoded, keys))
                .toList();
    }

    private Node toNode(Bytes encoded, WhatsappKeys keys) {
        var plainText = AesGmc.with(keys.readKey(), keys.readCounter(true), false)
                .process(encoded.toByteArray());
        return DECODER.decode(plainText);
    }
}
