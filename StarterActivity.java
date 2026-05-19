package com.hypers.hm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.hypers.hm.starter.StarterViewModel;

public class StarterActivity extends Activity implements LifecycleOwner, ViewModelStoreOwner {

    // FIX: implement LifecycleOwner sendiri karena Activity biasa tidak punya ini
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private final ViewModelStore viewModelStore = new ViewModelStore();

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }

    public static final String EXTRA_IS_ROOT = "extra_is_root";
    public static final String EXTRA_HOST    = "extra_host";
    public static final String EXTRA_PORT    = "extra_port";

    private TextView textOutput;
    private ScrollView scrollView;
    private StarterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        setContentView(R.layout.starter_activity);

        textOutput = findViewById(R.id.textview_terminal);
        scrollView = findViewById(R.id.scroll_view);

        boolean isRoot = getIntent().getBooleanExtra(EXTRA_IS_ROOT, false);
        String host    = getIntent().getStringExtra(EXTRA_HOST);
        int port       = getIntent().getIntExtra(EXTRA_PORT, -1);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(StarterViewModel.class);

        // FIX: cast dihapus — sekarang this sudah benar-benar LifecycleOwner
        viewModel.output.observe(this, pair -> {
            if (pair != null) {
                textOutput.setText(pair.first);
                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            }
        });

        viewModel.start(isRoot, host, port);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onPause() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        viewModelStore.clear();
        super.onDestroy();
    }
}
