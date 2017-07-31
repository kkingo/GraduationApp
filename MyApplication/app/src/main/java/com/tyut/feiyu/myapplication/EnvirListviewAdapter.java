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

public class EnvirListviewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> listItems;
    private ViewHolder viewHolder;


    public static class ViewHolder{
        public TextView condition,where,thing,cate;
        public TextView people;
        public Button agree;
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cate = (TextView) rootView.findViewById(R.id.et_cate);
            this.where = (TextView) rootView.findViewById(R.id.et_where);
            this.thing = (TextView)rootView.findViewById(R.id.et_thing);
            this.condition = (TextView)rootView.findViewById(R.id.et_condition);
            this.people = (TextView) rootView.findViewById(R.id.et_people);
            this.agree = (Button)rootView.findViewById(R.id.btn_agree);
        }
    }

    public EnvirListviewAdapter(Context context, List<Map<String,String>> listItems) {
        this.context = context;
        this.listItems = listItems;

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
            convertView = inflater.inflate(R.layout.envir_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cate.setText((String) listItems.get(position)
                .get("cate"));
        viewHolder.where.setText((String) listItems.get(position)
                .get("where"));
        viewHolder.thing.setText((String) listItems.get(position)
                .get("thing"));
        viewHolder.condition.setText((String) listItems.get(position)
                .get("condition"));
        viewHolder.people.setText((String)listItems.get(position).get("people"));
/*        viewHolder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> temp=getItem(selectID);
                String str=temp.get("people");
                int current=Integer.parseInt(str);
                current+=1;
                temp.put("people",String.valueOf(current));
                notifyDataSetChanged();
            }
        });*/
        return convertView;
    }
}
