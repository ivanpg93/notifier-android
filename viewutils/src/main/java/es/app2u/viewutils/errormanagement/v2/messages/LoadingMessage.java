package es.app2u.viewutils.errormanagement.v2.messages;

import android.content.Context;

import dialogs.Dialog;
import es.app2u.viewutils.errormanagement.v2.Message;

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
