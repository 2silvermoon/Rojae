package com.the43appmart.nfc.kfc;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class NFC_Receive_detail extends AppCompatActivity {


    TextView textView;
    ImageView image;
    int j;

    /*

     * 액티비티 화면을 만들고 xml 파일을 호출하는 함수이다.

     * 이 함수내에 사용하는 위젯을 선언한다.

     * Intent 값을 받아들인다.

     * onNewIntent() 메소드로 intent 로 받아온 값을 최종적으로 byte 로 변환 후 글씨로 변환한다.

     */

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.nfc_receive_detail);

        // TODO Auto-generated method stub



        textView = (TextView)findViewById(R.id.nfc_data);                      //TextView 등록
        image = (ImageView)findViewById(R.id.recv_img);                   //ImageView 등록
        Intent passedIntent = getIntent();                                    //intent 값을 받아들인다.

        /*

         * intent의 값이 null 이 아닐경우에 발생한다.

         */

        if(passedIntent != null)
        {
            onNewIntent(passedIntent); // 데이터를 글씨로 변환 후 띄우는 메소드
        }
    }



    /*

     * 이 함수는 intent 값을 변환 한다. 변환되는 과정은 다음과 같다.
     * 1. Intent -> Parcelable
     * 2. Parcelable -> NdefRecord
     * 3. NdefRecord -> byte
     * 4. byte -> String
     *

     * Parcelable[] data : 전달된 명령어 묶음이 몇묶음 인지를 담는다.
     * NdefRecord[] recs : 묶음안에 명령어가 몇개인지를 담는다.
     * byte[] payload : 인덱스 하나당 명령어 하나의 비트값을 담는다.
     * String s : payload 의 비트값이 담겨진다. 비트로이루워진 모든 텍스트 정보가 담겨져 한번에 화면에 뿌린다.

     */

    protected void onNewIntent(Intent intent) { //테그데이터를 전달받았을때 태그정보를 화면에 보여줌.
        String s = ""; // 글씨를 띄우는데 사용

        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES); // EXTRA_NDEF_MESSAGES : 여분의 배열이 태그에 존재한다.
        /*
         * 조건이 널이 아닌 경우에 글씨 & 그림으로 변환작업을 한다.
         * 널인 경우에는 메소드가 작동하지 않는다.
         */

        if(data != null)
        {

            /*
             * 이러한 처리르 한다.
             * 이러한 처리에서 벗어나면 예외 처리를 통해 출력해준다 .
             */

            try{
                for (int i =0; i<data.length; i++){
                    NdefRecord[] recs = ((NdefMessage)data[i]).getRecords();
                    for(j = 0; j<recs.length; j++)
                    {
                        /*
                         * byte로 날라온 텍스트 처리
                         */
                        if(recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(recs[j].getType(),
                                NdefRecord.RTD_TEXT)){
                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200)==0)?"UTF-8":"UTF-16";
                            int langCodeLen = payload[0] & 0077;
                            s += ("\n"+ new String(payload, langCodeLen + 1, payload.length - langCodeLen -1, textEncoding));
                        }
                    }
                }
            }

            catch(Exception e) {
                Log.e("TagDispatch", e.toString());
            }
        }
        textView.setText(s); //String s 를 화면에 뿌림
    }
}
