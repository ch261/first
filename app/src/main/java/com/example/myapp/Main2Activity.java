package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class Main2Activity extends AppCompatActivity implements Runnable {
    private static String TAG ="Main";
    private float dollarRate=(1/7.1f);
    private float euroRate=(1/11f);
    private float wonRate=500;
    private String updateDate="";

    EditText rmb;
    TextView show;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rmb=(EditText) findViewById(R.id.money);
        show=(TextView)findViewById(R.id.title);
        //读取数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        dollarRate=sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate=sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate=sharedPreferences.getFloat("won_rate",0.0f);
        updateDate=sharedPreferences.getString("update_date","");


        //获取当前时间
        Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todaystr= sdf.format(today);

        if (!todaystr.equals(updateDate)){
            Thread t = new Thread(this);
            t.start();
        }


        //开启子线程

        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    Bundle bdl = (Bundle) msg.obj;
                    dollarRate= bdl.getFloat("dollar_rate");
                    euroRate= bdl.getFloat("euro_rate");
                    wonRate= bdl.getFloat("won_rate");

                    //保存更新的日期
                    SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("update_date",todaystr);
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("euro_rate",euroRate);
                    editor.putFloat("won_rate",wonRate);
                    editor.apply();

                    Toast.makeText(Main2Activity.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };


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
//        Log.i(TAG,"openOne: dollarRate=" + dollarRate);
//        Log.i(TAG,"openOne: euroRate=" + euroRate);
//        Log.i(TAG,"openOne: wonRate=" + wonRate);
        startActivityForResult(config,1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.open_list){
            Intent list = new Intent(this, mylistActivity.class);
            startActivity(list);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==2) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.1f);
            euroRate = bundle.getFloat("key_euro", 0.1f);
            wonRate = bundle.getFloat("key_won", 0.1f);
            //保存sp中的数据
            SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.apply();
        }
    }
    @Override
    public void run() {

        //用于保存获取于网上的汇率
        Bundle bundle = new Bundle();
        //获取网络数据
        getformBOC(bundle);

        //Bundle bundle1;

        //获取msg对象，用于返回主线程
        Message msg = handler.obtainMessage();
        msg.what=5;
        msg.obj=bundle;

        handler.sendMessage(msg);
    }

    private void getformBOC(Bundle bundle) {
        String url ="http://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        try {
            doc = (Document) Jsoup.connect(url).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table =  doc.getElementsByTag("table").first();
        //获取TD中的数据
        Elements tds = table.getElementsByTag("td");
        for(int i =0;i< tds.size();i+=6){
            Element td1 = tds.get(i);
            Element td2 = tds.get(i+2);
            String str1 = td1.text();
            String val = td2.text();
            Log.i(TAG, "run: "+str1+"==>"+val);
            if(val!="-") {
                if ("美元".equals(str1)) {
                    bundle.putFloat("dollar_rate", 100f / Float.parseFloat(val));
                } else if ("欧元".equals(str1)) {
                    bundle.putFloat("euro_rate", 100f / Float.parseFloat(val));
                } else if ("韩元".equals(str1)) {
                    bundle.putFloat("won_rate", 100f / Float.parseFloat(val));
                }
            }
//            float v =100f/Float.parseFloat(val);
        }
    }
//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/icbc.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//            String html = inputStream2String(in);
//            Log.i(TAG, "run: html="+html);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



//    private String inputStream2String(InputStream inputStream) throws IOException{
//        final int bufferSize =1024;
//        final char[] buffer = new char[bufferSize];
//        final StringBuilder out = new StringBuilder();
//        Reader in = new InputStreamReader(inputStream,"gb2312");
//        while (true){
//            int rsz = in.read(buffer,0,buffer.length);
//            if(rsz<0)
//                break;
//            out.append(buffer,0,rsz);
//        }
//        return out.toString();
//    }
}

