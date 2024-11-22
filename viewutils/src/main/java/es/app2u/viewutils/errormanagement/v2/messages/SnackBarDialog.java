package es.app2u.viewutils.errormanagement.v2.messages;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import es.app2u.viewutils.errormanagement.v2.Message;

public class SnackBarDialog implements Message {

    private final int duration;
    private final Msg message;
    private Snackbar snackbar;

    public SnackBarDialog(Msg message, int duration) {
        this.duration = duration;
        this.message = message;
    }

    public SnackBarDialog(Msg message) {
       this(message, Toast.LENGTH_SHORT);
    }

    @Override
    public void show(Context context) {
        View view = ((Activity) context).findViewById(android.R.id.content);
        this.snackbar = Snackbar.make(view, message.toString(context), duration);
        snackbar.show();
    }

    @Override
    public void cancel(Context context) {
        if(snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    public Msg getMessage() {
        return message;
    }

}
