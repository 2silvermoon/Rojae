package com.the43appmart.nfc.kfc;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.the43appmart.nfc.kfc.DustBin.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Aniruddha on 27/01/2018.
 */

public class AddAmount extends Fragment {

    private SharedPreferences savecardDetails;
    TextView txtCardNumber, txtCardHolderName, txtExpiry;
    EditText edtAmount, edtAnswer;
    Button btnAddAmt;
    // Spinner spnSelectQues;
    String[] Question1 = {"Select Security Question 1", "Whats your nick Name?",
            "Whats your primary school Name?",
            "Whats your birth place"};

    private SharedPreferences saveUser;
    private TextView txtBalance;
    private Button btnGetOTP;
    private SharedPreferences saveOTP;
    private EditText edtSubmitOTP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.add_amount, container, false );

        txtCardNumber = (TextView) view.findViewById( R.id.txtCardNumber );
        txtCardHolderName = (TextView) view.findViewById( R.id.txtCardHolderName );
        edtAmount = (EditText) view.findViewById( R.id.edtAmount );
        edtSubmitOTP = (EditText) view.findViewById( R.id.edtSubmitOTP );

        btnAddAmt = (Button) view.findViewById( R.id.btnAddAmt );
        txtExpiry = (TextView) view.findViewById( R.id.txtExpiry );
        txtBalance = (TextView) view.findViewById( R.id.txtBalance );
        btnGetOTP = (Button) view.findViewById( R.id.btnGetOTP );
        savecardDetails = PreferenceManager.getDefaultSharedPreferences( getContext() );
        txtCardHolderName.setText( savecardDetails.getString( "strname", "" ) );
        txtCardNumber.setText( savecardDetails.getString( "strcardnumber", "" ) );
        txtExpiry.setText( savecardDetails.getString( "strexpiry", "" ) );
        txtBalance.setText( savecardDetails.getString( "strbalance", "" ) );


        //     spnSelectQues = (Spinner) view.findViewById( R.id.spnSelectQues111 );

//        ArrayAdapter ques1 = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, Question1 );
//        ques1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
//        spnSelectQues.setAdapter( ques1 );
        btnAddAmt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LOadQues();
                CheckData();


            }
        } );
        btnGetOTP.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateOTP();
                sendOTP();
            }
        } );
        GetBalance();
        return view;
    }

    private void CheckData() {
        if (checkValidation()) {
            saveOTP = PreferenceManager.getDefaultSharedPreferences( getContext() );
            String messege = saveOTP.getString( "strOTP", "" );
            if (messege.equals( edtSubmitOTP.getText().toString() )) {
                AdddAmount();
            } else {
                Toast.makeText( getContext(), "invalid Otp", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private void sendOTP() {
        saveOTP = PreferenceManager.getDefaultSharedPreferences( getContext() );
        String messege = saveOTP.getString( "strOTP", "" );
        String number = saveOTP.getString( "strContact", "" );

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };

        GetOTP getotp = new GetOTP( number, messege, responseListener );
        RequestQueue queue = Volley.newRequestQueue( getContext() );
        queue.add( getotp );
    }

    private void GenerateOTP() {

        Random rand = new Random();
        int n = rand.nextInt( 9999 ) + 1000;
        final String OTP = Integer.toString( n );
        saveOTP = PreferenceManager
                .getDefaultSharedPreferences( getContext() );
        SharedPreferences.Editor editor = saveOTP.edit();
        editor.putString( "strOTP", OTP );
        editor.commit();
    }

    private void GetBalance() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );

                    if (success) {

                        String id = jsonResponse.getString( "id" );
                        String balance = jsonResponse.getString( "Balance" );
                        txtBalance.setText( balance );
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                        builder.setMessage( "Failed To Load Wallete" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        saveUser = PreferenceManager.getDefaultSharedPreferences( getContext() );
        final String id = saveUser.getString( "strId", "" );

        LoadWalleteRequest Request = new LoadWalleteRequest( id, responseListener );
        RequestQueue queue = Volley.newRequestQueue( getContext() );
        queue.add( Request );

    }

//    private void LOadQues() {
//        String q = spnSelectQues.getSelectedItem().toString();
//        String a = edtAnswer.getText().toString();
//        final String ques = q;
//        final String ans = a;
//        saveUser = PreferenceManager.getDefaultSharedPreferences( getContext() );
//        String userid = saveUser.getString( "strId", "" );
//        // Response received from the server
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonResponse = new JSONObject( response );
//                    boolean success = jsonResponse.getBoolean( "success" );
//
//                    if (success) {
//
//                        Toast.makeText( getContext(), "Answer n Question is correct", Toast.LENGTH_SHORT ).show();
//                        AdddAmount();
//
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
//                        builder.setMessage( "Invalid Question or Answer" )
//                                .setNegativeButton( "Try Again..!", null )
//                                .create()
//                                .show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        CompareRequest loginRequestStudent = new CompareRequest( ques, ans, userid, responseListener );
//        RequestQueue queue = Volley.newRequestQueue( getContext() );
//        queue.add( loginRequestStudent );
//
//    }

    private void AdddAmount() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );

                    if (success) {

                        Toast.makeText( getContext(), "money added", Toast.LENGTH_SHORT ).show();
                        Intent i = new Intent( getContext(), MainActivity.class );
                        startActivity( i );

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                        builder.setMessage( "Invalid Question or Answer" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        int current_number = Integer.parseInt( txtBalance.getText().toString() );
        int bal_to_add = Integer.parseInt( edtAmount.getText().toString() );
        int updated = current_number + bal_to_add;
        final String UpdatedBalance = String.valueOf( updated );

        saveUser = PreferenceManager.getDefaultSharedPreferences( getContext() );
        final String id = saveUser.getString( "strId", "" );

        AddAmountRequest loginRequestStudent = new AddAmountRequest( UpdatedBalance, id, responseListener );
        RequestQueue queue = Volley.newRequestQueue( getContext() );
        queue.add( loginRequestStudent );

    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText( edtAmount )) ret = false;
        if (!Validation.hasText( edtSubmitOTP )) ret = false;
        return ret;
    }
}
