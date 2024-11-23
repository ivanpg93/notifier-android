package es.ivanpg93.notifier.messages;

import android.content.Context;
import android.widget.Toast;

import es.ivanpg93.notifier.errormanagement.Message;

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
