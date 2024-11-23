package es.ivanpg93.demo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import dialogs.DialogBuilder;
import es.ivanpg93.notifier.errormanagement.ErrorsManager;
import es.ivanpg93.notifier.errormanagement.PopupNotifier;
import es.ivanpg93.notifier.messages.DialogMessage;
import es.ivanpg93.notifier.messages.ErrorDialogMessage;
import es.ivanpg93.notifier.messages.Msg;
import es.ivanpg93.notifier.messages.ProgressBarDialog;
import es.ivanpg93.notifier.messages.SnackBarDialog;
import es.ivanpg93.notifier.messages.SucceedDialogMessage;
import es.ivanpg93.notifier.messages.ToastMessage;
import io.reactivex.observers.DisposableCompletableObserver;

public class MainViewModel extends ViewModel {

    protected final PopupNotifier notifier = new PopupNotifier();

    public void showActionDialog() {
        DialogBuilder builder = new DialogBuilder();
        builder.title("Hola!")
                .message("Com estas? \n -- mirar resposta al Log TEST --")
                .btnOK("Be")
                .btnNOK("Regular")
                .listenerOK(((dialog, which) -> clickBe()))
                .listenerNOK(((dialog, which) -> clickRegular()));

        notifier.notifyMessage(new DialogMessage(builder));
    }

    public void showErrorDialog() {
        notifier.notifyMessage(new ErrorDialogMessage(new Msg("Error Dialog!¿?")));
    }

    public void showLoading() {
        notifier.startLoading();

        // After 2 seconds, cancel it
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(notifier::cancelLoading, 2000);
    }

    public void showProgress(@NonNull String message) {
        notifier.startProgress(message);

        // After 2 seconds, cancel it
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(notifier::cancelProgress, 2000);
    }

    public void showProgressBar() {
        ProgressBarDialog dialog = new ProgressBarDialog(new Msg("message"), 0, 100);
        notifier.notifyMessage(dialog);

        // After 2 seconds, add progress after 4 cancel it
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            dialog.addProgress(40);
            notifier.notifyMessage(dialog);
        }, 2000);
        handler.postDelayed(() -> notifier.cancelMessage(dialog), 4000);
    }

    public void showToast(int value) {
        notifier.notifyMessage(new ToastMessage( new Msg(R.string.toast_example, value)));
    }

    public void showSnack() {
        notifier.notifyMessage(new SnackBarDialog( new Msg("message")));
    }

    public void showDialog() {
        notifier.notifyMessage(new ErrorDialogMessage("Algo ha salido mal..."));
    }

    public void showRequest(String code, String code2) {
        notifier.startLoading();
        new ExampleHandler().execute(code, code2).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                notifier.cancelLoading();
                notifier.notifyMessage(new SucceedDialogMessage("Tot be!"));
            }

            @Override
            public void onError(Throwable e) {
                notifier.cancelLoading();
                ErrorsManager.Parameters params = new ErrorsManager.Parameters();
                params.setErrorKeyMapper(errorMapper());
                notifier.notifyServerErrors(e, new ErrorsManager(params));
            }
        });
    }

    private void clickBe() {
        Log.d("TEST", "OK");
    }

    private void clickRegular() {
        Log.d("TEST", "REGULAR");
    }

    private Map<String, Msg> errorMapper() {
        Map<String, Msg> errorsMapper = new HashMap<>();
        errorsMapper.put("picking_not_found", new Msg("El picking no existe"));
        return errorsMapper;
    }

}
