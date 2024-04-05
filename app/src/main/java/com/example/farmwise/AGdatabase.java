package com.example.farmwise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AGdatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AGdata.db";
    public static final String TABLE_NAME = "records";
    public static final String TABLE_NAME2 = "details";

    //========create records table
    public static final String COL1 = "USER_NAME";
    public static final String COL2 = "MOBILE_NO";
    public static final String COL3 = "AADHAR_NO";
    public static final String COL4 = "PLACE";
    public static final String COL5 = "PIN_CODE";
    public static final String COL6 = "PASSWORD";

    //========create details table
    public static final String COL7 = "PERIOD";
    public static final String COL8 = "CROP_TYPE";
    public static final String COL9 = "PRODUCTION";
    public static final String COL10 = "REGION";
    public static final String COL11 = "AREA";

    public AGdatabase(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //-----records table
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+COL1+" TEXT, "+COL2+" VARCHAR, "+COL3+" VARCHAR, "+COL4+" TEXT, "+COL5+" VARCHAR, "+COL6+" VARCHAR)");
        //-----details table
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME2+" ("+COL7+" VARCHAR, "+COL8+" VARCHAR, "+COL9+" VARCHAR, "+COL10+" TEXT, "+COL11+" VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    //===========insert values in records table
    public boolean addData(String item1,String item2,String item3,String item4,String item5,String item6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1,item1);
        cv.put(COL2,item2);
        cv.put(COL3,item3);
        cv.put(COL4,item4);
        cv.put(COL5,item5);
        cv.put(COL6,item6);
        long result = db.insert(TABLE_NAME,null,cv);

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    //===========Login Verification
    public boolean valid(String item1,String item6){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USER_NAME=? AND PASSWORD=?",
                new String[]{item1,item6});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                mCursor.close();
                return true;
            }
        }
        return false;
    }

    //===========insert values in details table
    public boolean addData(String item7,String item8,String item9,String item10,String item11) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL7,item7);
        cv.put(COL8,item8);
        cv.put(COL9,item9);
        cv.put(COL10,item10);
        cv.put(COL11,item11);
        long result = db.insert(TABLE_NAME2,null,cv);

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    //===========view values in details table
    public ArrayList<ViewModel> getalldata(){
        ArrayList<ViewModel> list = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(sql,null);
        if (c.moveToFirst()){
            do {
                ViewModel viewModel = new ViewModel();
                viewModel.setPeriod(c.getString(0));
                viewModel.setCrop_type(c.getString(1));
                viewModel.setProduction(c.getString(2));
                viewModel.setRegion(c.getString(3));
                viewModel.setArea(c.getString(4));
                list.add(viewModel);
            }while (c.moveToNext());
        }
        return  list;
    }
}
