package com.the43appmart.nfc.kfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SendSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_send_success );
        getSupportActionBar().setHomeButtonEnabled( true );
    }
}
