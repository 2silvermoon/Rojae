package com.the43appmart.nfc.kfc.DustBin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rojae on 2019-09.17
 * Validate.php에 id를 보냄으로써
 * 회원가입이 가능한지 검사를 한다.
 */

public class ValidateRequest extends StringRequest {
    final static private String URL = "https://rojae.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
