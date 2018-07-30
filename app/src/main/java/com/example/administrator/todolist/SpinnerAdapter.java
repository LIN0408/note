package com.example.administrator.todolist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter  extends BaseAdapter{


    ArrayList<ItemData> colour_list;
    LayoutInflater inflater; //加載佈局(layout view)

    public SpinnerAdapter(@NonNull Context context, ArrayList<ItemData> colour_list) {
        this.colour_list = colour_list;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {

        return colour_list.size();
    }

    @Override
    public Object getItem(int position) {
        return colour_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return colour_list.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemData itemData=(ItemData) getItem(position);
        convertView=inflater.inflate(R.layout.color_view,null);
        ImageView palette=convertView.findViewById(R.id.color);
        TextView colour_name=convertView.findViewById(R.id.colour_name);
        palette.setBackgroundColor(Color.parseColor(itemData.code));
        colour_name.setText(itemData.name);

        return convertView;
    }
}
