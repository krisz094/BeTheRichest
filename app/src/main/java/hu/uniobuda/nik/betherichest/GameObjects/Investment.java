package hu.uniobuda.nik.betherichest.GameObjects;

import android.widget.Toast;

import hu.uniobuda.nik.betherichest.Factories.UpgradeFactory;
import hu.uniobuda.nik.betherichest.MainActivity;

/**
 * Created by Szabi on 2017.03.31..
 */


public class Investment {
    private String name;
    private double basePrice;
    private double baseDpS;
    private String description;
    private int id;
    private int[] relevantUpgradeIDs;
    private Game currentGame;
    private final double coeff = 1.15;
    private int imageResource;


    public Investment(int id, String name, double basePrice, double baseDpS, String description, int[] relevantUpgradeIDs, Game currentGame, int imageResource) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        this.relevantUpgradeIDs = relevantUpgradeIDs;
        this.currentGame = currentGame;
        this.imageResource = imageResource;
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

    public double getPrice() {
        return (double) Math.round(basePrice * Math.pow(coeff, getRank()));
    }

    /**
     * Gets money made by this investment per second, including the effect of upgrades
     *
     * @return Money Made Per Second
     */
    public Double getMoneyPerSec() {
        Double MPS = getRank() * baseDpS;
        for (Integer ID : relevantUpgradeIDs) {
            if (currentGame.gameState.getUpgradeBoughtById(ID)) {
                MPS = currentGame.getUpgrades().get(ID).execute(MPS);
            }
        }
        return MPS;
    }

    public Double getMoneyPerSecPerRank() {
        Double MPS = baseDpS;
        for (Integer ID : relevantUpgradeIDs) {
            if (currentGame.gameState.getUpgradeBoughtById(ID)) {
                MPS = currentGame.getUpgrades().get(ID).execute(MPS);
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
}
