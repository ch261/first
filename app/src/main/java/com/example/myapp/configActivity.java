package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class configActivity extends AppCompatActivity {
    public final String TAG ="configActivity";
    EditText dollartext;
    EditText eurotext;
    EditText wontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2 = intent.getFloatExtra("euro_rate_key",0.0f);
        float won2 = intent.getFloatExtra("won_rate_key",0.0f);
        Log.i(TAG,"onCreat: dollar2="+dollar2);
        Log.i(TAG,"onCreat: euro2="+euro2);
        Log.i(TAG,"onCreat: won2="+won2);
        dollartext = (EditText)findViewById(R.id.dollar_rate);
        eurotext = (EditText)findViewById(R.id.euro_rate);
        wontext = (EditText)findViewById(R.id.won_rate);

        //显示数据到控件
        dollartext.setText(String.valueOf(dollar2));
        eurotext.setText(String.valueOf(euro2));
        wontext.setText(String.valueOf(won2));


    }

    public void save(View btn){
        Log.i("cfg","save:");
        //获取新的值
        float newdollar = Float.parseFloat(dollartext.getText().toString());
        float neweuro = Float.parseFloat(eurotext.getText().toString());
        float newwon = Float.parseFloat(wontext.getText().toString());
        Log.i("cfg","save:获取新的值");
        Log.i(TAG,"onCreat: newdollar="+newdollar);
        Log.i(TAG,"onCreat: neweuro="+neweuro);
        Log.i(TAG,"onCreat: newwon="+newwon);
        //保存到Bundle或放入extra
        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newdollar);
        bdl.putFloat("key_euro",neweuro);
        bdl.putFloat("key_won",newwon);
        intent.putExtras(bdl);
        setResult(2,intent);
        //放回当前页面
        finish();

    }
}
