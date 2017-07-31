package com.tyut.feiyu.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by feiyu on 2017/6/8.
 */

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> learnlistItems;
    private ViewHolder viewHolder;
    private int up_times,down_times;
    public static class ViewHolder{
        public TextView first,second,third,content;
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.first = (TextView)rootView.findViewById(R.id.user_first);
            this.second = (TextView)rootView.findViewById(R.id.user_second);
            this.third = (TextView)rootView.findViewById(R.id.user_third);
            this.content = (TextView)rootView.findViewById(R.id.user_content);
        }
    }

    public UserAdapter(Context context, List<Map<String,String>> learnlistItems) {
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.user_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.first.setText((String) learnlistItems.get(position)
                .get("first"));
        viewHolder.second.setText((String) learnlistItems.get(position)
                .get("second"));
        viewHolder.third.setText((String) learnlistItems.get(position)
                .get("third"));
        viewHolder.content.setText((String) learnlistItems.get(position)
                .get("content"));
        return convertView;
    }

}
