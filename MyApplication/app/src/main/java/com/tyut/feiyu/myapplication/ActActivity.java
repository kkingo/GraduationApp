package com.tyut.feiyu.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tyut.feiyu.myapplication.R.id.lv_act;

public class ActActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ActListviewAdapter Adapter;
    private Button btn_input;
    private List<Map<String,String>> ListItems;
    private MsgSendThread msgsend;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        btn_input = (Button)findViewById(R.id.btn_input1);
        listView = (ListView)findViewById(lv_act);
        editText = (EditText)findViewById(R.id.act_input);
        msgsend=new MsgSendThread("ContentServlet","act");
        msgsend.start();
        String recMsg = null;
        try {
            msgsend.join();
            Log.d("receive","Thread has stopped");
            recMsg=msgsend.getContent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(recMsg!=null) {
            ListItems = getListItems(recMsg);
            Adapter = new ActListviewAdapter(this, ListItems);
            listView.setAdapter(Adapter);
        }
        listView.setOnItemClickListener(this);
        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString() !=null) {
                    Intent intent = new Intent(ActActivity.this, MainActivity.class);
                    intent.putExtra("type", "2/" + editText.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ActActivity.this,"please enter your words",Toast.LENGTH_LONG);
                }

            }
        });
    }

    private List<Map<String,String>> getListItems(String recMsg) {
        String[] inputmsg =new String[5];
        List<Map<String,String>> listItems = new ArrayList<Map<String,String>>();
        String[] pieces=recMsg.split("/");
        for(String str : pieces){
            inputmsg=str.split(":");
            Map<String,String> map = new HashMap<String,String>();
            map.put("act", inputmsg[0]);
            map.put("host", inputmsg[1]);
            map.put("time", inputmsg[2]);
            map.put("details", inputmsg[3]);
            map.put("number", inputmsg[4]);
            listItems.add(map);
        }
        return listItems;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(ActActivity.this,view,position);
    }
    public void showDialog(Context context, final View view, final int position){
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setIcon(R.drawable.ic_info_black_24dp);
        normalDialog.setTitle("您是否要参加这个活动？");
        normalDialog.setMessage("");
        normalDialog.setPositiveButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActActivity.this,"您未报名参加",Toast.LENGTH_SHORT).show();
                    }
                });

        normalDialog.setNegativeButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView count = (TextView)view.findViewById(R.id.et_number);
                        TextView details = (TextView)view.findViewById(R.id.et_details);
                        String countstr=count.getText().toString();
                        String sendmsg="1"+"/"+countstr+"/"+details.getText().toString()+"/a";
                        int current = Integer.parseInt(countstr);
                        current += 1;
                        count.setText(String.valueOf(current));
                        MsgSendThread send= new MsgSendThread("changeServlet",sendmsg);
                        send.start();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
