package com.the43appmart.nfc.kfc.DustBin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.the43appmart.nfc.kfc.NFC_Receive;
import com.the43appmart.nfc.kfc.NFC_Send;
import com.the43appmart.nfc.kfc.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SlidingUpPanelLayout mLayout;
    private SharedPreferences savedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "KNU 학생 인증" );

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        Menu m = findViewById(R.menu.activity_main_drawer);

        View v = navigationView.getHeaderView( 0 );


/*
        TextView userId = (TextView) v.findViewById(R.id.txtId);
        TextView userGender = (TextView) v.findViewById(R.id.txtGender);
        TextView userMajor = (TextView) v.findViewById(R.id.txtMajor);
        TextView userEmail = (TextView) v.findViewById(R.id.txtEmail);
*/

        savedUser = PreferenceManager.getDefaultSharedPreferences( getApplicationContext());

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        String Name = pref.getString("strID", "????");
        String Gender = pref.getString("strGender", "????");
        String Major = pref.getString("strMajor", "????");
        String Email = pref.getString("strEmail", "????");

        navigationView.setNavigationItemSelectedListener( this );
/*
        userId.setText(Name);
        userGender.setText(Gender);
        userMajor.setText(Major);
        userEmail.setText(Email);
*/

        /**
        final Button courseButton = findViewById(R.id.courseButton);
        final Button statisticsButton = findViewById(R.id.statisticButton);
        final Button scheduleButton = findViewById(R.id.scheduleButton);
        final LinearLayout notice = findViewById(R.id.notice);

        courseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new CourseFragment());
                fragmentTransaction.commit();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        System.out.println("@@@@@@@@@@@@@@@2");
        System.out.println(id);
        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment f = null;
        if (id == R.id.nav_nfc_send) {
            Intent i = new Intent(MainActivity.this, NFC_Send.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_nfc_read) {
            Intent i = new Intent(MainActivity.this, NFC_Receive.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("로그아웃");
            builder.setMessage("로그아웃을 하실건가요?");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences preferences =getSharedPreferences("pref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            builder.setNegativeButton("아니요",null);
            builder.show();
        } else if (id == R.id.nav_myinfo) {

            savedUser = PreferenceManager.getDefaultSharedPreferences( getApplicationContext());

            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

            String Name = pref.getString("strID", "????");
            String Gender = pref.getString("strGender", "????");
            String Major = pref.getString("strMajor", "????");
            String Email = pref.getString("strEmail", "????");

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                    MainActivity.this );
            alertDialog.setTitle( "내 정보" );
            alertDialog.setMessage( "아이디: "+ Name + '\n'
                + "성별: "+ Gender +'\n'
                + "전공: "+ Major + '\n'
                + "이메일: "+ Email +'\n');
            alertDialog.show();
        }

        // fingerprint enroll section

        /*
        else if (id == R.id.nav_create_fingerprint) {
            System.out.println("@@@@@@@@@@@@@!!");
            Intent i = new Intent(MainActivity.this, LoginFingerprint.class);
            startActivity(i);
            finish();
        }
        */

        if (f != null) {
            FragmentManager FM = getSupportFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out )
                    .replace( R.id.layout_MainActivity, f )
                    .addToBackStack( String.valueOf( FM ) )
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
