package com.example.myapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RareListActivity extends ListActivity implements Runnable{
    String data[] ={"wait..."};
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rare_list);
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
        for(int i =0;i< tds.size();i+=6) {
            Element td1 = tds.get(i);
            Element td2 = tds.get(i + 2);
            String str1 = td1.text();
            String val = td2.text();

            relist.add(str1+ "==>" + val);
        }
        Message msg = handler.obtainMessage(7);
        msg.obj=relist;
        handler.sendMessage(msg);



    }
}
