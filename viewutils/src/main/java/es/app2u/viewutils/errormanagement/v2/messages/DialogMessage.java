package es.app2u.viewutils.errormanagement.v2.messages;

import android.content.Context;

import androidx.annotation.Nullable;

import es.app2u.viewutils.R;
import es.app2u.viewutils.errormanagement.v2.Message;
import ivanpg93.dialogs.DialogBuilder;

public class DialogMessage implements Message {

    private DialogBuilder builder;
    @Nullable private Msg title;
    @Nullable private Msg message;

    public DialogMessage(@Nullable Msg title, @Nullable Msg message) {
        this.title = title;
        this.message = message;
    }

    public DialogMessage(DialogBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void show(Context context) {
        if (builder != null) {
            builder.build(context);
            return;
        }
        String titleStr = title == null ? "" : title.toString(context);
        String messageStr = message == null ? "" : message.toString(context);

        new DialogBuilder()
                .title(titleStr)
                .message(messageStr)
                .btnOK(context.getString(R.string.ok))
                .build(context);
    }

    @Override
    public void cancel(Context context) {
        // Todo: S'hauria de modificar la llibreria Dialogs per fer public el metode que permet cancelar dialegs. Com
        //  que de moment no necessitem aquesta funcionalitat, ja ho farem. També s'hauria de modificar el .build i
        //  posar-ho com en versions anteriors (1.1.0) on el .build retornava el dialog
    }

    @Override
    public Msg getMessage() {
        return message;
    }

}
