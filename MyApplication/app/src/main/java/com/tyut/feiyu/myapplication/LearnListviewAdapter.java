package com.tyut.feiyu.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by feiyu on 2017/5/12.
 */

public class LearnListviewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> learnlistItems;
    private ViewHolder viewHolder;
    private int up_times,down_times;
    public static class ViewHolder{
        public TextView et_content,et_teacher,et_course,et_name;
        public TextView et_up,et_down;
        public Button btn_up,btn_down;
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_up = (TextView) rootView.findViewById(R.id.et_up);
            this.et_down = (TextView) rootView.findViewById(R.id.et_down);
            this.btn_up = (Button)rootView.findViewById(R.id.btn_up);
            this.btn_down = (Button)rootView.findViewById(R.id.btn_down);
            this.et_name = (TextView)rootView.findViewById(R.id.et_name);
            this.et_teacher = (TextView)rootView.findViewById(R.id.et_teacher);
            this.et_course = (TextView) rootView.findViewById(R.id.et_course);
            this.et_content = (TextView)rootView.findViewById(R.id.et_content);
        }
    }

    public LearnListviewAdapter(Context context, List<Map<String,String>> learnlistItems) {
        this.context = context;
        this.learnlistItems = learnlistItems;
    }
    public void updateListView(List<Map<String,String>> listItems){
        this.learnlistItems=listItems;
        notifyDataSetChanged();
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return learnlistItems.size();
    }

    @Override
    public Map<String,String> getItem(int position) {
        return learnlistItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Log.e("method", "getView");
        final int selectID = position;
        up_times=1;
        down_times=1;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.learn_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.et_name.setText((String) learnlistItems.get(position)
                .get("name"));
        viewHolder.et_teacher.setText((String) learnlistItems.get(position)
                .get("teacher"));
        viewHolder.et_course.setText((String) learnlistItems.get(position)
                .get("course"));
        viewHolder.et_content.setText((String) learnlistItems.get(position)
                .get("content"));
        viewHolder.et_up.setText((String)learnlistItems.get(position).get("up"));
        viewHolder.et_down.setText((String)learnlistItems.get(position).get("down"));
        return convertView;
    }

}
