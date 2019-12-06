package com.the43appmart.nfc.kfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class NFC_Receive extends AppCompatActivity{
    /*
     * 위의 클래스 변수 정보 참조
     */

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] mlntentFilters;
    private String[][] mNFCTechLists;
    private TextView text;







    /*
     * 액티비티 화면을 만들고 xml 파일을 호출하는 함수이다.
     * 이 함수내에 사용하는 위젯을 선언핸다.
     * 진행 순서는 다음과 같다.

     * 1. 단말기의 NFC 상태를 점검
     * 2. intent 값 지정
     * 3. IntentFilter 지정
     * 4. NFCtech 지정
     * 5. onResume() 지정
     * 6. onPause() 지정
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_receive_detail);
      //  text = (TextView)findViewById(R.id.text); //위젯 선언
        /*
         * 1. 단말기의 NFC 상태를 점검
         */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null) {
           // text.setText("NFC가 꺼짐");
        }
        else {
         //   text.setText("NFC가 켜짐");
        }

        /*
         * 2. intent 값 지정
         */

        Intent intent = new Intent(this, NFC_Receive_detail.class); //다른액티비티에서 처리할 경우에 이렇게 지정한다.
//         intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); //이 문구는 현재 액티비티에서 해결할시에 사용한다.
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0); //intent의 값을 넣는다. 이것을 다음 액티비티에 넘김으로써 다음 액티비티에서 처리가가능

        /*
         *  3. IntentFilter 지정
         */
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED); // Intent to start an activity when a tag with NDEF payload is discovered.
        try{
            ndefIntent.addDataType("*/*");
        }
        catch(Exception e)
        {
            Log.e("TagDispatch", e.toString());
        }

        mlntentFilters = new IntentFilter [] {ndefIntent,};

        /*
         *  4. NFCtech 지정
         */
        mNFCTechLists = new String[][] { new String[]{ NfcF.class.getName()}};
    }

    /*
     * 5. onResume() 지정
     * 화면에 액티비티가 올라오기 전에 실행될 내용을 기술하는 메소드이다.
     * 위에서 지정된 pendingIntent 값으로 인해 second 액티비티로 정보를 넘긴다.
     * nfcAdapter.enableForegroundDispatch(this, pendingIntent, mlntentFilters, mNFCTechLists) 메소드는 위에서 지정한 필터의 값만 넘긴다.
     * nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null) 이렇게 작성할 경우 필터 상관없이 모든 데이터를 전송한다. 이렇게 써도 무방.
     */

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * NFC 태그가 사용 가능할 경우(nfcAdapter가 null 이 아닐경우)에 작동
         */
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, mlntentFilters, mNFCTechLists);
        }
    }



    /*
     * 6. onPause() 지정
     * 화면에 액티비티가 보이지 않을때 실행될 내용을 기술하는 메소드이다..
     * NFC 신호 대기를 중지한다.
     * 중지 후 finish() 명령어를 통해 이 액티비티를 종료한다.
     */

    @Override
    protected void onPause() { //화면이 중지될 때 사용안하기 위해
        //onPause() 에서 .disableForegroundDispatch(this); 사용
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this); //수신
            finish();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}