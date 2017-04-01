package hu.uniobuda.nik.betherichest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.uniobuda.nik.betherichest.Effects.DoublerEffect;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class UpgradeFactory {

    public static HashMap<Integer,Upgrade> CreateUpgrades() {
        HashMap<Integer,Upgrade> map = new HashMap<>();

        map.put(0,new Upgrade(
                0,                      //ID
                "something",            //NAME
                "costs 100, has a doubling effect",  //DESCRIPTION
                100,                    //PRICE
                new DoublerEffect()     //EFFECT
        ));

        map.put(1,new Upgrade(
                1,
                "something2",
                "costs 500 money, has a doubling effect",
                500,
                new DoublerEffect()
        ));

        map.put(2,new Upgrade(
                2,
                "double click",
                "costs 1000 money, has a doubling effect",
                1000,
                new DoublerEffect()
        ));


        return map;
    }

}
