package com.startnfc.blog.kfc_reader;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    static private String URL = "http://119.205.149.240:8081/NdefExe";

    private Map<String, String> parameters;

    public LoginRequest(String userData, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userData", userData);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}