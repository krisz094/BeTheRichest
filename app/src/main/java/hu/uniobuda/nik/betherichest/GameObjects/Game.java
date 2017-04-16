package hu.uniobuda.nik.betherichest.GameObjects;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.Factories.InvestmentFactory;
import hu.uniobuda.nik.betherichest.Factories.UpgradeFactory;

public class Game {

    private static Game instance;

    public static Game Get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public State gameState;
    private Double moneyPerSec;
    private Double moneyPerClick;
    private HashMap<Integer, Upgrade> upgrades;
    private HashMap<Integer, Investment> investments;
    private final int[] clickRelevantUpgradeIDs = {2, 3}; //upgrade IDs that affect clicking
    private Timer T;
    private DecimalFormat df = new DecimalFormat("0.0");

    private Game() {
        T = new Timer();
        gameState = new State();
        moneyPerSec = 0d;
        moneyPerClick = 1d;
        upgrades = UpgradeFactory.createUpgrades(this);
        investments = InvestmentFactory.createInvestments(this);

        gameState.loadState();
        recalcMoneyPerSec();
        recalcMoneyPerClick();
        StartTimer();
        //TODO: start timer that increments current money with money per sec
    }

    private void StartTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                earnMoney(getMoneyPerSec() / 10);
            }
        }, 0, 1000 / 10);
    }

    public void earnMoney(Double money) {
        gameState.currentMoney += money;
    }

    public void deduceMoney(Double money) {
        gameState.currentMoney -= money;
    }

    public void click() {
        earnMoney(moneyPerClick);
    }

    public Double getMoneyPerSec() {
        return moneyPerSec;
    }

    public String getMoneyPerSecAsString() {
        return df.format(moneyPerSec) + " dollars per sec";
    }

    public Double getMoneyPerClick() {
        return moneyPerClick;
    }

    public String getMoneyPerClickAsString() {

        return  df.format(moneyPerClick)+ " dollars per tap";
    }

    public Double getCurrentMoney() {
        return gameState.currentMoney;
    }

    public String getCurrentMoneyAsString() {
        return df.format(gameState.currentMoney) + " dollars";
    }


    public List<Investment> getInvestments() {
        ArrayList<Investment> list = new ArrayList<>(investments.values());
        Collections.sort(list, new Comparator<Investment>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Investment o1, Investment o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return list;
    }

    public List<Upgrade> getUpgrades() {
        ArrayList<Upgrade> list = new ArrayList<>(upgrades.values());
        Collections.sort(list, new Comparator<Upgrade>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Upgrade o1, Upgrade o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return list;
    }

    public void buyUpgrade(Integer id) {
        gameState.setUpgradeAsBought(id);
        deduceMoney((double) upgrades.get(id).getPrice());
        //refresh current MPS because there was a change
        recalcMoneyPerSec();
        recalcMoneyPerClick();
    }

    public void buyInvestment(Integer id) {
        Integer currRank = gameState.getInvestmentRankById(id);
        currRank += 1;
        gameState.setInvestmentRankById(id, currRank);
        deduceMoney((double) investments.get(id).getPrice());
        //refresh current MPS because there was a change
        recalcMoneyPerSec();
        recalcMoneyPerClick();
    }

    private void recalcMoneyPerSec() {
        Double money = 0d;
        for (Investment inv : investments.values()) {
            money += inv.getMoneyPerSec();
        }
        moneyPerSec = money;
    }

    private void recalcMoneyPerClick() {
        Double money = 1d;

        for (Integer upgradeID : clickRelevantUpgradeIDs) {
            if (gameState.getUpgradeBoughtById(upgradeID)) {
                money = upgrades.get(upgradeID).execute(money);
            }
        }

        moneyPerClick = money;
    }

}