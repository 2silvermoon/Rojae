package com.the43appmart.nfc.kfc;

import java.nio.charset.Charset;
import java.util.Locale;
import android.app.Activity;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;


/*
 * 클래스 명 : NFC_Send
 * 클래스 기능 : NFC통신 발신 기능
 * 클래스 설명 : 이 클래스는 frame 애니메이션을 진행하고

 */



public class NFC_Send extends Activity {



    /*
     * 위젯 관련 변수
     */

    private Toast               toast;
    private TextView           text; //텍스트 뷰 변수
    private SharedPreferences savedUser;


    /*

     * NFC 통신 관련 변수

     */

    private NfcAdapter         nfcAdapter;
    private NdefMessage         mNdeMessage; //NFC 전송 메시지


    /*
     * 액티비티 화면을 만들고 xml 파일을 호출하는 메소드이다.
     * 이 함수내에 사용하는 위젯을 선언한다.
     * 메소드의 소스 형식과 순서는 다음과 같다.

     * 1. 위젯 지정
     * 2. NFC 단말기 정보 가져오기
     * 3. NdefMessage 타입 mNdeMessage 변수에 NFC 단말기에 보낼 정보를 넣는다.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences savedUser = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        String Name = pref.getString("strID", "????");
        String Gender = pref.getString("strGender", "????");
        String Major = pref.getString("strMajor", "????");
        String Email = pref.getString("strEmail", "????");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_send);
        // TODO Auto-generated method stub

        /*

         * 1. 위젯 지정

         */
        // text = (TextView)findViewById(R.id.nfc_send_data);                                                              // 텍스트뷰

        /*
         * 2. NFC 단말기 정보 가져오기
         */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this); // nfc를 지원하지않는 단말기에서는 null을 반환.
        if(nfcAdapter != null) {
            Toast.makeText(getApplicationContext(), "NFC 단말기를 접촉해주세요", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "NFC 기능이 꺼져있습니다.", Toast.LENGTH_LONG).show();
        }

        /*
         * 3. NdefMessage 타입 mNdeMessage 변수에 NFC 단말기에 보낼 정보를 넣는다.
         */
        mNdeMessage=new NdefMessage(

//"ID:"+Name+"Gender:"+Gender+"Major:"+Major+"Email:"+Email+"kfcCode:"+48264826
        new NdefRecord[]{
                        createNewTextRecord(Name, Locale.ENGLISH, true),                        //텍스트 데이터
                        createNewTextRecord("Gender:"+Gender, Locale.ENGLISH, true),    //텍스트 데이터
                        createNewTextRecord("Major:"+Major, Locale.ENGLISH, true),           //텍스트 데이터
                        createNewTextRecord("Email:"+Email, Locale.ENGLISH, true),    //텍스트 데이터
                        createNewTextRecord("kfcCode:"+48264826, Locale.ENGLISH, true),                  //텍스트 데이터
                }
        );
    }

    /*
     * 액티비티 화면이 나오기 전에 실행되는 메소드이다.
     * onCreate 에서 정한 mNdeMessage 의 데이터를 NFC 단말기에 전송한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundNdefPush(this, mNdeMessage);
        }
    }

    /*
     * 액티비티 화면이 종료되면 NFC 데이터 전송을 중단하기 위해 실행되는 메소드이다.
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundNdefPush(this);
        }
    }

    /*
     * 텍스트 형식의 데이터를 mNdeMessage 변수에 넣을 수 있도록 변환해 주는 메소드이다.
     */
    public static NdefRecord createNewTextRecord(String text, Locale locale, boolean encodelnUtf8){
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodelnUtf8 ? Charset.forName("UTF-8"):Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);
        int utfBit = encodelnUtf8 ? 0:(1<<7);
        char status = (char)(utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte)status;

        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN,NdefRecord.RTD_TEXT, new byte[0], data);
    }


}