package hu.uniobuda.nik.betherichest.GameObjects;

import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uniobuda.nik.betherichest.DatabaseHandler;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class State {
    public double currentMoney;
    //upgradeBoughtById
    private HashMap<Integer, Boolean> UpgradeIdUnlocked;
    //InvestmentRankById
    private HashMap<Integer, Integer> InvestmentIdRank; //ezt vissza majd privátra
    SQLiteDatabase dbWriteable;
    SQLiteDatabase dbReadable;

    public List<Integer> getDisplayableUpgradeId() {
        return DisplayableUpgradeId;
    }

    public void setDisplayableUpgradeId(Integer id) {
        DisplayableUpgradeId.add(id);
    }

    private List<Integer> DisplayableUpgradeId;
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
        dbWriteable = Handler.createWriteableDatabase();
        dbWriteable = Handler.deleteInvestments(dbWriteable);
        dbWriteable = Handler.saveMoney(currentMoney, dbWriteable);
        for (Map.Entry<Integer, Integer> entry : InvestmentIdRank.entrySet()) {
            dbWriteable = Handler.saveInvestment(entry.getKey(), entry.getValue(), dbWriteable);
        }
        dbWriteable = Handler.deleteUpgrade(dbWriteable);
        Integer a;
        for (Map.Entry<Integer, Boolean> entry : UpgradeIdUnlocked.entrySet()) {
            if (entry.getValue()) {
                a = 1;
            } else {
                a = 0;
            }
            dbWriteable = Handler.saveUpgrade(entry.getKey(), a, dbWriteable);
        }
        dbWriteable = Handler.deleteDisplayedUpgrades(dbWriteable);
        for (Upgrade upgrade : Game.Get().getDisplayableUpgrades()) {
            DisplayableUpgradeId.add(upgrade.getId());
            dbWriteable = Handler.saveDisplayedUpgrade(upgrade.getId(), dbWriteable);
        }
        dbWriteable = Handler.saveNextAllowedGamblingDate(nextAllowedGamblingDate.getTimeInMillis(), dbWriteable);
        Handler.closeDatabase(dbWriteable);
    }


    public void loadState(DatabaseHandler Handler) throws ParseException {
        dbReadable = Handler.createReadableDatabase();
        currentMoney = Handler.loadMoney();
        Handler.loadInvestments(InvestmentIdRank);
        Handler.loadUpGrades(UpgradeIdUnlocked);
        nextAllowedGamblingDate.setTimeInMillis(Handler.loadNextAllowedGamblingDate());
        if (nextAllowedGamblingDate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() > 0) {
            isGamblingTimerRunning = true;
        }
        DisplayableUpgradeId = new ArrayList<>();
        Handler.loadDisplayedUpgades(DisplayableUpgradeId);
        for (Integer id : DisplayableUpgradeId) {
            Game.Get().setUpgrades(id);
        }
        Handler.closeDatabase(dbReadable);
    }

    public boolean getIsGamblingTimerRunning() {
        return isGamblingTimerRunning;
    }

    public void setIsGamblingTimerRunning(boolean isGamblingTimerRunning) {
        this.isGamblingTimerRunning = isGamblingTimerRunning;
    }
}
