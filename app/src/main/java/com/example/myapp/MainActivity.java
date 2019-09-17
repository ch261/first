package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG ="Main";
    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = findViewById(R.id.out);
        out.setText("hello world");

        inp = findViewById(R.id.inp);
        String  str =inp.getText().toString();
        inp.setText("12435");

        Button btn =findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        inp.setText("clicked");

    }
    
    public void abc(View b){
        Log.i(TAG, "abc: 1242");
    }
}
