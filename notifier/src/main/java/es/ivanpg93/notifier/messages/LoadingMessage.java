package es.ivanpg93.notifier.messages;

import android.content.Context;

import dialogs.Dialog;
import es.ivanpg93.notifier.errormanagement.Message;

public class LoadingMessage implements Message {

    @Override
    public void show(Context context) {
        Dialog.showLoadingDialog(context);
    }

    @Override
    public void cancel(Context context) {
        Dialog.cancelLoadingDialog();
    }

    @Override
    public Msg getMessage() {
        return new Msg("");
    }

}
