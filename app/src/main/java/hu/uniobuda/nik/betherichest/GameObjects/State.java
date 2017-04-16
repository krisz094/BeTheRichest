package hu.uniobuda.nik.betherichest.GameObjects;

import java.util.HashMap;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class State {
    public Double currentMoney;
    //upgradeBoughtById
    private HashMap<Integer, Boolean> UpgradeIdUnlocked;
    //InvestmentRankById
    private HashMap<Integer, Integer> InvestmentIdRank;

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

    public void saveState() {
        //TODO
    }

    public void loadState() {
        //TODO
    }
}
