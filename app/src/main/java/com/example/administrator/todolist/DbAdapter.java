package com.example.administrator.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.security.Key;

public class DbAdapter {

    static final String KEY_ID="_id";
    static final String KEY_DATE="date";
    static final String KEY_MEMO="memo";
    static final String KEY_REMINDER="reminder";
    static final String KEY_BACKGROUNDCOLOUR="backgroundColor";
    static final String DATABASE_NAME="todolist";
    static final String TABLE_NAME="todolistTable";

    DbHelper mDbHelper;
    SQLiteDatabase mdb;
    Context mContext;
    ContentValues values;

    public DbAdapter(Context context) {
        this.mContext = context;
        openDatabase();
    }

    public void openDatabase(){

        mDbHelper=new DbHelper(mContext);
        mdb=mDbHelper.getWritableDatabase();
    }
    public long createMemo(String date,
                           String memo,
                           String reminder,
                           String backgroundColour){

        try{
            values=new ContentValues();
            values.put(KEY_DATE,date);
            values.put(KEY_MEMO,memo);
            values.put(KEY_REMINDER,reminder);
            values.put(KEY_BACKGROUNDCOLOUR,backgroundColour);
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            Toast.makeText(mContext,"新增成功",Toast.LENGTH_SHORT).show();
        }

        return  mdb.insert(TABLE_NAME,null,values);

    }

    public Cursor lisMemos(){
        Cursor mCursor=mdb.query(TABLE_NAME,new String[]{KEY_ID,
        KEY_DATE,KEY_MEMO,KEY_REMINDER,KEY_BACKGROUNDCOLOUR},null,
                null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor queryById(int item_id){

        Cursor mCursor = mdb.query(TABLE_NAME,new String [] {KEY_ID,KEY_DATE,KEY_MEMO,KEY_REMINDER,KEY_BACKGROUNDCOLOUR},
                KEY_ID + "=" + item_id,null,null,null,null,null);

        if(mCursor != null){
            mCursor.moveToFirst();
        }

        return mCursor;
    }


    public long updateMemo(int id, String date, String memo,String reminder,String backgroundColour){

        long update=0;

        try{
            values=new ContentValues();
            values.put(KEY_DATE,date);
            values.put(KEY_MEMO,memo);
            values.put(KEY_REMINDER,reminder);
            values.put(KEY_BACKGROUNDCOLOUR,backgroundColour);
            update=mdb.update(TABLE_NAME,values,"_id=" + id,null);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mContext,"更新成功",Toast.LENGTH_SHORT).show();

        }
        return update;
    }

    public boolean deleteMemo(int id){

        String [] args={Integer.toString(id)};
        mdb.delete(TABLE_NAME,"_id=?",args);
        return true;
    }
}
