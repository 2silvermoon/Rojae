package com.the43appmart.nfc.kfc.DustBin;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.the43appmart.nfc.kfc.AddMoney;
import com.the43appmart.nfc.kfc.LoadWalleteRequest;
import com.the43appmart.nfc.kfc.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyWallete extends Fragment {
    TextView txtWalleteBal;
    Button btnAddMoney;
    private SharedPreferences saveUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.my_wallete, container, false );
        txtWalleteBal = (TextView) view.findViewById( R.id.txtWalleteBal );
        btnAddMoney=(Button)view.findViewById( R.id.btnAddMoney );
        btnAddMoney.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AddMoney();
                FragmentManager FM = getFragmentManager();
                FM.beginTransaction()
                        .setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out )
                        .replace( R.id.layout_MainActivity, fragment )
                        .addToBackStack( String.valueOf( FM ) )
                        .commit();
            }
        } );
        GetBalance();
        return view;
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
                        txtWalleteBal.setText( balance);
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


}
