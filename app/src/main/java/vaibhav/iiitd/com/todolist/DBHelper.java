package vaibhav.iiitd.com.todolist;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.List;

import android.database.Cursor;
/**
 * Created by Vaibhav on 31-10-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "TaskData.db";
    public static final String TABLE_NAME = " Task ";
    public static final String COLUMN_TITLE = " Title ";
    public static final String COLUMN_DETAIL = " Details ";
    public static final String COLUMN_TIME_INSERT = " Time_inserted ";
    public static final String COLUMN_TIME_DONE = " Time_Done ";


    public DBHelper(Context con){
        super(con, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists" + TABLE_NAME + "(" +
                        COLUMN_TITLE + "text," +
                        COLUMN_DETAIL + "text,"+
                        COLUMN_TIME_INSERT + "DATETIME," +
                        COLUMN_TIME_DONE + "DATETIME" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS grievance");
        onCreate(db);
    }

    public void insert_into_task(String title, String detail){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert into" + TABLE_NAME + "values(" +
                "'" + title + "'," +
                "'" + detail + "'," +
                "'" + d + "'," +
                "null" +
                ")";
        db.execSQL(query);
    }

    public ArrayList<String[]> get_data_for_display(){

        ArrayList<String[]> all_items = null;
        String []temp;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select" + COLUMN_TITLE + "," +
                COLUMN_TIME_INSERT + "from" +
                TABLE_NAME  + "where" +
                COLUMN_TIME_INSERT + "not in("+
                "select" + COLUMN_TIME_INSERT + "from" + TABLE_NAME +
                "where" + COLUMN_TIME_DONE + "is not null)" +
                "order by" + COLUMN_TIME_INSERT;
        Cursor result =  db.rawQuery(query, null);
        result.moveToFirst();
        int count = 0;
        while(result.isAfterLast() == false){
            count++;
            result.moveToNext();
        }
        result.moveToFirst();
        if(count == 0)
            return null;
        else{
            all_items = new ArrayList<String[]>();
            while(result.isAfterLast() == false){
                temp = new String[2];
                System.gc();
                temp[0] = result.getString(result.getColumnIndex("Title"));
                temp[1] = result.getString(result.getColumnIndex("Time_inserted"));
                all_items.add(temp);
                result.moveToNext();
            }
            return all_items;
        }
    }

    public String get_details(String name, String time){

        String details = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select" + COLUMN_DETAIL + "from" +
                TABLE_NAME + "where"+
                COLUMN_TITLE + "='" + name + "' and "+
                COLUMN_TIME_INSERT + "='" + time + "'";
        Cursor result =  db.rawQuery(query, null);
        result.moveToFirst();
        if(result.isAfterLast() == false){
            details = result.getString(result.getColumnIndex("Details"));
            result.moveToNext();
        }
        return details;
    }

    public void remove_task(String title, String details){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from" + TABLE_NAME +
                "where" + COLUMN_TITLE + "='" + title + "' and" +
                COLUMN_DETAIL + "='" + details + "'";

        db.execSQL(query);
        /*return db.delete("Task",
                "Title = ? DETAIL = ?",
                new String[]{title, details});*/
    }
}
