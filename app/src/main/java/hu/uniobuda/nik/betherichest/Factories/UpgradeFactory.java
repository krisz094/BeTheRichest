package hu.uniobuda.nik.betherichest.Factories;

import android.graphics.Color;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;

import hu.uniobuda.nik.betherichest.Effects.AdderEffect;
import hu.uniobuda.nik.betherichest.Effects.DoublerEffect;
import hu.uniobuda.nik.betherichest.Effects.GlobalIncrementEffect;
import hu.uniobuda.nik.betherichest.Effects.MultiplierEffect;
import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;
import hu.uniobuda.nik.betherichest.GameObjects.Upgrade;
import hu.uniobuda.nik.betherichest.R;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class UpgradeFactory {

    private static void AddToMap(Upgrade upgrade, HashMap<Integer, Upgrade> map) {
        map.put(upgrade.getId(), upgrade);
    }

    private static long Million(double input) {
        return Math.round(input * 1000000);
    }

    public static HashMap<Integer, Upgrade> createUpgrades(Game currentGame, List<Investment> investments) throws ArrayIndexOutOfBoundsException {
        HashMap<Integer, Upgrade> map = new HashMap<>();

        /* Colors config */
        int[] colors = new int[]{
                Color.rgb(255, 255, 255),   // Common White
                Color.rgb(30, 255, 0),      // Uncommon Green
                Color.rgb(0, 112, 221),     // Rare Blue
                Color.rgb(163, 53, 238),    // Epic Purple
                Color.rgb(255, 128, 0),     // Legendary Orange
                Color.rgb(220, 20, 60),     // Crimson
                Color.rgb(0, 255, 255),     // Cyan
                Color.rgb(252, 15, 192)     // Shocking Pink
        };

        /* The following arrays' length must NOT exceed the length of the colors' array */
        /* Begin config */
        int[] multipliers = new int[]{1, 5, 50, 5000 };
        int[] rankOfIdNeeded = new int[]{1, 5, 25, 50 };

        //Click doubler config
        long[] clickDoublerPrices = new long[]{100, 500, 5000};

        //Click global incrementer config
        final long[] globalIncrementPrices = new long[]{10000, Million(0.1), Million(0.5), Million(5), Million(50), Million(500), Million(5000), Million(50000)};
        final double[] moneyForEachGlobalIncrement = new double[]{0.1, 0.5, 5, 50, 500, 5000, 50000, 500000};

        //Total money per sec upgrades
        //final long[] MPSUpgradePrices = new long[]{Million(0.5), Million(1), Million(2.5), Million(5), Million(10)};
        //final double[] MPSUpgradePercent = new double[]{1.01, 1.02, 1.03, 1.04, 1.05};

        //Gambling reward doubler prices
        final long[] GamblingRewardDoublerPrices = new long[]{Million(0.5), Million(1), Million(10), Million(50), Million(100), Million(200), Million(500), Million(1000)};

        //Gambling chance increment prices + values
        final long[] GamblingChanceDoublerPrices = new long[]{Million(0.5), Million(1), Million(10), Million(50), Million(100), Million(200), Million(500), Million(1000)};
        final double[] GamblingChanceIncrements = new double[]{5, 5, 5, 5, 5, 5, 5, 5}; // In percents
        /* End config */

        if (
                multipliers.length != rankOfIdNeeded.length ||
                        globalIncrementPrices.length != moneyForEachGlobalIncrement.length ||
                        //MPSUpgradePrices.length != MPSUpgradePercent.length ||
                        GamblingChanceDoublerPrices.length != GamblingChanceIncrements.length ||
                        multipliers.length > colors.length ||
                        clickDoublerPrices.length > colors.length ||
                        globalIncrementPrices.length > colors.length ||
                        //MPSUpgradePrices.length > colors.length ||
                        GamblingRewardDoublerPrices.length > colors.length ||
                        GamblingChanceDoublerPrices.length > colors.length
                ) {
            throw new ArrayIndexOutOfBoundsException();
        }

        /* Begin factory */
        int currId = 0;
        Integer prevId;
        /*Investment relevant upgrades*/
        for (Investment investment : investments) {
            long basePrice = investment.getBasePrice() * 10;
            prevId = null;
            int multiplIdx = 0;
            for (int multiplier : multipliers) {
                List<ConditionalProvider> conditions = new ArrayList<>();
                conditions.add(new RankOfIDNeeded(investment.getId(), rankOfIdNeeded[multiplIdx]));
//                if (prevId != null) {
//                    conditions.add(new UpgradeWithIDUnlocked(prevId));
//                }
                ConditionalProvider[] conditionsArray = new ConditionalProvider[conditions.size()];
                conditions.toArray(conditionsArray);
                AddToMap(new Upgrade(
                        currId,
                        "",
                        basePrice * multiplier,
                        new DoublerEffect(),
                        conditionsArray,
                        currentGame,
                        investment.getImageResource(),
                        colors[multiplIdx]
                ), map);
                prevId = currId;
                investment.addRelevantUpgrade(currId);
                multiplIdx++;
                currId++;
            }
            currId += 100;
        }

        currId = 100000;
        /*Click relevant upgrades*/
        int multiplIdx = 0;
        prevId = null;
        for (long price : clickDoublerPrices) {
            List<ConditionalProvider> conditions = new ArrayList<>();
//            if (prevId != null) {
//                conditions.add(new UpgradeWithIDUnlocked(prevId));
//            }
            ConditionalProvider[] conditionsArray = new ConditionalProvider[conditions.size()];
            conditions.toArray(conditionsArray);
            AddToMap(new Upgrade(
                    currId,
                    "",
                    price,
                    new DoublerEffect(),
                    conditionsArray,
                    currentGame,
                    R.drawable.click,
                    colors[multiplIdx]
            ), map);
            currentGame.addClickRelevantUpgrade(currId);
            prevId = currId;
            multiplIdx++;
            currId++;
        }

        currId = 200000;
        multiplIdx = 0;
        prevId = null;
        for (long price : globalIncrementPrices) {
            List<ConditionalProvider> conditions = new ArrayList<>();
//            if (prevId != null) {
//                conditions.add(new UpgradeWithIDUnlocked(prevId));
//            }
            ConditionalProvider[] conditionsArray = new ConditionalProvider[conditions.size()];
            conditions.toArray(conditionsArray);
            AddToMap(new Upgrade(
                    currId,
                    "",
                    price,
                    new GlobalIncrementEffect(currentGame, moneyForEachGlobalIncrement[multiplIdx]),
                    conditionsArray,
                    currentGame,
                    R.drawable.click,
                    colors[multiplIdx]
            ), map);
            currentGame.addClickRelevantUpgrade(currId);
            prevId = currId;
            multiplIdx++;
            currId++;
        }

/*
        currId = 300000;
        multiplIdx = 0;
        prevId = null;
        for (long price : MPSUpgradePrices) {
            List<ConditionalProvider> conditions = new ArrayList<>();
//            if (prevId != null) {
//                conditions.add(new UpgradeWithIDUnlocked(prevId));
//            }
            ConditionalProvider[] conditionsArray = new ConditionalProvider[conditions.size()];
            conditions.toArray(conditionsArray);
            AddToMap(new Upgrade(
                    currId,
                    "",
                    price,
                    new MultiplierEffect(MPSUpgradePercent[multiplIdx]),
                    conditionsArray,
                    currentGame,
                    R.drawable.dollarsmall,
                    colors[multiplIdx]
            ), map);
            currentGame.addMPSRelevantUpgrade(currId);
            prevId = currId;
            multiplIdx++;
            currId++;
        }
*/

        currId = 400000;
        multiplIdx = 0;
        //prevId = null;
        for (long price : GamblingRewardDoublerPrices) {
            AddToMap(new Upgrade(
                    currId,
                    "",
                    price,
                    new DoublerEffect(),
                    new ConditionalProvider[]{},
                    currentGame,
                    R.drawable.clover,
                    colors[multiplIdx]
            ), map);
            currentGame.addGamblingRewardRelevantUpgrade(currId);
            //prevId = currId;
            multiplIdx++;
            currId++;
        }

        currId = 500000;
        multiplIdx = 0;
        for (long price : GamblingChanceDoublerPrices) {
            AddToMap(new Upgrade(
                    currId,
                    "",
                    price,
                    new AdderEffect(GamblingChanceIncrements[multiplIdx]),
                    new ConditionalProvider[]{},
                    currentGame,
                    R.drawable.clover,
                    colors[multiplIdx]
            ), map);
            currentGame.addGamblingChanceRelevantUpgrade(currId);
            //prevId = currId;
            multiplIdx++;
            currId++;
        }

        return map;
    }

}
