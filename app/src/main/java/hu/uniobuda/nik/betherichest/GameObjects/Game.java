package hu.uniobuda.nik.betherichest.GameObjects;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.Factories.GamblingFactory;
import hu.uniobuda.nik.betherichest.Factories.InvestmentFactory;
import hu.uniobuda.nik.betherichest.Factories.LeadersFactory;
import hu.uniobuda.nik.betherichest.Factories.UpgradeFactory;
import hu.uniobuda.nik.betherichest.Interfaces.DatabaseHandler;

public class Game {

    private static Game instance;
    private static final double START_MONEY_PER_CLICK = 1d;
    private static final double START_MONEY = 0d;

    public static Integer FPS = 20;

    public static Game Get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public State gameState;
    public MoneyChangedListener onMoneyChanged;
    public MoneyChangedListener2 onMoneyChanged2;
    public Handler handler;
    private Double moneyPerSec;
    private Double moneyPerClick;
    private HashMap<Integer, Upgrade> upgrades;
    private HashMap<Integer, Investment> investments;
    private HashMap<Integer, Gambling> gamblings;
    private List<Leaders> leaders;
    private final int[] clickRelevantUpgradeIDs = {3, 4, 5}; //upgrade IDs that affect clicking
    private Timer T;

    private DecimalFormat df = new DecimalFormat("0.0");

    private Game() {
        T = new Timer();
        gameState = new State();
        moneyPerSec = 0d;
        moneyPerClick = 1d;
        upgrades = UpgradeFactory.createUpgrades(this);
        investments = InvestmentFactory.createInvestments(this);
        gamblings = GamblingFactory.createGamblings(this);

        this.handler = new Handler(Looper.getMainLooper());


        //gameState.loadState();
        recalcMoneyPerSec();
        recalcMoneyPerClick();
        StartTimer();
        //TODO: start timer that increments current money with money per sec
    }

    private void StartTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                earnMoney(getMoneyPerSec() / FPS);
                postMoneyChanged(getCurrentMoneyAsString());

            }
        }, 0, 1000 / FPS);


        T.schedule(new TimerTask() {
            @Override
            public void run() {
                postMoneyChanged2();
            }
        }, 0, 300);
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

    public Double getMoneyPerClick() {
        return moneyPerClick;
    }

    public String getMoneyPerSecAsString() {
        return df.format(moneyPerSec) + " $ per sec";
    }

    public String getMoneyPerClickAsString() {
        return moneyPerClick.intValue() + " $ per tap";
    }

    public Double getCurrentMoney() {
        return gameState.currentMoney;
    }

    public String getCurrentMoneyAsString() {
        //TODO formázni kell a double számot, úgy hogy ott legyenek az ezres elválasztó vesszők, az int csak ~2 miliárdig megy föl
        return String.valueOf(String.format("%,d", gameState.currentMoney.intValue()));
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

    public List<Gambling> getGamblings() {
        ArrayList<Gambling> list = new ArrayList<>(gamblings.values());
        Collections.sort(list, new Comparator<Gambling>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Gambling o1, Gambling o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return list;
    }

    public List<Leaders> getLeaders() throws IOException, XmlPullParserException {
        return LeadersFactory.getLeaders();
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
        recalcAll();
    }

    public void buyInvestment(Integer id) {
        deduceMoney(investments.get(id).getPrice());
        Integer currRank = gameState.getInvestmentRankById(id);
        currRank += 1;
        gameState.setInvestmentRankById(id, currRank);
        //deduceMoney((double) investments.get(id).getPrice());
        //refresh current MPS because there was a change
        recalcAll();
    }


    public void saveGame(DatabaseHandler Handler) {
        gameState.saveState(Handler);
    }


    public void loadGame(DatabaseHandler Handler) {
        gameState.loadState(Handler);
        recalcAll();
    }

    public void recalcAll() {
        recalcMoneyPerSec();
        recalcMoneyPerClick();
    }


    private void recalcMoneyPerSec() {
        Double money = START_MONEY;
        for (Investment inv : investments.values()) {
            money += inv.getMoneyPerSec();
        }
        moneyPerSec = money;
        if (onMoneyChanged != null) {
            onMoneyChanged.onMoneyPerSecChanged(getMoneyPerSecAsString());
        }
    }

    private void recalcMoneyPerClick() {
        Double money = START_MONEY_PER_CLICK;

        for (Integer upgradeID : clickRelevantUpgradeIDs) {
            if (gameState.getUpgradeBoughtById(upgradeID)) {
                money = upgrades.get(upgradeID).execute(money);
            }
        }

        moneyPerClick = money;
        if (onMoneyChanged != null) {
            onMoneyChanged.onMoneyPerTapChanged(getMoneyPerClickAsString());
        }
    }


    public void postMoneyChanged(final String totalMoney) {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                if (onMoneyChanged != null) {
                    onMoneyChanged.onTotalMoneyChanged(getCurrentMoneyAsString());
                }
            }
        });
    }

    public void setOnMoneyChanged(MoneyChangedListener onMoneyChanged) {
        this.onMoneyChanged = onMoneyChanged;
    }

    public interface MoneyChangedListener {
        void onTotalMoneyChanged(String totalMoney);

        void onMoneyPerTapChanged(String moneyPerTap);

        void onMoneyPerSecChanged(String moneyPerSec);
    }

    public void setOnMoneyChanged2(MoneyChangedListener2 onMoneyChanged2) {
        this.onMoneyChanged2 = onMoneyChanged2;
    }

    public void postMoneyChanged2() {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                if (onMoneyChanged2 != null) {
                    onMoneyChanged2.onTotalMoneychanged2();
                }
            }
        });
    }

    public interface MoneyChangedListener2 {
        void onTotalMoneychanged2();
    }

}
