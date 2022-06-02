package com.example.phonedyguard.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.R;

import java.io.IOException;
import java.util.List;

public class route_register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_register);
        // 지오코딩(GeoCoding) : 주소,지명 => 위도,경도 좌표로 변환
        //     위치정보를 얻기위한 권한을 획득, AndroidManifest.xml
        //    ACCESS_FINE_LOCATION : 현재 나의 위치를 얻기 위해서 필요함
        //    INTERNET : 구글서버에 접근하기위해서 필요함
        final TextView reslt = (TextView) findViewById(R.id.result); // 결과창
        Button safeload_btn = (Button)findViewById(R.id.safeload_btn);


        final EditText end_edit = (EditText)findViewById(R.id.end_edittext);
        final EditText start_edit = (EditText)findViewById(R.id.start_edittext);
        final Geocoder geocoder = new Geocoder(this);


        safeload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                // 주소입력후 지도2버튼 클릭시 해당 위도경도값의 지도화면으로 이동
            List<Address> start_latlng = null;
            List<Address> end_latlng = null;
            String start_text = start_edit.getText().toString();
            String end_text = end_edit.getText().toString();
            try
            {
                start_latlng = geocoder.getFromLocationName(start_text, 10); // 읽을 개수
                end_latlng = geocoder.getFromLocationName(end_text, 10);
            }
            catch (IOException e)
            {                    e.printStackTrace();
                Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
            }
            if (start_latlng != null && end_latlng != null) {
                if (start_latlng.size() == 0 & end_latlng.size() == 0 ) {
                    reslt.setText("해당되는 주소 정보는 없습니다");
                } else {                        // 해당되는 주소로 인텐트 날리기
                             Address start = start_latlng.get(0);
                             Address end = end_latlng.get(0);
                             double start_lat = start.getLatitude();
                             double start_lon = start.getLongitude();
                             double end_lat = end.getLatitude();
                             double end_lon = end.getLongitude();
                            Log.d("test",  start_lat + ", " + start_lon);
                            Log.d("test",  end_lat + ", " + end_lon);
                }
            }
            }
        });
    } // end of onCreate
} // end of cla 동의대학교