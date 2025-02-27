package it.auties.whatsapp.model.sync;

import it.auties.protobuf.base.ProtobufMessage;
import it.auties.protobuf.base.ProtobufName;
import it.auties.protobuf.base.ProtobufProperty;
import it.auties.whatsapp.model.info.MessageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

import static it.auties.protobuf.base.ProtobufType.MESSAGE;
import static it.auties.protobuf.base.ProtobufType.UINT64;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
@Accessors(fluent = true)
@ProtobufName("HistorySyncMsg")
public class HistorySyncMessage implements ProtobufMessage {
    @ProtobufProperty(index = 1, type = MESSAGE, implementation = MessageInfo.class)
    private MessageInfo message;

    @ProtobufProperty(index = 2, type = UINT64)
    private long messageOrderId;
}