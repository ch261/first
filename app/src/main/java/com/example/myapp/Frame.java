package com.example.myapp;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class Frame extends FragmentActivity {

    private Frame mFrangments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbthome,rbtfunc,rbtset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mFrangments=new Frame[3];
        fragmentManager=getSupportFragmentManager();
        mFrangments[0]=(ListFragment)getFragmentManager().findFragmentById(R.id.fragment_main);
        mFrangments[1]=fragmentManager.findFragmentById(R.id.fragment_main);
        mFrangments[2]=fragmentManager.findFragmentById(R.id.fragment_main);
        fragmentTransaction=
                fragmentManager.beginTransaction().hide(mFrangments[0].hide(mFrangments[1].hide(mFrangments[2])));
        fragmentTransaction.show(mFrangments[0].commit);
        radioGroup=findViewById(R.id.bottomgroup);
    }
}
