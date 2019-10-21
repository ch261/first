package com.example.myapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RareListActivity extends ListActivity implements Runnable{
    String data[] ={"wait..."};
    Handler handler;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rare_list);

        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        setListAdapter(adapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RareListActivity.this,android.R.layout.simple_expandable_list_item_1,list2);
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取数据,带回主线程
        List<String> relist = new ArrayList<>();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

        if(curDateStr.equals(logDate)){
            //如果相等，则不从网络中获取数据
            Log.i("run","日期相等，从数据库中获取数据");
            Ratemanger dbManager = new Ratemanger(this);
            for(RateItem rateItem : dbManager.listAll()){
                relist.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
            }
        }else{
            Document doc = null;
            String url ="http://www.usd-cny.com/bankofchina.htm";
            try {
                doc = (Document) Jsoup.connect(url).get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Element table =  doc.getElementsByTag("table").first();
            //获取TD中的数据
            Elements tds = table.getElementsByTag("td");
            List<RateItem> rateList = new ArrayList<RateItem>();
            for(int i =0;i< tds.size();i+=6) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 2);
                String str1 = td1.text();
                String val = td2.text();

                relist.add(str1+ "==>" + val);
                rateList.add(new RateItem(str1,val));
            }
            //把数据写入数据库
            Ratemanger ratemanger = new Ratemanger(this);
            ratemanger.deleteAll();
            ratemanger.addAll(rateList);
            //更新记录日期
            SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, curDateStr);
            edit.commit();
            Log.i("run","更新日期结束：" + curDateStr);

        }


        Message msg = handler.obtainMessage(7);
        msg.obj=relist;
        handler.sendMessage(msg);


    }
}
