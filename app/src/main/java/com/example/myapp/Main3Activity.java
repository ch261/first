package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    @Override
    //保存横竖屏的数据
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = ((TextView)findViewById(R.id.sc1)).getText().toString();
        String scoreb =((TextView)findViewById(R.id.sc2)).getText().toString();
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea =savedInstanceState.getString("teama_score");
        String scoreb =savedInstanceState.getString("teamb_score");
        ((TextView)findViewById(R.id.sc1)).setText(scorea);
        ((TextView)findViewById(R.id.sc2)).setText(scoreb);
    }
    //


    public void btnreset1(View v){
        TextView out2=(TextView)findViewById(R.id.sc2);
        out2.setText("0");
        TextView out1=(TextView)findViewById(R.id.sc1);
        out1.setText("0");
    }
    public void btna1(View v){
        show(3);
    }
    public void btna2(View v){
        show(2);
    }
    public void btna3(View v){
        show(1);
    }

    public void  show(int i){
        TextView out1=(TextView)findViewById(R.id.sc1);
        String oldscoer =(String) out1.getText();
        String newscoer =String.valueOf(Integer.valueOf(oldscoer)+i);
        out1.setText(newscoer);
    }
    public void btnb1(View v){
        show1(3);
    }
    public void btnb2(View v){
        show1(2);
    }
    public void btnb3(View v){
        show1(1);
    }
    public void  show1(int i){
        TextView out2=(TextView)findViewById(R.id.sc2);
        String oldscoer =(String) out2.getText();
        String newscoer =String.valueOf(Integer.valueOf(oldscoer)+i);
        out2.setText(newscoer);
    }
}
