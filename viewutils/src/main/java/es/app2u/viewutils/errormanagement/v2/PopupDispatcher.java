package es.app2u.viewutils.errormanagement.v2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * The `PopupDispatcher` class acts as a manager for displaying and handling popup notifications
 * using the `PopupNotifier` utility. It's responsible for showing messages and performing actions
 * based on the received notifications.
 *
 * The constructor takes the application context, a `LifecycleOwner`, and a `PopupNotifier` instance
 * to observe the notifications. It listens to the `messageLiveData` and `loadingLiveData` from the
 * `PopupNotifier` and responds by showing messages and performing actions accordingly.
 *
 * Usage:
 * 1. Create an instance of `PopupDispatcher` by passing the application context, `LifecycleOwner`,
 *    and a `PopupNotifier` instance.
 * 2. Configure the `PopupDispatcher` with a custom configuration using `configure`.
 * 3. The `showMessage` method receives a `Message` object and displays it using the context.
 *    It also performs actions based on the message's action type (LOGOUT, BACK, NONE).
 *
 * The nested class `Configuration` defines the configuration settings for the `PopupDispatcher`.
 * It provides an interface `Navigation` with methods `logout` and `goBack` that are used to define
 * navigation actions. The configuration holds an instance of the `Navigation` interface for
 * performing logout and navigation actions.
 *
 * @see PopupNotifier
 */
public class PopupDispatcher {

    private final Context context;

    public PopupDispatcher(@NonNull Context context, @NonNull LifecycleOwner lifecycleOwner,
                           PopupNotifier notifier) {
        this.context = context;
        notifier.getShowMessageLiveData().observe(lifecycleOwner, this::showMessage);
        notifier.getCancelMessageLiveData().observe(lifecycleOwner, this::cancelMessage);
    }

    public void showMessage(Message message) {
        message.show(context);
    }

    public void cancelMessage(Message message) {
        message.cancel(context);
    }

}
