package com.the43appmart.nfc.kfc.DustBin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.the43appmart.nfc.kfc.LoadingActivity;
import com.the43appmart.nfc.kfc.R;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID = "";
    private String userPassword = "";
    private String userGender = "";
    private String userMajor = "";
    private String userEmail = "";
    private AlertDialog dialog;
    private boolean validate = false;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = "";
        userPassword = "";
        userGender = "";
        userMajor = "";
        userEmail = "";

        setContentView(R.layout.activity_register);

        // 저장된 회원의 정보 clear
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

        spinner = findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final EditText emailText = findViewById(R.id.emailText);

        RadioGroup genderGroup = findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) (findViewById(genderGroupID))).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = findViewById(i);
                userGender = genderButton.getText().toString();
            }
        });

        final Button havAccount = findViewById(R.id.HavAccount);
        final Button validateButton = findViewById(R.id.validateButton);

        havAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if (validate) {
                    return;
                }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("학번은 빈칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if(!CheckNumber(userID)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("학번은 숫자으로만 이루어집니다").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                // 저장된 회원의 정보 clear
                pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();

                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (userID.equals("") || userPassword.equals("") || userMajor.equals("") ||
                        userEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈간 없이 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent i = new Intent(RegisterActivity.this, LoadingActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 실패했습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    @Override
    protected void onStop(){
     super.onStop();
     if(dialog != null){
         dialog.dismiss();
         dialog = null;
     }
    }

    public boolean CheckNumber(String str){
        char check;

        if(str.equals(""))
        {
            //문자열이 공백인지 확인
            return false;
        }

        for(int i = 0; i<str.length(); i++){
            check = str.charAt(i);
            if( check < 48 || check > 58)
            {
                //해당 char값이 숫자가 아닐 경우
                return false;
            }

        }
        return true;
    }
}