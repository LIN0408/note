package com.example.administrator.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    TextView tvtime,tvmemo;
    DbAdapter dbAdapter;
    Spinner spinner_colours;
    Bundle bundle;
    int index;
    Boolean isDeleted;
    ConstraintLayout s_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tvtime = findViewById(R.id.tvtime);
        tvmemo = findViewById(R.id.Memo);
        s_bg = findViewById(R.id.background);

        bundle=this.getIntent().getExtras();
        index=bundle.getInt("item_id");
        dbAdapter=new DbAdapter(getBaseContext());
        Cursor cursor=dbAdapter.queryById(index);
        index = cursor.getInt(0);
        tvmemo.setText(cursor.getString(2));
        s_bg.setBackgroundColor(Color.parseColor("#"+cursor.getString(4)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.del_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.del:
                new AlertDialog.Builder(ShowActivity.this)
                        .setTitle("刪除訊息")
                        .setMessage("你確定要刪除這筆資料嗎？")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                isDeleted = dbAdapter.deleteMemo(index);
                                if(isDeleted){
                                    Toast.makeText(ShowActivity.this,"確定刪除",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setClass(ShowActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.edit:
                Intent i = new Intent();
                i.putExtra("item_id",index);
                i.putExtra("type","edit");
                i.setClass(ShowActivity.this, EditActivity.class );
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
