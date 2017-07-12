package com.future.awaker.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.future.awaker.R;
import com.future.awaker.base.ViewModelHolder;
import com.future.awaker.data.source.NewRepository;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_VIEW_MODEL_TAG = "MAIN_VIEW_MODEL_TAG";

    private MainFragment mainFragment;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_fl, mainFragment, MainFragment.class.getSimpleName())
                    .commit();
        }

        mainViewModel = findOrCreateViewModel();
        mainFragment.setMainViewModel(mainViewModel);
    }

    @Override
    protected void onDestroy() {
        NewRepository.destroyInstance();

        super.onDestroy();
    }

    private MainViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MainViewModel> retainedViewModel = (ViewModelHolder<MainViewModel>) getSupportFragmentManager()
                .findFragmentByTag(MAIN_VIEW_MODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            return retainedViewModel.getViewModel();
        } else {
            MainViewModel mainViewModel = new MainViewModel(NewRepository.get());
            ViewModelHolder viewModelHolder = ViewModelHolder.createContainer(mainViewModel);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(viewModelHolder, MAIN_VIEW_MODEL_TAG);
            transaction.commit();
            return mainViewModel;
        }
    }
}
