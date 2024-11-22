package es.app2u.viewutils.errormanagement.v2.messages;

import androidx.annotation.StringRes;

import es.app2u.viewutils.R;

public class SucceedDialogMessage extends DialogMessage {

    public SucceedDialogMessage(String message) {
        this(new Msg(message));
    }

    public SucceedDialogMessage(@StringRes int message) {
        this(new Msg(message));
    }

    public SucceedDialogMessage(Msg message) {
        super(new Msg(R.string.dialog_success), message);
    }

}
