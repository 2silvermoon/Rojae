package com.the43appmart.nfc.kfc.DustBin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    // tomkishou.iptime.org
    static private String URL = "http://119.205.149.240:8081/SignIn";

    //    final static private String URL = "https://rojae.cafe24.com/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}