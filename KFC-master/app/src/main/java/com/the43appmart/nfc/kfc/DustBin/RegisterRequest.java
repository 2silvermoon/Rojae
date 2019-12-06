package com.the43appmart.nfc.kfc.DustBin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rojae on 2019-09.17
 * Register.php에 접속하여 등록할 회원정보를 보내는 소스코드이다
 */

public class RegisterRequest extends StringRequest {
   final static private String URL =  "http://119.205.149.240:8081/SignUp";

   // final static private String URL = "https://rojae.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userMajor, String userEmail, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userID);
        parameters.put("userAuth","");
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userMajor", userMajor);
        parameters.put("userEmail", userEmail);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}