package es.app2u.viewutils.errormanagement.v2;

import androidx.annotation.NonNull;

import java.util.Map;

import es.app2u.viewutils.errormanagement.v2.messages.LoadingMessage;
import es.app2u.viewutils.errormanagement.v2.messages.Msg;
import es.app2u.viewutils.errormanagement.v2.messages.ProgressMessage;
import es.app2u.viewutils.singleliveevent.SingleLiveEvent;

/**
 * This class, `PopupNotifier`, serves as a utility to manage and display various types of popup notifications.
 * It encapsulates the functionality to show messages and loading indicators using LiveData.
 * The class provides methods to start and cancel loading indicators, notify server errors, and display custom messages.
 * It operates in conjunction with a configuration that can be set to customize its behavior.
 *
 * The class maintains two instances of `SingleLiveEvent`, `messageLiveData` and `loadingLiveData`,
 * to observe and propagate messages and loading events to the UI.
 *
 * Usage:
 * 1. Create an instance of `PopupNotifier` to manage popup notifications.
 * 2. Set a custom configuration using `setConfiguration` to customize error management behavior.
 * 3. Use `startLoading` and `cancelLoading` methods to manage loading indicators.
 * 4. Utilize `notifyServerErrors` to handle server-related errors, either using the default or custom `ErrorsManager`.
 * 5. Display custom messages using `notifyMessage`.
 * 6. Observe `messageLiveData` and `loadingLiveData` to receive updates on messages and loading events.
 *
 * The nested class `Configuration` encapsulates the configuration settings for `PopupNotifier`.
 * It provides an optional `ErrorsManager` that defines how server errors are managed.
 *
 * @see SingleLiveEvent
 * @see ErrorsManager
 */
public class PopupNotifier {

    //Live Data
    private final SingleLiveEvent<Message> showMessageLiveData = new SingleLiveEvent<>();
    private final SingleLiveEvent<Message> cancelMessageLiveData = new SingleLiveEvent<>();
    private static PopupNotifier.Configuration configuration = new PopupNotifier.Configuration();

    public static void setConfiguration(PopupNotifier.Configuration configuration) {
        PopupNotifier.configuration = configuration;
    }

    // Show a loading spinner
    public void startLoading() {
        showMessageLiveData.postValue(new LoadingMessage());
    }

    // Show a loading spinner with a message
    public void startProgress(@NonNull String message) {
        showMessageLiveData.postValue(new ProgressMessage(message));
    }

    // Cancel the loading spinner
    public void cancelLoading() {
        cancelMessageLiveData.postValue(new LoadingMessage());
    }

    public void cancelProgress() {
        cancelMessageLiveData.postValue(new ProgressMessage(""));
    }

    // Show a message. The message can be anthyng that implements the Message interface.
    // Most of the time it will be a Dialog, Toast or a SnackBar. Also it can be a custom message or a Spinner.
    public void notifyMessage(Message message) {
        showMessageLiveData.postValue(message);
    }

    // Cancel a message.
    public void cancelMessage(Message message) {
        cancelMessageLiveData.postValue(message);
    }

    // Show a message from an error. There is a predefined list of messages that will be show depending on the error.
    public void notifyServerErrors(Throwable e) {
        notifyServerErrors(e, configuration.getErrorsManager());
    }

    // If there is a error code that is not predefined, you can add it to the list of errors and show a custom message.
    public void notifyServerErrors(Throwable e, Map<String, Msg> map) {
        ErrorsManager manager = configuration.getErrorsManager();
        manager.addKeyErrors(map);
        notifyServerErrors(e, manager);
        manager.removeKeyErrors(map);
    }

    public void notifyServerErrors(Throwable e, String key, Msg msg) {
        ErrorsManager manager = configuration.getErrorsManager();
        manager.addKeyError(key, msg);
        notifyServerErrors(e, manager);
        manager.removeKeyError(key);
    }

    // You can also add your own ErrorMessage and customize the behaviour.
    public void notifyServerErrors(Throwable e, ErrorsManager errorsManager) {
        cancelLoading();
        Message message = errorsManager.manageServerErrors(e);
        notifyMessage(message);
    }

    //==============================================================================================
    //region Getters

    public SingleLiveEvent<Message> getShowMessageLiveData() {
        return showMessageLiveData;
    }

    public SingleLiveEvent<Message> getCancelMessageLiveData() {
        return cancelMessageLiveData;
    }

    //endregion
    public static class Configuration {
        private final ErrorsManager errorsManager;

        public Configuration() {
            this.errorsManager = new ErrorsManager();
        }

        public Configuration(ErrorsManager errorsManager) {
            this.errorsManager = errorsManager;
        }

        public ErrorsManager getErrorsManager() {
            return errorsManager;
        }
    }

}