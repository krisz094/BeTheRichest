package hu.uniobuda.nik.betherichest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.uniobuda.nik.betherichest.Effects.DoublerEffect;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class UpgradeFactory {

    private static void AddToMap(Upgrade upgrade, HashMap<Integer, Upgrade> map) {
        map.put(upgrade.id, upgrade);
    }
/*
    private static Upgrade Create(int id, String name,String desc, int price, String effect, String conditions, Game currentGame) {
        IEffectable Effect;
        switch(effect.toLowerCase()) {
            case "doubler":
                Effect = new DoublerEffect();
                break;
            default:
                throw new IllegalArgumentException();
                break;
        }
        List<ConditionalProvider> conditionsList = new ArrayList<>();

        for(String condition: conditions.split(",")) {
            switch(condition) {
                case ""
            }
        }


        return new Upgrade(id,name,desc, price, Effect,,currentGame);
    }*/

    public static HashMap<Integer, Upgrade> CreateUpgrades(Game currentGame) {
        HashMap<Integer, Upgrade> map = new HashMap<>();

        AddToMap(new Upgrade(
                0,                          //ID
                "something",                //NAME
                "costs 100, has a doubling effect",  //DESCRIPTION
                100,                        //PRICE
                new DoublerEffect(),        //EFFECT
                new ConditionalProvider[]{},//CONDITIONS
                currentGame                 //CURRENT GAME
        ), map);

        AddToMap(new Upgrade(
                1,
                "something2",
                "costs 500 money, has a doubling effect",
                500,
                new DoublerEffect(),
                new ConditionalProvider[]{},
                currentGame
        ), map);

        AddToMap(new Upgrade(
                2,
                "double click",
                "costs 1000 money, has a doubling effect (on clicking)",
                1000,
                new DoublerEffect(),
                new ConditionalProvider[]{new RankOfIDNeeded(0, 1)},
                currentGame
        ), map);

        AddToMap(new Upgrade(
                3,
                "double click 2",
                "costs 2500 money, has a doubling effect (on clicking)",
                2500,
                new DoublerEffect(),
                new ConditionalProvider[]{new RankOfIDNeeded(0, 10), new UpgradeWithIDUnlocked(2)},
                currentGame
        ), map);

        return map;
    }

}
