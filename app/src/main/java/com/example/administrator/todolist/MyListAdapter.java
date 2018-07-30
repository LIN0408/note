package com.example.administrator.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyListAdapter extends CursorAdapter {

    LayoutInflater inflater;

    public MyListAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor , ViewGroup parent){
        return  inflater.inflate(R.layout.item_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context,Cursor cursor){
        LinearLayout container=view.findViewById(R.id.bg_memo);
        TextView date = view.findViewById(R.id.txtDate);
        TextView memo = view.findViewById(R.id.txtMemo);
        date.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        memo.setText(cursor.getString(cursor.getColumnIndexOrThrow("memo")));
        container.setBackgroundColor(Color.parseColor("#"+cursor.getString(4)));
    }
}
