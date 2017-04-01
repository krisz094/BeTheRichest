package hu.uniobuda.nik.betherichest;

import android.graphics.Picture;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Szabi on 2017.03.31..
 */


public class Investment {
    String name;
    int basePrice;
    double baseDpS;
    String description;
    int id;
    int[] relevantUpgradeIDs;
    Game currentGame;
    final double coeff = 1.05;

    public Investment(int id, String name, int basePrice, double baseDpS, String description, int[] relevantUpgradeIDs, Game currentGame) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        this.relevantUpgradeIDs = relevantUpgradeIDs;
        this.currentGame = currentGame;
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
    public int GetRank() {
        return currentGame.gameState.GetInvestmentRankById(id);
    }

    public int GetPrice() {
        return (int) Math.round(basePrice * Math.pow(coeff, GetRank()));
    }

    /**
     * Gets money made by this investment per second, including the effect of upgrades
     *
     * @return Money Made Per Second
     */
    public Double GetMoneyPerSec() {
        Double MPS = Double.valueOf(GetRank() * baseDpS);
        for (Integer ID : relevantUpgradeIDs) {
            if (currentGame.gameState.GetUpgradeBoughtById(ID)) {
                MPS = currentGame.upgrades.get(ID).Execute(MPS);
            }
        }
        return MPS;
    }

    public boolean IsBuyable() {
        return currentGame.gameState.currentMoney >= GetPrice();
    }


}
