package es.ivanpg93.notifier.messages;

import androidx.annotation.StringRes;

import es.ivanpg93.notifier.R;

public class ErrorDialogMessage extends DialogMessage {

    public ErrorDialogMessage(String message) {
       this(new Msg(message));
    }

    public ErrorDialogMessage(@StringRes int message) {
        this(new Msg(message));
    }

    public ErrorDialogMessage(Msg message) {
        super(new Msg(R.string.dialog_error), message);
    }

}
