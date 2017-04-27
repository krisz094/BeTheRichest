package hu.uniobuda.nik.betherichest.Factories;

import java.util.HashMap;

import hu.uniobuda.nik.betherichest.Effects.AdderEffect;
import hu.uniobuda.nik.betherichest.Effects.DoublerEffect;
import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Upgrade;
import hu.uniobuda.nik.betherichest.R;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class UpgradeFactory {

    private static void AddToMap(Upgrade upgrade, HashMap<Integer, Upgrade> map) {
        map.put(upgrade.getId(), upgrade);
    }

    public static HashMap<Integer, Upgrade> createUpgrades(Game currentGame) {
        HashMap<Integer, Upgrade> map = new HashMap<>();

        // PIZZA UPGRADES - ID: 0

        AddToMap(new Upgrade(
                0,                          //ID
                "Pizza upgrade 1",            //NAME
                "",                         //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{new RankOfIDNeeded(0, 1)}, //CONDITIONS
                currentGame,                //CURRENT GAME
                R.drawable.pizza1s      //IMAGE
        ), map);

        AddToMap(new Upgrade(
                1,
                "Pizza upgrade 2",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(0), new HaveMoreMoneyThan(500d), new RankOfIDNeeded(0, 10)},
                currentGame,
                R.drawable.pizza2s
        ), map);

        AddToMap(new Upgrade(
                2,
                "Pizza upgrade 3",
                "",
                10000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(1), new HaveMoreMoneyThan(8000d), new RankOfIDNeeded(0, 20)},
                currentGame,
                R.drawable.pizza3s
        ), map);

        // CLICK UPGRADES

        AddToMap(new Upgrade(
                3,
                "Double click",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{},
                currentGame,
                0
        ), map);

        AddToMap(new Upgrade(
                4,
                "Double click 2",
                "",
                5000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(3)},
                currentGame,
                0
        ), map);

        AddToMap(new Upgrade(
                5,
                "Click +100",
                "",
                5000,
                new AdderEffect(100d),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(4)},
                currentGame,
                0
        ), map);

        // ICE CREAM UPGRADES

        AddToMap(new Upgrade(
                6,                          //ID
                "Ice cream upgrade 1",            //NAME
                "",                         //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{new RankOfIDNeeded(1, 1)}, //CONDITIONS
                currentGame,                //CURRENT GAME
                R.drawable.icecream1s      //IMAGE
        ), map);

        AddToMap(new Upgrade(
                7,
                "Ice cream upgrade 2",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(6), new HaveMoreMoneyThan(500d), new RankOfIDNeeded(1, 10)},
                currentGame,
                R.drawable.icecream2s
        ), map);

        AddToMap(new Upgrade(
                8,
                "Ice cream upgrade 3",
                "",
                10000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(7), new HaveMoreMoneyThan(8000d), new RankOfIDNeeded(1, 20)},
                currentGame,
                R.drawable.icecream3s
        ), map);

        // APARTMENT UPGRADES

        AddToMap(new Upgrade(
                9,                          //ID
                "Apartment upgrade 1",            //NAME
                "",                         //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{new RankOfIDNeeded(2, 1)}, //CONDITIONS
                currentGame,                //CURRENT GAME
                R.drawable.apartment1s      //IMAGE
        ), map);

        AddToMap(new Upgrade(
                10,
                "Apartment upgrade 2",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(9), new HaveMoreMoneyThan(500d), new RankOfIDNeeded(2, 10)},
                currentGame,
                R.drawable.apartment2s
        ), map);

        AddToMap(new Upgrade(
                11,
                "Apartment upgrade 3",
                "",
                10000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(10), new HaveMoreMoneyThan(8000d), new RankOfIDNeeded(2, 20)},
                currentGame,
                R.drawable.apartment3s
        ), map);

        // BANK UPGRADES

        AddToMap(new Upgrade(
                12,                          //ID
                "Bank upgrade 1",            //NAME
                "",                         //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{new RankOfIDNeeded(3, 1)}, //CONDITIONS
                currentGame,                //CURRENT GAME
                R.drawable.bank1s      //IMAGE
        ), map);

        AddToMap(new Upgrade(
                13,
                "Bank upgrade 2",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(12), new HaveMoreMoneyThan(500d), new RankOfIDNeeded(3, 10)},
                currentGame,
                R.drawable.bank2s
        ), map);

        AddToMap(new Upgrade(
                14,
                "Bank upgrade 3",
                "",
                10000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(13), new HaveMoreMoneyThan(8000d), new RankOfIDNeeded(3, 20)},
                currentGame,
                R.drawable.bank3s
        ), map);

        // HOTEL CHAIN UPGRADES

        AddToMap(new Upgrade(
                15,                          //ID
                "Hotel chain upgrade 1",            //NAME
                "",                         //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{new RankOfIDNeeded(4, 1)}, //CONDITIONS
                currentGame,                //CURRENT GAME
                R.drawable.hotel1s     //IMAGE
        ), map);

        AddToMap(new Upgrade(
                16,
                "Hotel chain upgrade 2",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(15), new HaveMoreMoneyThan(500d), new RankOfIDNeeded(4, 10)},
                currentGame,
                R.drawable.hotel2s
        ), map);

        AddToMap(new Upgrade(
                17,
                "Hotel chain upgrade 3",
                "",
                10000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(16), new HaveMoreMoneyThan(8000d), new RankOfIDNeeded(4, 20)},
                currentGame,
                R.drawable.hotel3s
        ), map);

        // OIL RIG UPGRADES

        AddToMap(new Upgrade(
                18,                          //ID
                "Oil rig upgrade 1",            //NAME
                "",                         //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{new RankOfIDNeeded(5, 1)}, //CONDITIONS
                currentGame,                //CURRENT GAME
                R.drawable.oilrig1s      //IMAGE
        ), map);

        AddToMap(new Upgrade(
                19,
                "Oil rig upgrade 2",
                "",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(18), new HaveMoreMoneyThan(500d), new RankOfIDNeeded(5, 10)},
                currentGame,
                R.drawable.oilrig2s
        ), map);

        AddToMap(new Upgrade(
                20,
                "Oil rig upgrade 3",
                "",
                10000,
                new DoublerEffect(),
                new ConditionalProvider[]{new UpgradeWithIDUnlocked(19), new HaveMoreMoneyThan(8000d), new RankOfIDNeeded(5, 20)},
                currentGame,
                R.drawable.oilrig3s
        ), map);

        return map;
    }

}
