package es.ivanpg93.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.ivanpg93.notifier.errormanagement.PopupDispatcher;

public class MainActivity extends AppCompatActivity {

    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vm = new ViewModelProvider(this).get(MainViewModel.class);
        new PopupDispatcher(this, this, vm.notifier);

        findViewById(R.id.show_action_dialog).setOnClickListener(v -> vm.showActionDialog());
        findViewById(R.id.show_error_dialog).setOnClickListener(v -> vm.showErrorDialog());
        findViewById(R.id.show_dialog).setOnClickListener(v -> vm.showDialog());
        findViewById(R.id.show_toast).setOnClickListener(v -> vm.showToast(5));
        findViewById(R.id.show_snack_bar).setOnClickListener(v -> vm.showSnack());
        findViewById(R.id.show_request).setOnClickListener(v -> vm.showRequest("123434", "251"));
        findViewById(R.id.loading).setOnClickListener(v -> vm.showLoading());
        findViewById(R.id.progress).setOnClickListener(v -> vm.showProgress("Progress message"));
        findViewById(R.id.progressBar).setOnClickListener(v -> vm.showProgressBar());
    }

}