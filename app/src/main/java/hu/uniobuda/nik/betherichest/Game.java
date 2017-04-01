package hu.uniobuda.nik.betherichest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
        return UpgradeIdUnlocked.get(id);
    }
    public void SetUpgradeAsBought(int id) {
        UpgradeIdUnlocked.put(id,true);
    }

    public int GetInvestmentRankById(int id) {
        return InvestmentIdRank.get(id);
    }
    public void SetInvestmentRankById(int id, int rank) {
        InvestmentIdRank.put(id,rank);
    }

    public void SaveState() {
        //TODO
    }

    public void LoadState() {
        //TODO
    }
}

public class Game {

    protected State gameState;
    protected Double moneyPerSec;
    protected Double moneyPerClick;
    protected HashMap<Integer,Upgrade> upgrades;
    protected HashMap<Integer,Investment> investments;
    protected final int[] clickRelevantUpgradeIDs = { 2 }; //upgrade IDs that affect clicking

    public Game() {
        gameState = new State();
        moneyPerSec = 0d;
        moneyPerClick = 1d;
        upgrades = UpgradeFactory.CreateUpgrades();
        investments = InvestmentFactory.CreateInvestments();

        gameState.LoadState();
        RecalcMoneyPerSec();
        RecalcMoneyPerClick();
        //TODO: start timer that increments current money with money per sec
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
    public Double GetMoneyPerClick() {
        return moneyPerClick;
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

    protected void RecalcMoneyPerClick() {
        Double money = 1d;

        for(Integer upgradeID: clickRelevantUpgradeIDs) {
            if (gameState.GetUpgradeBoughtById(upgradeID)) {
                money = upgrades.get(upgradeID).Execute(money);
            }
        }

        moneyPerClick = money;
    }

}
