package es.ivanpg93.notifier.errormanagement;

import android.content.Context;

import es.ivanpg93.notifier.messages.Msg;

public interface Message {
    void show(Context context);
    void cancel(Context context);
    Msg getMessage();
}
