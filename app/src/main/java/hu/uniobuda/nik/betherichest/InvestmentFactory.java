package hu.uniobuda.nik.betherichest;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Szabi on 2017.03.31..
 */
public class InvestmentFactory {

    public static HashMap<Integer,Investment> createInvestments(Game currentGame) {
        HashMap<Integer,Investment> map = new HashMap<>();

        map.put(0,new Investment(
                0,      //ID
                "ASD",  //NAME
                15,     //BASE PRICE
                0.1,    //BASE MONEY PER SECOND
                "Put your money into the bank, for a small amount of interest.", //DESCRIPTION
                new int[] {0},   //UPGRADES THAT AFFECT THIS INVESTMENT
                currentGame
        ));

        map.put(1,new Investment(
                1,
                "Hamburger Stand",
                15,
                0.1,
                "Make delicious hamburgers on the street, to get some money from hungry people.",
                new int[] {1},
                currentGame
        ));

        return map;
    }

}
