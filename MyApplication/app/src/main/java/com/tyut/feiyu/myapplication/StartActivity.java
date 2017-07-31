package com.tyut.feiyu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private Button learn,act,envir,setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        act = (Button)findViewById(R.id.btn_to_act1);
        envir = (Button)findViewById(R.id.btn_to_envir1);
        learn = (Button)findViewById(R.id.btn_to_learn1);
        setting = (Button)findViewById(R.id.btn_to_setting1);
        act.setOnClickListener(this);
        learn.setOnClickListener(this);
        envir.setOnClickListener(this);
        setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_to_act1:
                intent = new Intent(StartActivity.this,ActActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_to_envir1:
                intent = new Intent(StartActivity.this,EnvirActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_to_learn1:
                intent = new Intent(StartActivity.this,LearningActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_to_setting1:
                intent = new Intent(StartActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
        }

    }
}
