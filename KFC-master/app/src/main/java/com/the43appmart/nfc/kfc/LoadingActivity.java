package com.the43appmart.nfc.kfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.the43appmart.nfc.kfc.DustBin.LoginActivity;
import com.the43appmart.nfc.kfc.DustBin.MainActivity;

public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences savedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_loading );
        getSupportActionBar().hide();
        InitView();
    }

    private void InitView() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Init();
                                      }
                                  }, 2000
        );
    }

    private void Init() {
        // 2가지 경우를 검사
        // 회원가입을 하여 로딩화면이 나오는가
        // 로그인을 하여 로딩화면이 나오는가

        // 데이터에 id가 있는지 검사
        savedUser = PreferenceManager.getDefaultSharedPreferences( getApplicationContext());
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String Name = pref.getString("strID", "");

        // 없는 경우는 Login 페이지
        if(Name.equals("")){
            AlertDialog dialog;
            Intent i = new Intent(LoadingActivity.this, LoginActivity.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.this);
            dialog = builder.setMessage("회원 등록에 성공했습니다.").setPositiveButton("확인", null).create();
            dialog.show();

            SystemClock.sleep(3000);

            startActivity(i);
            finish();
        }

        // 있는 경우는 Main 페이지
        else{
            Intent i = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
