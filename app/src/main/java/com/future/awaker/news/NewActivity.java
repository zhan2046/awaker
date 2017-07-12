package com.future.awaker.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.future.awaker.R;
import com.future.awaker.base.ViewModelHolder;
import com.future.awaker.data.source.NewRepository;

public class NewActivity extends AppCompatActivity {

    public static final String MAIN_VIEW_MODEL_TAG = "MAIN_VIEW_MODEL_TAG";

    private NewFragment newFragment;
    private NewViewModel newViewModel;

    public static void launch(Context context) {
        Intent intent = new Intent(context, NewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        if (newFragment == null) {
            newFragment = NewFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_fl, newFragment, NewFragment.class.getSimpleName())
                    .commit();
        }

        newViewModel = findOrCreateViewModel();
        newFragment.setNewViewModel(newViewModel);
    }

    @Override
    protected void onDestroy() {
        NewRepository.destroyInstance();

        super.onDestroy();
    }

    private NewViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<NewViewModel> retainedViewModel = (ViewModelHolder<NewViewModel>) getSupportFragmentManager()
                .findFragmentByTag(MAIN_VIEW_MODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            return retainedViewModel.getViewModel();
        } else {
            NewViewModel newViewModel = new NewViewModel(NewRepository.get());
            ViewModelHolder viewModelHolder = ViewModelHolder.createContainer(newViewModel);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(viewModelHolder, MAIN_VIEW_MODEL_TAG);
            transaction.commit();
            return newViewModel;
        }
    }
}
