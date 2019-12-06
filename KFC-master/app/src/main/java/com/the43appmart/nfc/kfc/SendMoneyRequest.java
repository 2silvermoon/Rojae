package com.the43appmart.nfc.kfc;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.the43appmart.nfc.kfc.Domain.DOMAIN_URL;

/**
 * Created by Aniruddha on 22/03/2018.
 */

public class SendMoneyRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL =DOMAIN_URL+"/NFCPay/ReceiveMoney.php";
    private Map<String, String> params;

    public SendMoneyRequest(String sender_email, String rec_email, String rec_amt, Response.Listener<String> listener) {
        super( Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("sender_email",sender_email);
        params.put("rec_email",rec_email);
        params.put("rec_amt",rec_amt);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
