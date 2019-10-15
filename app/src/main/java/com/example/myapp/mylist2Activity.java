package com.example.myapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mylist2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    Handler handler;
    private String TAG ="list2";
    private ArrayList<HashMap<String,String>> listItems; //存放文字图片
    private List<HashMap<String, String>> list2;
    private SimpleAdapter listItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
//        MyAdapterActivity myAdapter =new MyAdapterActivity(this,R.layout.activity_mylist2,listItems);
//        this.setListAdapter(myAdapter);

        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what==7){
                    list2 = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(mylist2Activity.this,list2,
                            R.layout.activity_mylist2,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }
        };

        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);

    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i= 0;i<20;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail",""+i);
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,listItems,
                R.layout.activity_mylist2,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    }

    @Override
    public void run() {
        List<HashMap<String, String>> reList =new ArrayList<HashMap<String, String>>();
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
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle",str1);
            map.put("ItemDetail",val);
            reList.add(map);

        }

        Message msg = handler.obtainMessage(7);
        msg.obj=reList;
        handler.sendMessage(msg);

    }

    @Override //点击按钮函数
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        HashMap<String, String> map= (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr =map.get("ItemTitle");
        String detailStr =map.get("ItemDetail");

        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView)view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());

        //打开新的页面
        Intent rateCalc =new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);


    }

    @Override  //长按按钮函数
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

        //构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //删除操作
                list2.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        }).
                setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}
