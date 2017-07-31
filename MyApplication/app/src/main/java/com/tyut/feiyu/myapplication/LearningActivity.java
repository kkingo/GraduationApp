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
import java.util.logging.Handler;

import static android.R.layout.simple_list_item_1;

public class LearningActivity extends AppCompatActivity {
    private ListView lv_learn;
    private LearnListviewAdapter learnAdapter;
    private Button btn_input;
    private List<Map<String,String>> LearnListItems;
    private MsgSendThread msgsend;
    private Handler handler;
    private int clicktime=1;
    private AutoCompleteTextView mInputText;
    private List<Integer> clickpos=new ArrayList<Integer>();
    String[] res={"评价 课程 老师","提出 建议 意见","发表 心得 体会","评价张三老师的数据结构课程","发表一些建议","数据库","嵌入式","数据结构","操作系统","计算机网络","c语言",
            "人工智能","通信原理","云计算","传感器网络","数字逻辑","算法","数据挖掘"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        btn_input = (Button)findViewById(R.id.btn_input);
        lv_learn = (ListView)findViewById(R.id.lv_learn);
        mInputText = (AutoCompleteTextView) findViewById(R.id.text_learn);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,simple_list_item_1,res);
        mInputText.setAdapter(arrayAdapter);
        msgsend=new MsgSendThread("ContentServlet","learn");
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
            LearnListItems = getListItems(recMsg);
            learnAdapter = new LearnListviewAdapter(this, LearnListItems);
            lv_learn.setAdapter(learnAdapter);
            lv_learn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean isShow = true;
                    for (int i : clickpos) {
                        if(position == i){
                            isShow = false;

                        }
                    }
                    if(isShow=true) {
                        showDialog(LearningActivity.this, view, position);
                    }
                }
            });
        }
        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=mInputText.getText().toString();
                if(input != null) {
                    Intent intent = new Intent(LearningActivity.this, MainActivity.class);
                    intent.putExtra("type", "1/" + input);
                    mInputText.setText("");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LearningActivity.this,"please enter your words",Toast.LENGTH_LONG);
                }
            }
        });
    }

    private List<Map<String,String>> getListItems(String recMsg) {
        String[] inputmsg = new String[6];
        List<Map<String,String>> listItems = new ArrayList<Map<String,String>>();
        String[] pieces=recMsg.split("/");
        Log.d("tag",String.valueOf(pieces.length));
        for(String str : pieces){
            inputmsg=str.split(":");
            Map<String,String> map = new HashMap<String,String>();
            map.put("name", inputmsg[0]);
            map.put("teacher", inputmsg[1]);
            map.put("course", inputmsg[2]);
            map.put("content", inputmsg[3]);
            map.put("up", inputmsg[4]);
            map.put("down", inputmsg[5]);
            listItems.add(map);
        }
        return listItems;
    }

    public void showDialog(Context context,final View view,final int position){
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setIcon(R.drawable.ic_info_black_24dp);
        normalDialog.setTitle("你是赞成还是反对这则消息");
        normalDialog.setMessage("");
        normalDialog.setPositiveButton("反对",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            TextView down=(TextView)view.findViewById(R.id.et_down);
                            TextView details=(TextView)view.findViewById(R.id.et_content);
                            Button  mDown = (Button)view.findViewById(R.id.btn_down);
                            String downstr=down.getText().toString();
                            String sendmsg="0"+"/"+downstr+"/"+details.getText().toString()+"/l";
                            int current = Integer.parseInt(downstr);
                            current += 1;
                            down.setText(String.valueOf(current));
                            mDown.setVisibility(View.INVISIBLE);
                            clickpos.add(position);
                            Log.d("tag",sendmsg);
                            MsgSendThread send= new MsgSendThread("changeServlet",sendmsg);
                            send.start();
                    }
                });

        normalDialog.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView up = (TextView)view.findViewById(R.id.et_up);
                        TextView details = (TextView)view.findViewById(R.id.et_content);
                        Button  mUp = (Button)view.findViewById(R.id.btn_up);
                        String upstr=up.getText().toString();
                        String sendmsg="1"+"/"+upstr+"/"+details.getText().toString()+"/l";
                        int current = Integer.parseInt(upstr);
                        current += 1;
                        up.setText(String.valueOf(current));
                        mUp.setVisibility(View.INVISIBLE);
                        clickpos.add(position);
                        MsgSendThread send= new MsgSendThread("changeServlet",sendmsg);
                        send.start();
                    }
                });
        // 显示
        normalDialog.show();
    }

}
