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
    private static final String TABLE_LASTGAMBLINGDATE = "LastGamblingDate";
    private static final String TABLE_NEXTALLOWEDGAMBLINGDATE = "NextAllowedGamblingDate";

    public DBHelper dbHelper;

    public DatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
    }

    //Save nél elösször delete() utána bejárni a listát és insert

    //ITT VANNAK A GAMBLINGES MÓKÁK
    public long saveLastGamblingDate(String lastGamblingDate) {
        SQLiteDatabase db = deleteLastGamblingdate();
        ContentValues values = new ContentValues();
        values.put("lastDate", lastGamblingDate);
        long id = db.insert(TABLE_LASTGAMBLINGDATE, null, values);
        return id;
    }

    private SQLiteDatabase deleteLastGamblingdate() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LASTGAMBLINGDATE);
        db.execSQL("CREATE TABLE " + TABLE_LASTGAMBLINGDATE + "(" +
                "lastDate TEXT PRIMARY KEY" +
                ")");
        return db;
    }

    public long saveNextAllowedGamblingDate(String nextAllowedGamblingDate) {
        SQLiteDatabase db = deleteNextAllowedGamblingDate();
        ContentValues values = new ContentValues();
        values.put("nextAllowedDate", nextAllowedGamblingDate);
        long id = db.insert(TABLE_NEXTALLOWEDGAMBLINGDATE, null, values);
        return id;
    }

    private SQLiteDatabase deleteNextAllowedGamblingDate() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEXTALLOWEDGAMBLINGDATE);
        db.execSQL("CREATE TABLE " + TABLE_NEXTALLOWEDGAMBLINGDATE + "(" +
                "nextAllowedDate TEXT PRIMARY KEY" +
                ")");
        return db;
    }

    public long saveMoney(double currentmoney) {
        SQLiteDatabase db=deleteMoney();
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("currentMoney", currentmoney);
        long id = db.insert(TABLE_MONEY, null, values);

        db.close();
        return id;
    }

    private SQLiteDatabase deleteMoney() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
        db.execSQL("CREATE TABLE " + TABLE_MONEY + "(" +
                "currentMoney REAL PRIMARY KEY" +
                ")");
        //db.close();
        return db;
    }

    public long saveInvestment(int _id, int rank,SQLiteDatabase db) {
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("rank", rank);
        long id = db.insert(TABLE_INVESTMENTS, null, values);
        //db.close();
        return id;
    }

    public SQLiteDatabase deleteInvestments() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENTS);
        db.execSQL(
                "CREATE TABLE " + TABLE_INVESTMENTS + "(" +
                        "_id   INTEGER PRIMARY KEY," +
                        "rank  INTEGER" +
                        ")");
        //db.close();
        return db;
    }


    public long saveUpgrade(int _id, int rank,SQLiteDatabase db) {
        long id = 0;
        if (rank == 1) {
            //SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_id", _id);
            values.put("rank", rank);
            id = db.insert(TABLE_UPGRADES, null, values);
            //db.close();
        }
        return id;
    }

    public SQLiteDatabase deleteUpgrade() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPGRADES);
        db.execSQL("CREATE TABLE " + TABLE_UPGRADES + "(" +
                "_id   INTEGER PRIMARY KEY," +
                "rank  INTEGER" +
                ")");
        //db.close();
        return db;
    }

    //külön fügvény arra hogy zárja az adatbázist ne kelljen for cikulba nyitni zárni
    public void closeDatabase(SQLiteDatabase db)
    {
        db.close();
    }
    public void openDatabase(SQLiteDatabase db)
    {
        db = dbHelper.getWritableDatabase();
    }

    //LOAD
    public Cursor loadInvestments(HashMap<Integer, Integer> investmentIdRank) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_INVESTMENTS, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        while (!result.isAfterLast()) {
            Integer id = result.getInt(result.getColumnIndex("_id"));
            Integer rank = result.getInt(result.getColumnIndex("rank"));
            investmentIdRank.put(id, rank);
            result.moveToNext();
        }
        return result;
    }

    public Cursor loadUpGrades(HashMap<Integer, Boolean> upgradeIdUnlocked) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_UPGRADES, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        while (!result.isAfterLast()) {
            Integer id = result.getInt(result.getColumnIndex("_id"));
            Boolean rank;
            if (result.getInt(result.getColumnIndex("rank")) == 1) {
                rank = true;
            } else {
                rank = false;
            }
            upgradeIdUnlocked.put(id, rank);
            result.moveToNext();
        }
        return result;
    }

    public Double loadMoney() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_MONEY, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        if (result.getCount() == 0) {
            return 0.0;
        } else {
            return result.getDouble(result.getColumnIndex("currentMoney"));
        }
    }

    public String loadLastGamblingDate() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_LASTGAMBLINGDATE, null, null, null, null, null, null);
        result.moveToFirst();  // kurzor előremozgatása, alapból a végén állt meg
        db.close();
        if (result.getCount() == 0) {
            return null;
        } else {
            return result.getString(result.getColumnIndex("lastDate"));
            //TABLE_LASTGAMBLINGDATE = "LastGamblingDate";
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
            db.execSQL("CREATE TABLE " + TABLE_LASTGAMBLINGDATE + "(" +
                    "lastDate TEXT PRIMARY KEY" +
                    ")");
            db.execSQL("CREATE TABLE " + TABLE_NEXTALLOWEDGAMBLINGDATE + "(" +
                    "nextAllowedDate TEXT PRIMARY KEY" +
                    ")");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPGRADES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LASTGAMBLINGDATE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEXTALLOWEDGAMBLINGDATE);
            onCreate(db);
        }
    }
}
