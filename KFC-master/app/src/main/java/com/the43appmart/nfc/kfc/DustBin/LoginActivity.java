package com.the43appmart.nfc.kfc.DustBin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.the43appmart.nfc.kfc.LoadingActivity;
import com.the43appmart.nfc.kfc.R;

import org.json.JSONObject;

import java.net.URLDecoder;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private SharedPreferences saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        String Name = pref.getString("strID", "");

        if(!Name.equals("")){
            // 로그인 기록이 있는 경우 -> 지문인식 다이얼로그
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("로그인 기록이 있습니다");
            builder.setMessage("지문인식으로 로그인을 하시겠습니까?");
            builder.setPositiveButton("좋아요", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(LoginActivity.this, LoginFingerprint.class);
                    startActivity(i);
                    finish();
                }
            });

            builder.setNegativeButton("싫어요",null);
            builder.show();

        }

        TextView registerButton = (TextView) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 저장된 회원의 정보 clear
                editor.clear();
                editor.apply();
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        TextView helpButton = (TextView) findViewById(R.id.helpButton);

        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
                dialog = builder.setMessage("로그인 이후에는 지문인식이 가능합니다\n계정이 없으시면 회원가입을 해주세요").setPositiveButton("확인",null).create();
                dialog.show();
            }
        });

        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                final ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollView);
                scrollView.setVisibility(View.GONE);

                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                Response.Listener<String> responseLister = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try{
                            String q = URLDecoder.decode(response, "UTF-8");
                            System.out.println(q);
                            JSONObject jsonRequest = new JSONObject(response);
                            boolean success = jsonRequest.getBoolean("success");
                            if(success){
                                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();

                                Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
                                LoginActivity.this.startActivity(intent);

                                String userGender = jsonRequest.getString("userGender");
                                String userMajor = jsonRequest.getString("userMajor");
                                String userEmail = jsonRequest.getString("userEmail");

                                editor.putString("strID", userID);
                                editor.putString("strGender", userGender);
                                editor.putString("strMajor", userMajor);
                                editor.putString("strEmail", userEmail);

                                editor.commit();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요.").setNegativeButton("다시 시도",null).create();
                                dialog.show();
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();;
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
