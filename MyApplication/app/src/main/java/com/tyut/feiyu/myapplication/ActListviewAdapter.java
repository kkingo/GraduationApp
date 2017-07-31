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

public class ActListviewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> listItems;
    private ViewHolder viewHolder;


    public static class ViewHolder{
        public TextView details,host,time,act;
        public TextView number;
        public Button join;
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.act = (TextView) rootView.findViewById(R.id.et_act);
            this.host = (TextView) rootView.findViewById(R.id.et_host);
            this.join = (Button)rootView.findViewById(R.id.btn_join);
            this.time = (TextView)rootView.findViewById(R.id.et_time);
            this.details = (TextView)rootView.findViewById(R.id.et_details);
            this.number = (TextView) rootView.findViewById(R.id.et_number);
        }
    }

    public ActListviewAdapter(Context context, List<Map<String,String>> listItems) {
        this.context = context;
        this.listItems = listItems;

    }
    public void updateListView(List<Map<String,String>> listItem){
        this.listItems=listItem;
        notifyDataSetChanged();
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return listItems.size();
    }

    @Override
    public Map<String,String> getItem(int position) {
        return listItems.get(position);
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
            convertView = inflater.inflate(R.layout.act_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.act.setText((String) listItems.get(position)
                .get("act"));
        viewHolder.host.setText((String) listItems.get(position)
                .get("host"));
        viewHolder.time.setText((String) listItems.get(position)
                .get("time"));
        viewHolder.details.setText((String) listItems.get(position)
                .get("details"));
        viewHolder.number.setText((String)listItems.get(position).get("number"));
/*        viewHolder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> temp=getItem(selectID);
                String str=temp.get("number");
                int current=Integer.parseInt(str);
                current+=1;
                temp.put("number",String.valueOf(current));
                notifyDataSetChanged();
            }
        });*/
        return convertView;
    }
}
