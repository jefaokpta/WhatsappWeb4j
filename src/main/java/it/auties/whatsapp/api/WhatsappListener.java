package it.auties.whatsapp.api;

import com.google.zxing.common.BitMatrix;
import it.auties.whatsapp.manager.WhatsappStore;
import it.auties.whatsapp.protobuf.action.Action;
import it.auties.whatsapp.protobuf.chat.Chat;
import it.auties.whatsapp.protobuf.contact.Contact;
import it.auties.whatsapp.protobuf.contact.ContactStatus;
import it.auties.whatsapp.protobuf.info.MessageInfo;
import it.auties.whatsapp.protobuf.message.model.MessageStatus;
import it.auties.whatsapp.protobuf.setting.Setting;
import it.auties.whatsapp.socket.Socket;
import it.auties.whatsapp.util.QrHandler;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

/**
 * This interface can be used to listen for events fired when new information is sent by WhatsappWeb's socket.
 * A WhatsappListener can be registered manually using {@link Whatsapp#registerListener(WhatsappListener)}.
 * Otherwise, it can be registered by annotating it with the {@link RegisterListener} annotation.
 */
@SuppressWarnings("unused")
public interface WhatsappListener {
    /**
     * Called when {@link Socket} successfully establishes a connection with new secrets.
     * By default, the QR code is printed to the console.
     * If no enum supports your intended functionality, define the logic inside this method and return {@link QrHandler#CUSTOM}.
     *
     * @param qr the generator code to consume
     * @return a non-null handler to process the qr code
     */
    @NonNull
    default QrHandler onQRCode(BitMatrix qr){
        return QrHandler.TERMINAL;
    }

    /**
     * Called when {@link Socket} successfully establishes a connection and logs in into an account.
     * When this event is called, any data, including chats and contact, is not guaranteed to be already in memory.
     * Instead, {@link WhatsappListener#onChats()} and {@link WhatsappListener#onContacts()} should be used.
     *
     */
    default void onLoggedIn() {
    }

    /**
     * Called when an updated list of properties is received.
     * This method is called both when a connection is established with WhatsappWeb and when new props are available.
     * In the latter case though, this object should be considered as partial and is guaranteed to contain only updated entries.
     *
     * @param props the updated list of properties
     */
    default void onProps(Map<String, String> props) {
    }

    /**
     * Called when {@link Socket} successfully disconnects from WhatsappWeb's WebSocket
     *
     * @param reconnect whether the connection is going to be re-established
     */
    default void onDisconnected(boolean reconnect) {
    }

    /**
     * Called when {@link Socket} receives a failure from Whatsapp.
     * This usually indicates that the pair device has been disconnect forcefully.
     *
     * @param statusCode the status code for the failure
     * @param reason the reason for the failure, might be null
     * @return whether the keys should be deleted and a new session should be opened
     */
    default boolean onFailure(long statusCode, String reason) {
        return true;
    }

    /**
     * Called when {@link Socket} receives an action from Whatsapp.
     *
     * @param action the action that was executed
     */
    default void onAction(Action action) {

    }

    /**
     * Called when {@link Socket} receives a setting change from Whatsapp.
     *
     * @param setting the setting that was toggled
     */
    default void onSetting(Setting setting) {

    }

    /**
     * Called when {@link Socket} receives new features from Whatsapp.
     *
     * @param features the non-null features that were sent
     */
    default void onFeatures(List<String> features) {

    }

    /**
     * Called when {@link Socket} receives all the contacts from WhatsappWeb's WebSocket.
     * To access this data use {@link WhatsappStore#contacts()}.
     */
    default void onContacts() {
    }

    /**
     * Called when {@link Socket} receives a new contact
     *
     * @param contact the new contact
     */
    default void onNewContact(Contact contact) {
    }

    /**
     * Called when {@link Socket} receives an update regarding the presence of a contact
     *
     * @param chat    the chat that this update regards
     * @param contact the contact that this update regards
     * @param status  the new status of the contact
     */
    default void onContactPresence(Chat chat, Contact contact, ContactStatus status) {
    }

    /**
     * Called when {@link Socket} receives all the chats from WhatsappWeb's WebSocket.
     * When this event is fired, it is guaranteed that all metadata excluding messages will be present.
     * To access this data use {@link WhatsappStore#chats()}.
     * If you also need the messages to be loaded, please refer to {@link WhatsappListener#onChatRecentMessages(Chat)}.
     */
    default void onChats() {
    }

    /**
     * Called when {@link Socket} receives the recent message for a chat already in memory.
     * When this event is fired, it is guaranteed that all metadata excluding messages will be present.
     */
    default void onChatRecentMessages(Chat chat) {
    }

    /**
     * Called when {@link Socket} receives a new chat
     *
     * @param chat the new chat
     */
    default void onNewChat(Chat chat) {
    }

    /**
     * Called when a new message is received in a chat
     *
     * @param message the message that was sent
     */
    default void onNewMessage(MessageInfo message) {
    }

    /**
     * Called when a message is deleted
     *
     * @param message  the message that was deleted
     * @param everyone whether this message was deleted by you only for yourself or whether the message was permanently removed
     */
    default void onMessageDeleted(MessageInfo message, boolean everyone) {
    }

    /**
     * Called when the status of a message changes inside a conversation.
     * This means that the status change can be considered global as the only other participant is the contact.
     * If you need updates regarding any chat, implement {@link WhatsappListener#onMessageStatus(Chat, Contact, MessageInfo, MessageStatus)}
     *
     * @param info   the message whose status changed
     * @param status the new status of the message
     */
    default void onMessageStatus(MessageInfo info, MessageStatus status) {
    }

    /**
     * Called when the status of a message changes inside any type of chat.
     * If {@code chat} is a conversation with {@code contact}, the new read status can be considered valid for the message itself(global status).
     * Otherwise, it should be considered valid only for {@code contact}.
     * If you only need updates regarding conversation, implement {@link WhatsappListener#onMessageStatus(MessageInfo, MessageStatus)}
     *
     * @param chat    the chat that triggered a status change
     * @param contact the contact that triggered a status change
     * @param message the message whose status changed
     * @param status  the new status of the message
     */
    default void onMessageStatus(Chat chat, Contact contact, MessageInfo message, MessageStatus status) {
    }

    /**
     * Called when {@link Socket} receives all the status updated from WhatsappWeb's Socket
     * To access this data use {@link WhatsappStore#status()}.
     * If you also need the messages to be loaded, please refer to {@link WhatsappListener#onChatRecentMessages(Chat)}.
     */
    default void onStatus() {
    }
}
