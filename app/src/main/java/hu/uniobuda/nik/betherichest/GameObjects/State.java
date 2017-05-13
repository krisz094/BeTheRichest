package hu.uniobuda.nik.betherichest.GameObjects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hu.uniobuda.nik.betherichest.Interfaces.DatabaseHandler;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class State {
    public double currentMoney;
    //upgradeBoughtById
    private HashMap<Integer, Boolean> UpgradeIdUnlocked;
    //InvestmentRankById
    private HashMap<Integer, Integer> InvestmentIdRank; //ezt vissza majd privátra
    private Calendar lastGamblingDate;
    private Calendar nextAllowedGamblingDate;
    private boolean isGamblingTimerRunning = false;

    public Calendar getLastGamblingDate() {
        return lastGamblingDate;
    }

    public void setLastGamblingDate(Calendar lastGamblingDate) {
        this.lastGamblingDate = lastGamblingDate;
    }

    public Calendar getNextAllowedGamblingDate() {
        return nextAllowedGamblingDate;
    }

    public void setNextAllowedGamblingDate(Calendar nextAllowedGamblingDate) {
        this.nextAllowedGamblingDate = nextAllowedGamblingDate;
    }


    public State() {
        currentMoney = 0d;
        UpgradeIdUnlocked = new HashMap<>();
        InvestmentIdRank = new HashMap<>();
        lastGamblingDate = null;
        nextAllowedGamblingDate = Calendar.getInstance();

    }

    public Boolean getUpgradeBoughtById(int id) {
        if (UpgradeIdUnlocked.get(id) == null) return false;
        return UpgradeIdUnlocked.get(id);
    }

    public void setUpgradeAsBought(int id) {
        UpgradeIdUnlocked.put(id, true);
    }

    public int getInvestmentRankById(int id) {
        if (InvestmentIdRank.get(id) == null) return 0;
        return InvestmentIdRank.get(id);
    }

    public void setInvestmentRankById(int id, int rank) {
        InvestmentIdRank.put(id, rank);
    }


    public void saveState(DatabaseHandler Handler) {
        Handler.saveMoney(currentMoney);
        SQLiteDatabase db = Handler.deleteInvestments();
        for (Map.Entry<Integer, Integer> entry : InvestmentIdRank.entrySet()) {
            Handler.saveInvestment(entry.getKey(), entry.getValue(), db);
        }
        Handler.closeDatabase(db);
        db = Handler.deleteUpgrade();
        Integer a;
        for (Map.Entry<Integer, Boolean> entry : UpgradeIdUnlocked.entrySet()) {
            if (entry.getValue()) {
                a = 1;
            } else {
                a = 0;
            }
            Handler.saveUpgrade(entry.getKey(), a, db);
        }
        //Handler.saveLastGamblingDate(ConvertCalendarToString(lastGamblingDate));
        Handler.saveNextAllowedGamblingDate(nextAllowedGamblingDate.getTimeInMillis());
        Handler.closeDatabase(db);
    }

    public void loadState(DatabaseHandler Handler) throws ParseException {
        //TODO

        currentMoney = Handler.loadMoney();
        Handler.loadInvestments(InvestmentIdRank);
        Handler.loadUpGrades(UpgradeIdUnlocked);
        //lastGamblingDate=ConvertStringToCalendar(Handler.loadLastGamblingDate());
        nextAllowedGamblingDate.setTimeInMillis(Handler.loadNextAllowedGamblingDate());
        if (nextAllowedGamblingDate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() > 0) {
            isGamblingTimerRunning = true;
        }
        //isGamblingTimerRunning=true;
        //SZABIVAL EGYEZTETNI MI LEGYEN

    }

    private String ConvertCalendarToString(Calendar cal) {
        final String timeString = new SimpleDateFormat("HH:mm:ss:SSS").format(cal.getTime());
        return timeString;
    }

    private Calendar ConvertStringToCalendar(String timeString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS", Locale.ENGLISH);
        Calendar cal = sdf.getCalendar();
        if (timeString == null) {
            cal.setTime(new Date());
        } else {
            Date date = sdf.parse(timeString);
            cal.setTime(date);
        }
        return cal;
    }

    public boolean getIsGamblingTimerRunning() {
        return isGamblingTimerRunning;
    }

    public void setIsGamblingTimerRunning(boolean isGamblingTimerRunning) {
        this.isGamblingTimerRunning = isGamblingTimerRunning;
    }
}
