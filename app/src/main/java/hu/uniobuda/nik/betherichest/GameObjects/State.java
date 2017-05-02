package hu.uniobuda.nik.betherichest.GameObjects;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

import hu.uniobuda.nik.betherichest.Interfaces.DatabaseHandler;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class State {
    public Double currentMoney;
    //upgradeBoughtById
    private HashMap<Integer, Boolean> UpgradeIdUnlocked;
    //InvestmentRankById
    private HashMap<Integer, Integer> InvestmentIdRank; //ezt vissza majd privátra



    public State() {
        currentMoney = 0d;
        UpgradeIdUnlocked = new HashMap<>();
        InvestmentIdRank = new HashMap<>();

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

        Handler.deleteInvestments();
        Handler.deleteUpgrade();
        Handler.saveMoney(currentMoney);

        for(Map.Entry<Integer,Integer> entry : InvestmentIdRank.entrySet()) {
            Handler.saveInvestment(entry.getKey(),entry.getValue());
        }
        Integer a;
        for(Map.Entry<Integer,Boolean> entry : UpgradeIdUnlocked.entrySet()) {
            if(entry.getValue())
            {
                a=1;
            }
            else
            {
                a=0;
            }
            Handler.saveUpgrade(entry.getKey(),a);
        }
    }

    public void loadState(DatabaseHandler Handler) {
        //TODO

        currentMoney = Handler.loadMoney();
        Handler.loadInvestments(InvestmentIdRank);
        Handler.loadUpGrades(UpgradeIdUnlocked);

    }
}
