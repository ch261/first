package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    private static String TAG ="Main";
    EditText rmb;
    TextView show;

    float dollarRate=(1/7.1f);
    float euroRate=(1/11f);
    float wonRate=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rmb=(EditText) findViewById(R.id.money);
        show=(TextView)findViewById(R.id.title);


    }


    public void onClick(View btn){

        float r =0;
        String str =rmb.getText().toString();
        if(str.length()>0){
            r= Float.parseFloat(str);
        }else{
            //提示用户输入内容
            Toast.makeText(this,"请输入数字",Toast.LENGTH_SHORT).show();
        }

        if(btn.getId()==R.id.dollar){
            show.setText(String.format("%.2f",r*dollarRate));

        }else if(btn.getId()==R.id.euro){
            show.setText(String.format("%.2f",r*euroRate));

        }else if(btn.getId()==R.id.won){
            show.setText(String.format("%.2f",r*wonRate));

        }

    }
    public void openOne(View btn){
        //打开一个新界面
        Intent config = new Intent(this, configActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        Log.i(TAG,"openOne: dollarRate=" + dollarRate);
        Log.i(TAG,"openOne: euroRate=" + euroRate);
        Log.i(TAG,"openOne: wonRate=" + wonRate);
        startActivity(config);
        finish();



    }



}
