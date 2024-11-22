package es.app2u.viewutils.errormanagement.v2.messages;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;

//import es.app2u.dialogs.Dialog;
import es.app2u.viewutils.errormanagement.v2.Message;

public class ProgressBarDialog implements Message {

    private Msg message;
    private int progress;
    private final int maxProgress;
    @Nullable private final DialogInterface.OnClickListener listener;

    public ProgressBarDialog(Msg message, int progress, int maxProgress) {
        this(message, progress, maxProgress, null);
    }

    public ProgressBarDialog(Msg message, int progress, int maxProgress,
                                 DialogInterface.OnClickListener listener) {
        this.message = message;
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.listener = listener;
    }

    public void addProgress(int progress) {
        this.progress += progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void updateMessage(Msg message) {
        this.message = message;
    }

    @Override
    public void show(Context context) {
        //Dialog.showProgressBarDialog(context, message.toString(context), progress, maxProgress, listener);
    }

    @Override
    public void cancel(Context context) {
        //Dialog.cancelProgressBarDialog();
    }

    @Override
    public Msg getMessage() {
        return message;
    }

}

