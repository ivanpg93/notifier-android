package es.app2u.viewutils.errormanagement.v2;

import android.content.Context;

import es.app2u.viewutils.errormanagement.v2.messages.Msg;

public interface Message {
    void show(Context context);
    void cancel(Context context);
    Msg getMessage();
}
