package it.auties.whatsapp.api;

import com.google.zxing.common.BitMatrix;
import it.auties.whatsapp.manager.WhatsappStore;
import it.auties.whatsapp.protobuf.action.Action;
import it.auties.whatsapp.protobuf.chat.Chat;
import it.auties.whatsapp.protobuf.chat.GroupAction;
import it.auties.whatsapp.protobuf.chat.GroupPolicy;
import it.auties.whatsapp.protobuf.chat.GroupSetting;
import it.auties.whatsapp.protobuf.contact.Contact;
import it.auties.whatsapp.protobuf.info.MessageInfo;
import it.auties.whatsapp.protobuf.setting.Setting;
import it.auties.whatsapp.socket.Socket;
import it.auties.whatsapp.util.QrHandler;
import lombok.NonNull;

import java.util.List;

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
    default QrHandler onQRCode(@NonNull BitMatrix qr){
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
     * Called when {@link Socket} successfully disconnects from WhatsappWeb's WebSocket.
     * When this event is called, any data, including chats and contact, is guaranteed to not be available anymore.
     */
    default void onDisconnected() {
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
    default void onAction(@NonNull Action action) {

    }

    /**
     * Called when {@link Socket} receives a setting change from Whatsapp.
     *
     * @param setting the setting that was toggled
     */
    default void onSetting(@NonNull Setting setting) {

    }

    /**
     * Called when {@link Socket} receives new features from Whatsapp.
     *
     * @param features the non-null features that were sent
     */
    default void onFeatures(@NonNull List<String> features) {

    }


    /**
     * Called when new information regarding this session is available.
     * Only the new data will be available in {@code info} as it's a partial object.
     *
     * @param info the partial object used to represent the new data available for this session
     */
    default void onInformationUpdate(@NonNull Object info) {
    }

    /**
     * Called when {@link Socket} receives a plain text list.
     * This data is usually not very useful, but it may be necessary for particular use cases.
     *
     * @param response the list received as plain text by {@link Socket}
     */
    default void onListResponse(@NonNull List<Object> response) {
    }

    /**
     * Called when {@link Socket} receives all the contacts from WhatsappWeb's WebSocket.
     * To access this data use {@link WhatsappStore#contacts()}.
     */
    default void onContacts() {
    }

    /**
     * Called when {@link Socket} receives an update regarding a contact
     *
     * @param contact the updated contact
     */
    default void onContactUpdate(@NonNull Contact contact) {
    }

    /**
     * Called when {@link Socket} receives a new contact
     *
     * @param contact the new contact
     */
    default void onNewContact(@NonNull Contact contact) {
    }

    /**
     * Called when {@link Socket} receives an update regarding the presence of a contact.
     * If {@code chat} is a conversation with {@code contact}, the new presence is available by calling {@link Contact#lastKnownPresence()}.
     * Otherwise, it should be queried using {@link Chat#presences()}.
     *
     * @param chat    the chat that this update regards
     * @param contact the contact that this update regards
     */
    default void onContactPresenceUpdate(@NonNull Chat chat, @NonNull Contact contact) {
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
    default void onChatRecentMessages(@NonNull Chat chat) {
    }

    /**
     * Called when {@link Socket} receives a new chat
     *
     * @param chat the new chat
     */
    default void onNewChat(@NonNull Chat chat) {
    }

    /**
     * Called when a chat is archived
     *
     * @param chat the chat that was archived
     */
    default void onChatArchived(@NonNull Chat chat) {
    }

    /**
     * Called when a chat is unarchived
     *
     * @param chat the chat that was unarchived
     */
    default void onChatUnarchived(@NonNull Chat chat) {
    }

    /**
     * Called when a chat's mute changes
     *
     * @param chat the chat whose mute changed
     */
    default void onChatMuteChange(@NonNull Chat chat) {
    }

    /**
     * Called when a chat's read status changes
     *
     * @param chat the chat whose read status changed
     */
    default void onChatReadStatusChange(@NonNull Chat chat) {
    }

    /**
     * Called when a chat's ephemeral status changes
     *
     * @param chat the chat whose ephemeral status changed
     */
    default void onChatEphemeralStatusChange(@NonNull Chat chat) {
    }

    /**
     * Called when a group's subject changes
     *
     * @param group the group whose subject changed
     */
    default void onGroupSubjectChange(@NonNull Chat group) {
    }

    /**
     * Called when a group's description changes
     *
     * @param group         the group whose description changed
     * @param description   the new description
     * @param descriptionId the id of the new description
     */
    default void onGroupDescriptionChange(@NonNull Chat group, @NonNull String description, @NonNull String descriptionId) {
    }

    /**
     * Called when a group's settings change
     *
     * @param group   the group whose settings changed
     * @param setting the setting that changed
     * @param policy  the new policy that was set
     */
    default void onGroupSettingsChange(@NonNull Chat group, @NonNull GroupSetting setting, @NonNull GroupPolicy policy) {
    }

    /**
     * Called when an action is executed on a group's participant
     *
     * @param group       the group where the action was executed
     * @param participant the target of the action that was executed
     * @param action      the type of the action that was executed
     */
    default void onGroupAction(@NonNull Chat group, @NonNull Contact participant, @NonNull GroupAction action) {
    }

    /**
     * Called when a new message is received in a chat
     *
     * @param chat    the chat where the message was sent
     * @param message the message that was sent
     */
    default void onNewMessage(@NonNull Chat chat, @NonNull MessageInfo message) {
    }

    /**
     * Called when the read status of a message changes.
     * If {@code chat} is a conversation with {@code contact}, the new read status can be considered valid for the message itself.
     * Otherwise, it should be considered valid only for {@code contact} without making assumptions about the status of the message for other participants of the group.
     *
     * @param chat    the chat where the message is
     * @param contact the contact that this update regards
     * @param message the message this update regards
     */
    default void onMessageReadStatusUpdate(@NonNull Chat chat, @NonNull Contact contact, @NonNull MessageInfo message) {
    }

    /**
     * Called when the metadata or content of a message is updated
     *
     * @param chat    the chat where the message is
     * @param message the message this update regards
     */
    default void onMessageUpdate(@NonNull Chat chat, @NonNull MessageInfo message) {
    }

    /**
     * Called when a message is deleted
     *
     * @param message  the message that was deleted
     * @param everyone whether this message was deleted by you only for yourself or whether the message was permanently removed
     */
    default void onMessageDeleted(@NonNull MessageInfo message, boolean everyone) {
    }

    /**
     * Called when a message is starred
     *
     * @param chat    the chat where the message is
     * @param message the message that was starred
     */
    default void onMessageStarred(@NonNull Chat chat, @NonNull MessageInfo message) {
    }

    /**
     * Called when a message is unstarred
     *
     * @param chat    the chat where the message is
     * @param message the message that was unstarred
     */
    default void onMessageUnstarred(@NonNull Chat chat, @NonNull MessageInfo message) {
    }

    /**
     * Called when the global read status of a message changes.
     * This status can be accessed calling {@link MessageInfo#globalStatus()}.
     * If {@code chat} is a conversation, {@link MessageInfo#globalStatus()} is equal to the one stored in {@link MessageInfo#individualReadStatus()} for the corresponding contact.
     * Otherwise, it is guaranteed that each value stored in {@link MessageInfo#individualReadStatus()} for each participant of the chat is equal or higher hierarchically then {@link MessageInfo#globalStatus()}.
     *
     * @param chat    the chat where the message is
     * @param message the message that was unstarred
     */
    default void onMessageGlobalReadStatusUpdate(@NonNull Chat chat, @NonNull MessageInfo message) {
    }

    /**
     * Called when an updated blocklist is received.
     * This method is called both when a connection is established with WhatsappWeb and when a contact is added or removed from the blocklist.
     *
     * @param blocklist the updated blocklist
     */
    default void onBlocklistUpdate(@NonNull Object blocklist) {
    }

    /**
     * Called when an updated list of properties is received.
     * This method is called both when a connection is established with WhatsappWeb and when new props are available.
     * In the latter case though, this object should be considered as partial and is guaranteed to contain only updated entries.
     *
     * @param props the updated list of properties
     */
    default void onPropsUpdate(@NonNull Object props) {
    }

    /**
     * Called when an updated object describing the status of the phone's associated with this session battery status changes
     *
     * @param battery the new battery status
     */
    default void onPhoneBatteryStatusUpdate(@NonNull Object battery) {
    }
}
