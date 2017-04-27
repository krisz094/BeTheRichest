package hu.uniobuda.nik.betherichest.Interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.HashMap;
import java.util.Map;

import hu.uniobuda.nik.betherichest.GameObjects.State;


/**
 * Created by Márk on 2017. 04. 18..
 */


public class DatabaseHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbname";
    private static final String TABLE_INVESTMENTS = "Investments";
    private static final String TABLE_MONEY = "Money";
    private static final String TABLE_UPGRADES = "Upgrades";

    public DBHelper dbHelper;

    public DatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);

        /*
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("rank", 3);
        long id = db.insert(TABLE_INVESTMENTS, null, values);
        */

    }

    //Save nél elösször delete() utána bejárni a listát és insert

    public long saveMoney(double currentmoney) {
        deleteMoney();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("currentMoney", currentmoney);
        long id = db.insert(TABLE_MONEY, null, values);

        db.close();
        return id;
    }

    private void deleteMoney() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
        db.execSQL("CREATE TABLE " + TABLE_MONEY + "(" +
                "currentMoney REAL PRIMARY KEY" +
                ")");
        db.close();
    }

    public long saveInvestment(int _id, int rank) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("rank", rank);
        long id = db.insert(TABLE_INVESTMENTS, null, values);
        db.close();
        return id;
    }

    public void deleteInvestments() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENTS);
        db.execSQL(
                "CREATE TABLE " + TABLE_INVESTMENTS + "(" +
                        "_id   INTEGER PRIMARY KEY," +
                        "rank  INTEGER" +
                        ")");
        db.close();
    }


    public long saveUpgrade(int _id, int rank) {
        long id = 0;
        if (rank == 1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_id", _id);
            values.put("rank", rank);
            id = db.insert(TABLE_UPGRADES, null, values);
            db.close();
        }
        return id;
    }

    public void deleteUpgrade() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPGRADES);
        db.execSQL("CREATE TABLE " + TABLE_UPGRADES + "(" +
                "_id   INTEGER PRIMARY KEY," +
                "rank  INTEGER" +
                ")");
        db.close();
    }


    //LOAD
    public Cursor loadInvestments(HashMap<Integer, Integer> investmentIdRank) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_INVESTMENTS, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        while (result.moveToNext()) {
            Integer id =result.getColumnIndex("_id");
            Integer rank = result.getColumnIndex("rank");
            investmentIdRank.put(id, rank);
        }
        return result;
    }

    public Cursor loadUpGrades(HashMap<Integer, Boolean> upgradeIdUnlocked) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_UPGRADES, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        while (result.moveToNext()) {
            Integer id = result.getColumnIndex("_id");
            Boolean rank;
            if (result.getColumnIndex("rank") == 1) {
                rank = true;
            } else {
                rank = false;
            }
            upgradeIdUnlocked.put(id, rank);
        }
        return result;
    }

    public Double loadMoney() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_MONEY, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        if(result.getCount()==0)
        {
            return 0.0;
        }
        else {


            return result.getDouble(result.getColumnIndex("currentMoney"));
        }
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + TABLE_INVESTMENTS + "(" +
                            "_id   INTEGER PRIMARY KEY," +
                            "rank  INTEGER" +
                            ")"
            );
            db.execSQL("CREATE TABLE " + TABLE_UPGRADES + "(" +
                    "_id   INTEGER PRIMARY KEY," +
                    "rank  INTEGER" +
                    ")");
            db.execSQL("CREATE TABLE " + TABLE_MONEY + "(" +
                    "currentMoney REAL PRIMARY KEY" +
                    ")");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPGRADES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
            onCreate(db);

        }
    }
}
