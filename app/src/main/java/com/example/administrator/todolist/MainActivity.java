package com.example.administrator.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DbAdapter dbAdapter;
    TextView no_memo;
    ListView listView;
    MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter= new DbAdapter(MainActivity.this);
        no_memo=findViewById(R.id.no_memo);
        listView=findViewById(R.id.list_memos);

        if(dbAdapter.lisMemos().getCount()==0){

            listView.setVisibility(View.INVISIBLE);
            no_memo.setVisibility(View.VISIBLE);
        }else{

            listView.setVisibility(View.VISIBLE);
            no_memo.setVisibility(View.INVISIBLE);
        }

        displayList();
    }

    private void displayList(){
        Cursor cursor=dbAdapter.lisMemos();
        myListAdapter=new MyListAdapter(this,cursor);
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor current_cursor=(Cursor) listView.getItemAtPosition(position);
                int item_id=current_cursor.getInt(current_cursor.getColumnIndexOrThrow("_id"));
                Intent intent = new Intent();
                intent.putExtra("type","edit");
                intent.putExtra("item_id",item_id);
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add:
                Intent intent = new Intent();
                intent.putExtra("type","add");
                intent.setClass(MainActivity.this, EditActivity.class );
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
