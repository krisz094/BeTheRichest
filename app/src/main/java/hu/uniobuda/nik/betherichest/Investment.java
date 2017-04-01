package hu.uniobuda.nik.betherichest;

import android.graphics.Picture;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Szabi on 2017.03.31..
 */


public class Investment {
    private String name;
    private int basePrice;
    private double baseDpS;
    private String description;
    private int id;
    private int[] relevantUpgradeIDs;
    private Game currentGame;
    private final double coeff = 1.05;

    public Investment(int id, String name, int basePrice, double baseDpS, String description, int[] relevantUpgradeIDs, Game currentGame) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        this.relevantUpgradeIDs = relevantUpgradeIDs;
        this.currentGame = currentGame;
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

    public int getPrice() {
        return (int) Math.round(basePrice * Math.pow(coeff, getRank()));
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

    public void buy() {
        if (isBuyable()) {
            currentGame.buyInvestment(id);
        }
    }

    public boolean isBuyable() {
        return currentGame.getCurrentMoney() >= getPrice();
    }


}
