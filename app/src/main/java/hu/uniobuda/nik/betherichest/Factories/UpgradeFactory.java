package hu.uniobuda.nik.betherichest.Factories;

import android.graphics.Color;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.uniobuda.nik.betherichest.Effects.AdderEffect;
import hu.uniobuda.nik.betherichest.Effects.DoublerEffect;
import hu.uniobuda.nik.betherichest.Effects.GlobalIncrementEffect;
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

    public static HashMap<Integer, Upgrade> createUpgrades(Game currentGame, List<Investment> investments) {
        HashMap<Integer, Upgrade> map = new HashMap<>();

        /* Colors config */
        int[] colors = new int[]{         //standard rpg rarity colors (source: World of Warcraft)
                Color.rgb(255, 255, 255),   // white - common
                Color.rgb(30, 255, 0),      // green - uncommon
                Color.rgb(0, 112, 221),   // blue - rare
                Color.rgb(163, 53, 238),   // purple - epic
                Color.rgb(255, 128, 0),   // orange - legendary
                Color.rgb(230, 204, 255)    // light orange - artifact
        };

        /* The following arrays' length must NOT exceed the length of the colors' array */
        /* Begin config */
        int[] multipliers = new int[]{1, 5, 50, 5000};
        int[] rankOfIdNeeded = new int[]{1, 5, 25, 50};

        //Click doubler config
        long[] clickDoublerPrices = new long[]{100, 500, 10000};

        //Click global incrementer config
        final long[] globalIncrementPrices = new long[]{100000, 500000, 10000000, 1000000000};
        final double[] moneyForEachGlobalIncrement = new double[]{0.1, 0.5, 5, 50};
        /* End config */

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
                if (prevId != null) {
                    conditions.add(new UpgradeWithIDUnlocked(prevId));
                }
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
        }

        currId = 100000;
        /*Click relevant upgrades*/
        int multiplIdx = 0;
        prevId = null;
        for (long price : clickDoublerPrices) {
            List<ConditionalProvider> conditions = new ArrayList<>();
            if (prevId != null) {
                conditions.add(new UpgradeWithIDUnlocked(prevId));
            }
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
            if (prevId != null) {
                conditions.add(new UpgradeWithIDUnlocked(prevId));
            }
            ConditionalProvider[] conditionsArray = new ConditionalProvider[conditions.size()];
            conditions.toArray(conditionsArray);
            AddToMap(new Upgrade(
                    currId,
                    "",
                    price,
                    new GlobalIncrementEffect(currentGame, moneyForEachGlobalIncrement[multiplIdx]),
                    conditionsArray,
                    currentGame,
                    R.drawable.dollar,
                    colors[multiplIdx]
            ), map);
            currentGame.addClickRelevantUpgrade(currId);
            prevId = currId;
            multiplIdx++;
            currId++;
        }
        return map;
    }

}
