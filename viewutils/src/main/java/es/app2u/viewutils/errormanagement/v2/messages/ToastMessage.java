package es.app2u.viewutils.errormanagement.v2.messages;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import es.app2u.viewutils.errormanagement.v2.Message;

public class ToastMessage implements Message {

    private final int duration;
    private final Msg message;
    private Toast toast;

    public ToastMessage(Msg message) {
        this(message, Toast.LENGTH_SHORT);
    }

    public ToastMessage(Msg message, int duration) {
        this.message = message;
        this.duration = duration;
    }

    @Override
    public void show(Context context) {
       this.toast = Toast.makeText(context, message.toString(context), duration);
         toast.show();
    }

    @Override
    public void cancel(Context context) {
        if (toast != null) {
            toast.cancel();
        }
    }

    @Override
    public Msg getMessage() {
        return message;
    }

}
