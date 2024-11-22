package es.app2u.viewutils.errormanagement.v2.messages;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class Msg {

    @StringRes @Nullable private Integer messageId;
    @Nullable private String message;
    @Nullable private Object[] args;

    public Msg(@StringRes @Nullable Integer messageId) {
        this.messageId = messageId;
    }

    public Msg(@StringRes @Nullable Integer messageId, @Nullable Object... args) {
        this(messageId);
        this.args = args;
    }

    public Msg(@Nullable String message) {
        this.message = message;
    }

    public Msg(@Nullable String message, @Nullable Object... args) {
        this(message);
        this.args = args;
    }

    public String toString(Context context) {

        if (messageId != null) {
            String str = context.getString(messageId);
            return String.format(str, args);
        }

        if (!TextUtils.isEmpty(message) && message != null) {
            return String.format(message, args);
        }

        return "";
    }

}
