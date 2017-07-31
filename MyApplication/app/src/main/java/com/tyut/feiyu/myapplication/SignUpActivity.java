package com.tyut.feiyu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,age,gender,major,account,password,tpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button submit = (Button)findViewById(R.id.submit);
        name = (EditText)findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);
        major = (EditText)findViewById(R.id.major);
        gender = (EditText)findViewById(R.id.gender);
        account = (EditText)findViewById(R.id.studentaccount);
        password = (EditText)findViewById(R.id.password);
        tpassword = (EditText)findViewById(R.id.password_twice);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText=name.getText().toString();
                String ageText=age.getText().toString();
                String genderText=gender.getText().toString();
                String majorText=major.getText().toString();
                String accountText=account.getText().toString();
                String psword=password.getText().toString();
                String tpsword=tpassword.getText().toString();
                if(psword.equals(tpsword)){
                    Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    String sendMsg=nameText+"/"+ageText+"/"+genderText+"/"+majorText+"/"+accountText
                            +"/"+psword;
                    MsgSendThread msgsend=new MsgSendThread("HelloServlet",sendMsg);
                    msgsend.start();
                }
            }
        });
    }

}
