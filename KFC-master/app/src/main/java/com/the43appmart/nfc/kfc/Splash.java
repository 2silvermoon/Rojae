package com.the43appmart.nfc.kfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.the43appmart.nfc.kfc.DustBin.LoginActivity;
import com.the43appmart.nfc.kfc.DustBin.MainActivity;

public class Splash extends AppCompatActivity {

    private SharedPreferences saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        getSupportActionBar().hide();
        InitView();
    }

    private void InitView() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Init();
                                      }
                                  }, 4000
        );
    }

    private void Init() {
        saveUser = PreferenceManager.getDefaultSharedPreferences(this);
        String CheckUser = saveUser.getString("strUserName", "");
        if (!CheckUser.isEmpty( )) {
            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(Splash.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
