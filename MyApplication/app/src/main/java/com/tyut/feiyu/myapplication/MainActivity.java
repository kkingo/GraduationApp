package com.tyut.feiyu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageview;
    private Button right;
    private Button left;
    private ListviewAdapter adapter;
    private AutoCompleteTextView et_meg;
    private ListView listview=null;
    private ImageButton settings;
    private String type;
    String[] res={"评价 课程 老师","提出 建议 意见","发表 心得 体会","发布 活动","举办 活动"  ,"反应 校园 生活 问题",
                  "骑行","评价张三老师的数据结构课程","发表一些建议","行知楼","行远楼","行勉楼","明泽苑","宿舍","一食堂","东区食堂","三食堂","清韵轩","东区澡堂","小广场",
            "计算机学院","信息学院","物电学院","轻纺学院","东操场","西操场","行思楼","行逸楼","学生活动中心","第二餐厅","艺术学院","博睿楼","经管学院"
            ,"政法学院","力学学院","外国语学院","体育学院","艺术学院","小广场","图书馆","数据库","嵌入式","数据结构","操作系统","计算机网络","c语言",
            "人工智能","通信原理","云计算","传感器网络","数字逻辑","算法","数据挖掘"};
    private MsgSendThread msgsend;
    private int clicktime=1;
    private SharedPreferences sharedPref;
    private static final String overmsg="谢谢您的参与，我们已经了解您的主要意思的了，本次对话结束。";
    private String tag="B";
    private String send;
    private String recMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        right= (Button) findViewById(R.id.btn_right);
        et_meg=(AutoCompleteTextView) findViewById(R.id.et_meg);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,simple_list_item_1,res);
        et_meg.setAdapter(arrayAdapter);
        listview=(ListView)findViewById(R.id.listview);
        settings=(ImageButton)findViewById(R.id.settings);
        settings.setOnClickListener(this);
        adapter=new ListviewAdapter(this);
        listview.setAdapter(adapter);
        right.setOnClickListener(this);
        sharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if(type==null||type.equals(""))
            type = "1/程序出现问题，请返回上一页";
        String[] info=type.split("/");
        type=info[0];
        adapter.addDataToAdapter(new Msginfo(null,info[1]));
        adapter.notifyDataSetChanged();
        getLTPresultsTask ltpTask=new getLTPresultsTask();
        ltpTask.execute(info[1],sharedPref.getString("students_id","0000000000"));
    }


    @Override
    public void onClick(View v) {
        String sendMsg = et_meg.getText().toString().trim();
        recMsg="SystemRecived,please wait";
        String studentid=sharedPref.getString("students_id","0000000000");
        LtpUtils ltp=LtpUtils.GetDefaultLtpObject(sendMsg);
        switch (v.getId()) {
            case R.id.btn_right:
                adapter.addDataToAdapter(new Msginfo(null,sendMsg));
                adapter.notifyDataSetChanged();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                   getServerTask serverTask=new getServerTask();
                   sendMsg=String.valueOf(clicktime)+"/无/"+studentid+"/"+tag+"/"+sendMsg+"/"+type;
                   serverTask.execute(sendMsg);
                et_meg.setText("");
                listview.smoothScrollToPosition(listview.getCount() - 1);
                break;
            case R.id.settings:
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
        }

    }

    public String getLtpResult(String sendMsg){
        LtpUtils ltp=LtpUtils.GetDefaultLtpObject(sendMsg);
        queryThread qThread=new queryThread(ltp);
        qThread.start();
        try {
            qThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return qThread.getResults();
    }
        public String[] getServerAnswer(String sendmsg){
        String msg=null;
        String[] resultSet=new String[2];
            Log.d("mytag",sendmsg);
        msgsend=new MsgSendThread("IndexServlet",sendmsg);
        msgsend.start();
        try {
            msgsend.join();
            Log.d("receive","Thread has stopped");
            msg=msgsend.getContent();
            if(msg==null){
                msg = "E/服务器无反馈，请重试";
            }
            resultSet = msg.split("/");
            Log.d("receive", msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    class  getServerTask extends AsyncTask<String , Void , String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            String sendmsg=params[0];
            return getServerAnswer(sendmsg);
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            tag=strings[0];
            String recMsg=strings[1];
            if(tag.equals("O"))
                clicktime=0;
            adapter.addDataToAdapter(new Msginfo(recMsg,null));
            adapter.notifyDataSetChanged();
        }
    }

    class getLTPresultsTask extends  AsyncTask<String ,Void , String[] >{

        @Override
        protected String[] doInBackground(String... params) {
            String msg=params[0];
            Log.d("tag","msg");
            String sendMsg;
            String ltpResult=getLtpResult(msg);
            if(!(ltpResult == null)) {
                Log.d("My", ltpResult);
                sendMsg=String.valueOf(clicktime)+"/"+ltpResult+"/"+params[1]+"/"+tag+"/"+params[0]+"/"+type;
            }
            else {
                sendMsg = String.valueOf(clicktime) + "/" +"0"+ "/" + params[1] + "/" + tag + "/" + params[0] + "/" + type;
            }
            Log.d("tag",sendMsg);
            return getServerAnswer(sendMsg);
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            tag=strings[0];
            recMsg=strings[1];
            adapter.addDataToAdapter(new Msginfo(recMsg,null));
            adapter.notifyDataSetChanged();
        }
    }

}

