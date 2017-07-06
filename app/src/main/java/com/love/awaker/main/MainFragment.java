package com.love.awaker.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class MainFragment extends Fragment {

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
