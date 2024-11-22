package es.app2u.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.app2u.viewutils.errormanagement.v2.PopupDispatcher;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        new PopupDispatcher(this, this, viewModel.notifier);

        findViewById(R.id.show_action_dialog).setOnClickListener(v -> viewModel.showActionDialog());
        findViewById(R.id.show_error_dialog).setOnClickListener(v -> viewModel.showErrorDialog());
        findViewById(R.id.show_dialog).setOnClickListener(v -> viewModel.showDialog());
        findViewById(R.id.show_toast).setOnClickListener(v -> viewModel.showToast(5));
        findViewById(R.id.show_snack_bar).setOnClickListener(v -> viewModel.showSnack());
        findViewById(R.id.show_request).setOnClickListener(v -> viewModel.showRequest("123434", "251"));
        findViewById(R.id.loading).setOnClickListener(v -> viewModel.showLoading());
        findViewById(R.id.progress).setOnClickListener(v -> viewModel.showProgress("Progress message"));
        findViewById(R.id.progressBar).setOnClickListener(v -> viewModel.showProgressBar());
    }
}