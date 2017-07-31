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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;
import static com.tyut.feiyu.myapplication.R.id.lv_envir;

public class EnvirActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private EnvirListviewAdapter Adapter;
    private Button input;
    private List<Map<String,String>> ListItems;
    private MsgSendThread msgsend;
    private AutoCompleteTextView editText;
    String[] res={"行知楼","行远楼","行勉楼","明泽苑","宿舍","一食堂","东区食堂","三食堂","清韵轩","东区澡堂","小广场",
            "计算机学院","信息学院","物电学院","轻纺学院","东操场","西操场","行思楼","行逸楼","学生活动中心","第二餐厅","艺术学院","博睿楼","经管学院"
            ,"政法学院","力学学院","外国语学院","体育学院","艺术学院","小广场","图书馆"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envir);
        input = (Button)findViewById(R.id.btn_input2);
        listView = (ListView)findViewById(lv_envir);
        editText = (AutoCompleteTextView) findViewById(R.id.envir_input);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,simple_list_item_1,res);
        editText.setAdapter(arrayAdapter);
        msgsend=new MsgSendThread("ContentServlet","envir");
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
            Adapter = new EnvirListviewAdapter(this, ListItems);
            listView.setAdapter(Adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("tag",String.valueOf(position));
                    showDialog(EnvirActivity.this,view,position);
                }
            });
        }
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if(input != null) {
                    Intent intent = new Intent(EnvirActivity.this, MainActivity.class);
                    intent.putExtra("type", "3/" + editText.getText().toString());
                    editText.setText("");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EnvirActivity.this,"please enter your words",Toast.LENGTH_LONG);
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
            map.put("cate", inputmsg[0]);
            map.put("where", inputmsg[1]);
            map.put("thing", inputmsg[2]);
            map.put("condition", inputmsg[3]);
            map.put("people", inputmsg[4]);
            listItems.add(map);
        }
        return listItems;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(EnvirActivity.this,view,position);
    }
    public void showDialog(Context context, final View view, final int position){
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setIcon(R.drawable.ic_info_black_24dp);
        normalDialog.setTitle("您是否也想反应同一问题？");
        normalDialog.setMessage("");
        normalDialog.setPositiveButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EnvirActivity.this,"您没有确定支持该话题",Toast.LENGTH_SHORT).show();
                    }
                });
        normalDialog.setNegativeButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView count = (TextView)view.findViewById(R.id.et_people);
                        TextView details = (TextView)view.findViewById(R.id.et_condition);
                        String countstr=count.getText().toString();
                        String sendmsg="1"+"/"+countstr+"/"+details.getText().toString()+"/e";
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
