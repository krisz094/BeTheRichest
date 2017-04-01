package hu.uniobuda.nik.betherichest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by krisz on 2017. 04. 01..
 */

class State {
    protected Double currentMoney;
    //upgradeBoughtById
    protected HashMap<Integer, Boolean> UpgradeIdUnlocked;
    //InvestmentRankById
    protected HashMap<Integer, Integer> InvestmentIdRank;

    public State() {
        currentMoney = 0d;
        UpgradeIdUnlocked = new HashMap<>();
        InvestmentIdRank = new HashMap<>();
    }

    public Boolean GetUpgradeBoughtById(int id) {
        if (UpgradeIdUnlocked.get(id) == null) return false;
        return UpgradeIdUnlocked.get(id);
    }

    public void SetUpgradeAsBought(int id) {
        UpgradeIdUnlocked.put(id, true);
    }

    public int GetInvestmentRankById(int id) {
        if (InvestmentIdRank.get(id) == null) return 0;
        return InvestmentIdRank.get(id);
    }

    public void SetInvestmentRankById(int id, int rank) {
        InvestmentIdRank.put(id, rank);
    }

    public void SaveState() {
        //TODO
    }

    public void LoadState() {
        //TODO
    }
}

public class Game {

    private static Game instance;

    public static Game Get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    protected State gameState;
    protected Double moneyPerSec;
    protected Double moneyPerClick;
    protected HashMap<Integer, Upgrade> upgrades;
    protected HashMap<Integer, Investment> investments;
    protected final int[] clickRelevantUpgradeIDs = {2, 3}; //upgrade IDs that affect clicking
    protected Timer T;

    private Game() {
        T = new Timer();
        gameState = new State();
        moneyPerSec = 0d;
        moneyPerClick = 1d;
        upgrades = UpgradeFactory.CreateUpgrades(this);
        investments = InvestmentFactory.CreateInvestments(this);

        gameState.LoadState();
        RecalcMoneyPerSec();
        RecalcMoneyPerClick();
        StartTimer();
        //TODO: start timer that increments current money with money per sec
    }

    public void StartTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                EarnMoney(GetMoneyPerSec() / 10);
            }
        }, 0, 1000 / 10);
    }

    public void EarnMoney(Double money) {
        gameState.currentMoney += money;
    }

    public void Click() {
        EarnMoney(moneyPerClick);
    }

    public Double GetMoneyPerSec() {
        return moneyPerSec;
    }
    public String GetMoneyPerSecAsString()
    {
        return "Money/sec: " + moneyPerSec.toString();
    }

    public Double GetMoneyPerClick() {
        return moneyPerClick;
    }
    public String GetMoneyPerClickAsString(){
        return "Money/tap: " + moneyPerClick.toString();
    }

    public String GetCurrentMoney() {
        return String.valueOf(Math.round(gameState.currentMoney));
    }


    public List<Investment> GetInvestments() {
        return new ArrayList<>(investments.values());
    }

    public List<Upgrade> GetUpgrades() {
        return new ArrayList<>(upgrades.values());
    }

    public void BuyUpgrade(Integer id) {
        gameState.SetUpgradeAsBought(id);

        //refresh current MPS because there was a change
        RecalcMoneyPerSec();
        RecalcMoneyPerClick();
    }

    public void BuyInvestment(Integer id, Integer howManyRanks) {
        Integer currRank = gameState.GetInvestmentRankById(id);
        currRank += howManyRanks;
        gameState.SetInvestmentRankById(id, currRank);

        //refresh current MPS because there was a change
        RecalcMoneyPerSec();
        RecalcMoneyPerClick();
    }


    protected void RecalcMoneyPerSec() {
        Double money = 0d;
        for (Investment inv : investments.values()) {
            money += inv.GetMoneyPerSec();
        }
        moneyPerSec = money;
    }

    protected void RecalcMoneyPerClick() {
        Double money = 1d;

        for (Integer upgradeID : clickRelevantUpgradeIDs) {
            if (gameState.GetUpgradeBoughtById(upgradeID)) {
                money = upgrades.get(upgradeID).Execute(money);
            }
        }

        moneyPerClick = money;
    }

    //OLD METHOD
    /*
    protected double getInvestmentMoneyPerSec(Investment investment) {
        //first we have to get the MPS of the investment itself
        double MPS = investment.getMPSForRank(gameState.GetInvestmentRankById(investment.id));
        //then we have to increase the MPS with the unlocked upgrades of the investment
        for(Integer upgradeID: investment.relevantUpgradeIDs){
            //statement below returns true, if upgrade with id is unlocked
            if (gameState.GetUpgradeBoughtById(upgradeID)) {
                // we modify the MPS with a function, eg. f(x) = 2x
                MPS = upgrades.get(upgradeID).Execute(MPS);
            }
        }
        return MPS;
    }


    protected void RecalcMoneyPerSec() {
        Double money = 0d;
        for(Investment inv: investments.values()) {
            money += getInvestmentMoneyPerSec(inv);
        }
        moneyPerSec = money;
    }
    */
}
