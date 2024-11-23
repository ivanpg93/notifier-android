package es.app2u.viewutils.errormanagement.v2.messages;

import android.content.Context;

import androidx.annotation.NonNull;

import dialogs.Dialog;
import es.app2u.viewutils.errormanagement.v2.Message;

public class ProgressMessage implements Message {

    private final @NonNull String message;

    public ProgressMessage(@NonNull String message) {
        this.message = message;
    }

    @Override
    public void show(Context context) {
        Dialog.showProgressDialog(context, message);
    }

    @Override
    public void cancel(Context context) {
        Dialog.cancelProgressDialog();
    }

    @Override
    public Msg getMessage() {
        return new Msg(message);
    }

}
