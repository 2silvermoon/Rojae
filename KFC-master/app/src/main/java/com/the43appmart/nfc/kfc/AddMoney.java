package com.the43appmart.nfc.kfc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.the43appmart.nfc.kfc.Domain.DOMAIN_URL;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class AddMoney extends Fragment {

    private RecyclerView recyclerView;
    private AddMoneyAdapter adapter;
    private List<AddMoneyData> data_list = null;
    private SharedPreferences savedUser;
    private String Id;
    private ProgressDialog progress;
    private Context context;
    private String GETDATA;
    private SharedPreferences savecardDetails;
    private String name, cardnumber, expiry;
    private String balance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_money, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<AddMoneyData>();

        savedUser = PreferenceManager.getDefaultSharedPreferences(getContext());
        Id = savedUser.getString("strId", "");
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Please Wait");
        progress.setMessage("Getting Card Details...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);

        load_data_from_server();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AddMoneyAdapter(getContext(), data_list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    name = data_list.get(position).getName().toString();
                    cardnumber = data_list.get(position).getCardNumber();
                    expiry = data_list.get(position).getExpiry();
                    savecardDetails = PreferenceManager
                            .getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = savecardDetails.edit();
                    editor.putString("strname", name);
                    editor.putString("strcardnumber", cardnumber);
                    editor.putString("strexpiry", expiry);
                    editor.putString("strbalance", balance);

                    editor.commit();
                    Fragment f = new AddAmount();
                    if (f != null) {
                        FragmentManager FM = getFragmentManager();
                        FM.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.layout_MainActivity, f)
                                .addToBackStack(String.valueOf(FM))
                                .commit();
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return view;
    }

    private void load_data_from_server() {
        progress.show();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(DOMAIN_URL + "/NFCPay/GetMyCards.php?id=" + Id)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        AddMoneyData data = new AddMoneyData(object.getString("UserId"),
                                object.getString("Name"),
                                object.getString("CardNumber"),
                                object.getString("CVV"),
                                object.getString("Expiry"),
                                object.getString("Brand"));
                        data_list.add(data);
                    }


                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                } catch (
                        JSONException e)

                {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                progress.hide();
            }
        };

        task.execute();
    }
}
