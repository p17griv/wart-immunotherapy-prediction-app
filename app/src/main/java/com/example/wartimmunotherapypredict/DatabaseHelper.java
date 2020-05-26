package com.example.wartimmunotherapypredict;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DatabaseName = "HistoryDb";
    public static final String TableName = "Results";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "age";
    public static final String Col_3 = "gender";
    public static final String Col_4 = "numberOfWarts";
    public static final String Col_5 = "typeOfWarts";
    public static final String Col_6 = "surfaceArea";
    public static final String Col_7 = "indurationDiameter";
    public static final String Col_8 = "dateAndTime";
    public static final String Col_9 = "time";

    public DatabaseHelper(Context context)
    {
        super(context, DatabaseName, null,1);
    }

    //Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, age INTEGER, gender VARCHAR(20)," +
                "numberOfWarts INTEGER, typeOfWarts VARCHAR(20), surfaceArea INTEGER, indurationDiameter INTEGER, dateAndTime VARCHAR(20), time INTEGER)");
    }

    //Delete entire Table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }

    //Delete and recreate table
    public void recreate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }

    //Insert into table
    public boolean insertData(int age, String gender, int numberOfWarts, String typeOfWarts, int surfaceArea, int indurationDiameter, String dateAndTime, int time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col_2,age);
        cv.put(Col_3,gender);
        cv.put(Col_4,numberOfWarts);
        cv.put(Col_5,typeOfWarts);
        cv.put(Col_6,surfaceArea);
        cv.put(Col_7,indurationDiameter);
        cv.put(Col_8,dateAndTime);
        cv.put(Col_9,time);

        long res = db.insert(TableName,null,cv);
        if(res==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllResults()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TableName,null);
        return res;
    }
}
