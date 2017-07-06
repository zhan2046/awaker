package com.love.awaker.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.love.awaker.R;

public class MainActivity extends AppCompatActivity {

  private MainFragment mainFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (mainFragment == null) {
      mainFragment = MainFragment.newInstance();
      getSupportFragmentManager().beginTransaction()
          .add(R.id.content_fl, mainFragment, MainFragment.class.getSimpleName())
          .commit();
    }
  }
}
