package com.the43appmart.nfc.kfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.the43appmart.nfc.kfc.DustBin.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aniruddha on 24/01/2018.
 */

public class AddCard extends Fragment {
    EditText CardHolderName, CardNumber, CardCVV;
    Spinner spnCardBrand, spnSelectMonth, spnSelectYear;
    String[] CardBrands = {"Select Card Brand", "VISA","MASTER CARD"};
    String[] Months = {"Month", "01","02","03", "04", "05", "06", "07", "08", "09", "10","11","12"};
    String[] Years = {"Year", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027"};
    private SharedPreferences savedUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.add_card, container, false );
        CardHolderName = (EditText) view.findViewById( R.id.edtCardHolderName );
        CardNumber = (EditText) view.findViewById( R.id.edtCardNumber );
        CardCVV = (EditText) view.findViewById( R.id.edtCardCVV );
        spnCardBrand = (Spinner) view.findViewById( R.id.spnCardBrand );
        spnSelectMonth = (Spinner) view.findViewById( R.id.spnSelectMonth );
        spnSelectYear = (Spinner) view.findViewById( R.id.spnSelectYear );
        Button btnAddCard = (Button) view.findViewById( R.id.btnAddCard );

        ArrayAdapter cb = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, CardBrands );
        cb.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnCardBrand.setAdapter( cb );

        ArrayAdapter mon = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, Months );
        mon.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnSelectMonth.setAdapter( mon );

        ArrayAdapter yr = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, Years );
        yr.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnSelectYear.setAdapter( yr );

        btnAddCard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spnCardBrand.getSelectedItem().toString().equals( "Select Card Brand" ) ||
                        spnSelectMonth.getSelectedItem().toString().equals( "Month" ) ||
                        spnSelectYear.getSelectedItem().toString().equals( "Year" )) {
                    Toast.makeText( getContext(), "Choose correct options..", Toast.LENGTH_SHORT ).show();
                } else {

                }

                if (checkValidation()) {
                    sendData();
                    Toast.makeText( getContext(), "Ok done", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( getContext(), "Fields Should not be Empty", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        return view;
    }

    private void sendData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );
                    if (success) {
                        Toast.makeText( getContext(), "Card Added Successfully.", Toast.LENGTH_SHORT ).show();
                        Intent intent = new Intent( getContext(), MainActivity.class );
                        getContext().startActivity( intent );
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                        builder.setMessage( "Failed...Card Already Exist, Please Try with another card" )
                                .setNegativeButton( "Retry", null )
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        savedUser = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String Id = savedUser.getString("strId", "");
        final String cardbrand = spnCardBrand.getSelectedItem().toString();
        final String holdername = CardHolderName.getText().toString();
        final String cardnumber = CardNumber.getText().toString();
        final String cardcvv = CardCVV.getText().toString();
        final String expiry = spnSelectMonth.getSelectedItem().toString()+"/"+spnSelectYear.getSelectedItem().toString();

        CardSendData registerRequest = new CardSendData( Id,cardbrand, holdername, cardnumber, cardcvv, expiry, responseListener );
        RequestQueue queue = Volley.newRequestQueue( getContext() );
        queue.add( registerRequest );
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText( CardHolderName )) ret = false;
        if (!Validation.hasText( CardNumber )) ret = false;
        if (!Validation.hasText( CardCVV )) ret = false;
        return ret;
    }
}
