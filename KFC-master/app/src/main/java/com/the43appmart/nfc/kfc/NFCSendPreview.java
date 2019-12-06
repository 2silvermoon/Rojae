package com.the43appmart.nfc.kfc;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.the43appmart.nfc.kfc.DustBin.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NFCSendPreview extends AppCompatActivity {
    TextView txtName, txtEmail, txtContact, txtNFCid;
    EditText edtAmt, edtAnswer;
    Button btnSend;
    private String receiverEmail;
    CardView card_view, card_view_1;
    Spinner spnSelectQues;
    String[] Question1 = {"Select Security Question 1", "Whats your nick Name?",
            "Whats your primary school Name?",
            "Whats your birth place"};
    private SharedPreferences savedUserDetails;
    private SharedPreferences saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nfcsend_preview );
        getSupportActionBar().setTitle( "NFC Send" );
        getSupportActionBar().setHomeButtonEnabled( true );
        card_view = (CardView) findViewById( R.id.card_view );
        card_view_1 = (CardView) findViewById( R.id.card_view_1 );
        card_view_1.setVisibility( View.INVISIBLE );
        card_view.setVisibility( View.INVISIBLE );
        txtNFCid = (TextView) findViewById( R.id.txtNFCid );
        txtName = (TextView) findViewById( R.id.txtName );
        txtEmail = (TextView) findViewById( R.id.txtEmail );
        txtContact = (TextView) findViewById( R.id.txtContact );
        edtAmt = (EditText) findViewById( R.id.edtAmt );
        edtAnswer = (EditText) findViewById( R.id.edtAnswer );
        spnSelectQues = (Spinner) findViewById( R.id.spnSelectQues );

        ArrayAdapter ques1 = new ArrayAdapter( this, android.R.layout.simple_spinner_item, Question1 );
        ques1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnSelectQues.setAdapter( ques1 );

        btnSend = (Button) findViewById( R.id.btnSend );
        btnSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItem = spnSelectQues.getSelectedItemPosition();
                savedUserDetails = PreferenceManager.getDefaultSharedPreferences( NFCSendPreview.this );
                String Q = savedUserDetails.getString( "strQuestion1", "" );
                String A = savedUserDetails.getString( "strAnswer1", "" );

                if (checkValidation()) {
                    if (selectedItem != 0) {
                        if (edtAnswer.getText().toString().equals( A ) && spnSelectQues.getSelectedItem().toString().equals( Q )) {
                            SendMoney();
                        } else {
                            Toast.makeText( NFCSendPreview.this, "Invalid Question or Answer", Toast.LENGTH_SHORT ).show();
                        }

                    } else {
                        Toast.makeText( NFCSendPreview.this, "Select Question", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );

        receiverEmail = getIntent().getStringExtra( "strEmail" );
//        receiverEmail = "sample@samp.samp";
        txtEmail.setText( receiverEmail );
        GetReceiversDetails();

    }

    private void SendMoney() {
        saveUser = PreferenceManager.getDefaultSharedPreferences( this );
        final String sender_email = saveUser.getString( "strEmail", "" );
        final String rec_email = receiverEmail;
        final String rec_amt = edtAmt.getText().toString();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    String success = jsonResponse.getString( "success" );

                    if (success.equals( "TRANSFERED" )) {
                        Intent intent = new Intent( NFCSendPreview.this, SendSuccess.class );
                        startActivity( intent );
                        finish();
                    } else if (success.equals( "NOBAL" )) {
                        AlertDialog.Builder builder = new AlertDialog.Builder( NFCSendPreview.this );
                        builder.setMessage( "Insufficient Balance in your Wallete" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( NFCSendPreview.this );
                        builder.setMessage( "Something goes wrong" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SendMoneyRequest sendMoneyRequest = new SendMoneyRequest( sender_email, rec_email, rec_amt, responseListener );
        RequestQueue queue = Volley.newRequestQueue( NFCSendPreview.this );
        queue.add( sendMoneyRequest );

    }

    private void GetReceiversDetails() {
        final String rec_email = receiverEmail;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );

                    if (success) {
                        card_view.setVisibility( View.VISIBLE );
                        card_view_1.setVisibility( View.VISIBLE );
                        String id = jsonResponse.getString( "id" );
                        String name = jsonResponse.getString( "Name" );
                        String contact = jsonResponse.getString( "Contact" );
                        txtName.setText( name );
                        txtContact.setText( contact );
                        txtNFCid.setText( id );
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( NFCSendPreview.this );
                        builder.setMessage( "No Details Found" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        NFCReceiverDetailRequest nfcReceiverDetailRequest = new NFCReceiverDetailRequest( rec_email, responseListener );
        RequestQueue queue = Volley.newRequestQueue( NFCSendPreview.this );
        queue.add( nfcReceiverDetailRequest );

    }

    @Override
    public void onBackPressed() {
        Intent ii = new Intent( NFCSendPreview.this, MainActivity.class);
        startActivity( ii );
        finish();
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText( edtAmt )) ret = false;
        if (!Validation.hasText( edtAnswer )) ret = false;
        return ret;
    }
}