package com.the43appmart.nfc.kfc;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.the43appmart.nfc.kfc.Domain.DOMAIN_URL;

/**
 * Created by Aniruddha on 22/03/2018.
 */

public class NFCReceiverDetailRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL =DOMAIN_URL+"";
    private Map<String, String> params;

    public NFCReceiverDetailRequest(String rec_email,  Response.Listener<String> listener) {
        super( Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("rec_email",rec_email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
