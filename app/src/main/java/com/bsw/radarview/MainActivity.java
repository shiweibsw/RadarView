package com.bsw.radarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bsw.radarview.library.RadarValue;
import com.bsw.radarview.library.RadarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RadarView mRadarView;
    List<RadarValue> datas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadarView=(RadarView)findViewById(R.id.radar_view);
        for (int i = 0; i < 7; i++) {
            RadarValue v1=new RadarValue();
            v1.setName("test"+i);
            v1.setValue(i*15);
            datas.add(v1);
        }
        mRadarView.setDatas(datas);

    }
}
