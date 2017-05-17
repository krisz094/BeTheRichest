package hu.uniobuda.nik.betherichest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.HashMap;
import java.util.List;


/**
 * Created by Márk on 2017. 04. 18..
 */


public class DatabaseHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbname";
    private static final String TABLE_INVESTMENTS = "Investments";
    private static final String TABLE_MONEY = "Money";
    private static final String TABLE_UPGRADES = "Upgrades";
    private static final String TABLE_NEXTALLOWEDGAMBLINGDATE = "NextAllowedGamblingDate";
    private static final String TABLE_DISPLAYEDINVESTMENTS = "DisplayedInvestments";

    public DBHelper dbHelper;
    SQLiteDatabase dbReadable;
    SQLiteDatabase dbWritable;

    public DatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
        dbReadable = dbHelper.getReadableDatabase();
        dbWritable = dbHelper.getWritableDatabase();
    }

    public void saveDisplayedUpgrade(Integer _id, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("displayedId", _id);
        db.insert(TABLE_DISPLAYEDINVESTMENTS, null, values);
    }

    public void deleteDisplayedUpgrades(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPLAYEDINVESTMENTS);
        db.execSQL("CREATE TABLE " + TABLE_DISPLAYEDINVESTMENTS + "(" +
                "displayedId INTEGER PRIMARY KEY" + ")");
    }

    public void saveNextAllowedGamblingDate(long nextAllowedGamblingDate, SQLiteDatabase db) {
        deleteNextAllowedGamblingDate(db);
        ContentValues values = new ContentValues();
        values.put("nextAllowedDate", nextAllowedGamblingDate);
        db.insert(TABLE_NEXTALLOWEDGAMBLINGDATE, null, values);
    }

    private void deleteNextAllowedGamblingDate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEXTALLOWEDGAMBLINGDATE);
        db.execSQL("CREATE TABLE " + TABLE_NEXTALLOWEDGAMBLINGDATE + "(" +
                "nextAllowedDate REAL" +
                ")");
    }

    public void saveMoney(double currentMoney, SQLiteDatabase db) {
        deleteMoney(db);
        ContentValues values = new ContentValues();
        values.put("currentMoney", currentMoney);
        db.insert(TABLE_MONEY, null, values);
    }

    private SQLiteDatabase deleteMoney(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
        db.execSQL("CREATE TABLE " + TABLE_MONEY + "(" +
                "currentMoney REAL" +
                ")");
        return db;
    }

    public void saveInvestment(int _id, int rank, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("rank", rank);
        db.insert(TABLE_INVESTMENTS, null, values);
    }

    public void deleteInvestments(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENTS);
        db.execSQL(
                "CREATE TABLE " + TABLE_INVESTMENTS + "(" +
                        "_id   INTEGER PRIMARY KEY," +
                        "rank  INTEGER" +
                        ")");
    }

    public void saveUpgrade(int _id, int rank, SQLiteDatabase db) {
        if (rank == 1) {
            ContentValues values = new ContentValues();
            values.put("_id", _id);
            values.put("rank", rank);
            db.insert(TABLE_UPGRADES, null, values);
        }
    }

    public void deleteUpgrade(SQLiteDatabase db) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPGRADES);
        db.execSQL("CREATE TABLE " + TABLE_UPGRADES + "(" +
                "_id   INTEGER PRIMARY KEY," +
                "rank  INTEGER" +
                ")");
    }


    public void closeDatabase(SQLiteDatabase db) {
        db.close();
    }


    //LOAD

    public Cursor loadDisplayedUpgrades(List<Integer> list) {

        Cursor result = dbReadable.query(TABLE_DISPLAYEDINVESTMENTS, null, null, null, null, null, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            Integer id = result.getInt(result.getColumnIndex("displayedId"));
            list.add(id);
            result.moveToNext();
        }
        return result;
    }

    public Cursor loadInvestments(HashMap<Integer, Integer> investmentIdRank) {

        Cursor result = dbReadable.query(TABLE_INVESTMENTS, null, null, null, null, null, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            Integer id = result.getInt(result.getColumnIndex("_id"));
            Integer rank = result.getInt(result.getColumnIndex("rank"));
            investmentIdRank.put(id, rank);
            result.moveToNext();
        }
        return result;
    }

    public Cursor loadUpGrades(HashMap<Integer, Boolean> upgradeIdUnlocked) {
        Cursor result = dbReadable.query(TABLE_UPGRADES, null, null, null, null, null, null);
        result.moveToFirst();
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
        Cursor result = dbReadable.query(TABLE_MONEY, null, null, null, null, null, null);
        result.moveToFirst();
        if (result.getCount() == 0) {
            return 0.0;
        } else {
            return result.getDouble(result.getColumnIndex("currentMoney"));
        }
    }

    public long loadNextAllowedGamblingDate() {
        Cursor result = dbReadable.query(TABLE_NEXTALLOWEDGAMBLINGDATE, null, null, null, null, null, null);
        result.moveToFirst();
        if (result.getCount() == 0) {
            return 0;
        } else {
            return result.getLong(result.getColumnIndex("nextAllowedDate"));
        }
    }

    public SQLiteDatabase createWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase createReadableDatabase() {
        return dbHelper.getReadableDatabase();

    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + TABLE_INVESTMENTS + "(" +
                            "_id   INTEGER PRIMARY KEY," +
                            "rank  INTEGER" + ")");
            db.execSQL("CREATE TABLE " + TABLE_UPGRADES + "(" +
                    "_id   INTEGER PRIMARY KEY," +
                    "rank  INTEGER" + ")");
            db.execSQL("CREATE TABLE " + TABLE_MONEY + "(" +
                    "currentMoney REAL" + ")");
            db.execSQL("CREATE TABLE " + TABLE_NEXTALLOWEDGAMBLINGDATE + "(" +
                    "nextAllowedDate" + ")");
            db.execSQL("CREATE TABLE " + TABLE_DISPLAYEDINVESTMENTS + "(" +
                    "displayedId INTEGER PRIMARY KEY" + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPGRADES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEXTALLOWEDGAMBLINGDATE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPLAYEDINVESTMENTS);
            onCreate(db);
        }
    }
}
