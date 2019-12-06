package com.the43appmart.nfc.kfc;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.the43appmart.nfc.kfc.Domain.DOMAIN_URL;

/**
 * Created by Aniruddha on 19/12/2017.
 */

public class AddAmountRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL =DOMAIN_URL+"/NFCPay/UpdateBalance.php";
    private Map<String, String> params;

    public AddAmountRequest(String updatedBalance, String id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("updatedBalance",updatedBalance);
        params.put("id",id);
    }

   @Override
    public Map<String, String> getParams() {
        return params;
    }
}
