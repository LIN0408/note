package com.example.administrator.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    TextView txtTitle;
    EditText edit_memo;
    Button btn_ok,btn_to_previous_page;
    Spinner spinner_colours;
    String myMemo,currentTime;
    MyListener myListener;
    DbAdapter dbAdapter;
    DateFormat dateFormat;  //抓取系統時間
    ArrayList<ItemData> colour_list;
    SpinnerAdapter spinnerAdapter;
    String selectedColour;
    Bundle bundle;
    int index;
    Boolean isDeleted;
    ConstraintLayout e_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initialisation();
    }

    private  void initialisation(){

        toSeeIfValuesSent();
        myListener=new MyListener();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm" , Locale.TAIWAN);
        colour_list=new ArrayList<>();
        addColour();
        spinnerAdapter = new SpinnerAdapter(this,colour_list);
        spinner_colours=(Spinner)findViewById(R.id.spinner_colours);
        btn_ok=(Button)findViewById(R.id.cancel);
        btn_to_previous_page=(Button)findViewById(R.id.ok);
        btn_to_previous_page.setOnClickListener(myListener);
        btn_ok.setOnClickListener(myListener);
        spinner_colours.setAdapter(spinnerAdapter);
        spinner_colours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView img=(ImageView) view.findViewById(R.id.color);
                ColorDrawable drawable=(ColorDrawable)img.getBackground();
                selectedColour=Integer.toHexString(drawable.getColor()).substring(2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        if(bundle.getString("type").equals("edit")) menuInflater.inflate(R.menu.del_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void toSeeIfValuesSent(){
        edit_memo = findViewById(R.id.editMemo);
        txtTitle = findViewById(R.id.txtTitle);
        e_bg = findViewById(R.id.background);
        dbAdapter=new DbAdapter(getBaseContext());
        bundle=this.getIntent().getExtras();
        if(bundle.getString("type").equals("edit")){
            txtTitle.setText("修改便利貼");
            index=bundle.getInt("item_id");
            Cursor cursor=dbAdapter.queryById(index);
            edit_memo.setText(cursor.getString(2));
            e_bg.setBackgroundColor(Color.parseColor("#"+cursor.getString(4)));
        }

    }

    public void addColour(){
        colour_list.add(new ItemData("紫色","#e4c9ff"));
        colour_list.add(new ItemData("綠色","#93ec9e"));
        colour_list.add(new ItemData("藍色","#79d4ff"));
        colour_list.add(new ItemData("橘色","#ff967c"));
        colour_list.add(new ItemData("黃色","#f9f77d"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.del){
            new AlertDialog.Builder(EditActivity.this)
                    .setTitle("刪除訊息")
                    .setMessage("你確定要刪除這筆資料嗎？")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            isDeleted = dbAdapter.deleteMemo(index);
                            if(isDeleted){
                                Toast.makeText(EditActivity.this,"確定刪除",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditActivity.this,MainActivity.class));
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.cancel:
                    Intent intent = new Intent();
                    intent.setClass(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ok:
                    myMemo=edit_memo.getText().toString();
                    Log.i("memo",myMemo);
                    currentTime=dateFormat.format(new Date(System.currentTimeMillis()));
                    if(bundle.getString("type").equals("edit")){
                        try{
                            dbAdapter.updateMemo(index,currentTime,myMemo,null,selectedColour);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            startActivity(new Intent(EditActivity.this,MainActivity.class));
                        }
                    }else{
                        currentTime=dateFormat.format(new Date(System.currentTimeMillis()));
                        try{
                            dbAdapter.createMemo(currentTime,myMemo,null,selectedColour);

                        }catch (Exception e){

                            e.printStackTrace();
                        }finally {
                            Intent i = new Intent();
                            i.setClass(EditActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    }
                    break;

            }
        }
    }

}
