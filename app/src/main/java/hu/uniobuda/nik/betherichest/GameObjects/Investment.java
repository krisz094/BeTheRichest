package hu.uniobuda.nik.betherichest.GameObjects;


import java.util.ArrayList;

/**
 * Created by Szabi on 2017.03.31..
 */


public class Investment {
    static int currentId = 0;
    private int id;
    private String name;
    private double basePrice;
    private double baseDpS;
    private String description;
    private ArrayList<Integer> relevantUpgradeIDs;
    private Game currentGame;
    private final double coeff = 1.15;
    private int imageResource;

    public Investment(String name, double basePrice, double baseDpS, String description, Game currentGame, int imageResource) {
        this.id = currentId++;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        this.relevantUpgradeIDs = new ArrayList<Integer>();
        this.currentGame = currentGame;
        this.imageResource = imageResource;
    }

    public void addRelevantUpgrade(Integer id) {
        this.relevantUpgradeIDs.add(id);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the current rank of this investment
     *
     * @return Current rank
     */
    public int getRank() {
        return currentGame.gameState.getInvestmentRankById(id);
    }

    public long getBasePrice() {
        return Math.round(basePrice);
    }

    public double getPrice() {
        return (double) Math.round(basePrice * Math.pow(coeff, getRank()));
    }

    /**
     * Gets money made by this investment per second, including the effect of upgrades
     *
     * @return Money Made Per Second
     */
    public double getMoneyPerSec() {
        double MPS = getRank() * baseDpS;
        for (int ID : relevantUpgradeIDs) {
            if (currentGame.gameState.getUpgradeBoughtById(ID)) {
                //MPS = currentGame.getUpgrades().get(ID).execute(MPS);
                MPS = currentGame.getUpgradeById(ID).execute(MPS);
            }
        }
        return MPS;
    }

    public double getMoneyPerSecPerRank() {
        double MPS = baseDpS;
        for (int ID : relevantUpgradeIDs) {
            if (currentGame.gameState.getUpgradeBoughtById(ID)) {
                MPS = currentGame.getUpgradeById(ID).execute(MPS);
            }
        }
        return MPS;
    }

    public boolean buy() {
        if (isBuyable()) {
            currentGame.buyInvestment(id);
            return true;
        }
        return false;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isBuyable() {
        return currentGame.getCurrentMoney() >= getPrice();
    }

    public double getDPSPercentage() {
        return currentGame.getMoneyPerSec() == 0 ? 0 : getMoneyPerSec() / currentGame.getMoneyPerSec() * 100;
    }
}
