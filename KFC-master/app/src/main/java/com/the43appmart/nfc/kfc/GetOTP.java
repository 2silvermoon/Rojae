package com.the43appmart.nfc.kfc;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aniruddha on 05/11/2017.
 */

public class GetOTP extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://43appmart.com/NFCPay/NfcOtp.php";
    private Map<String, String> params;

    public GetOTP(String number, String messege, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("number",number);
        params.put("messege",messege);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
