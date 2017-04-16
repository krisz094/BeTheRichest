package hu.uniobuda.nik.betherichest;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Szabi on 2017.03.31..
 */
public class InvestmentFactory {

    private static void AddToMap(Investment investment, HashMap<Integer, Investment> map) {
        map.put(investment.getId(), investment);
    }

    public static HashMap<Integer, Investment> createInvestments(Game currentGame) {
        HashMap<Integer, Investment> map = new HashMap<>();

        /*
        List<Investment> items = new ArrayList<>();
        items.add(new Investment(1, "Pizza", 10, 30, "Finom", null, null, R.drawable.pizza));
        items.add(new Investment(2, "Ice Cream Shop", 15, 35, "Finom", null, null, R.drawable.icecream));
        items.add(new Investment(3, "Apartmann", 150, 100, "Finom", null, null, R.drawable.apartment));
        items.add(new Investment(4, "Bank", 800, 300, "Finom", null, null, R.drawable.bank));
        items.add(new Investment(4, "Hotel Chain", 1500, 360, "Finom", null, null, R.drawable.hotel_chain));
        items.add(new Investment(4, "Oil Rig", 15000, 3000, "Finom", null, null, R.drawable.oilrig));
        */


        AddToMap(new Investment(
                0,                  //ID
                "Pizza",              //NAME
                10,                 //BASE PRICE
                30,                //BASE MONEY PER SECOND
                "Make delicious pizza on the street, to get some money from hungry people.", //DESCRIPTION
                new int[]{},      //UPGRADES THAT AFFECT THIS INVESTMENT
                currentGame,
                R.drawable.pizza
        ), map);

        AddToMap(new Investment(
                1,
                "Ice Cream Shop",
                15,
                35,
                "Make delicious ice creams on the street, to get some money from hungry people.",
                new int[]{},
                currentGame,
                R.drawable.icecream
        ), map);
        AddToMap(new Investment(
                2,
                "Apartment",
                150,
                100,
                "Buy homes that you sell to people.",
                new int[]{},
                currentGame,
                R.drawable.apartment
        ), map);
        AddToMap(new Investment(
                3,
                "Bank",
                800,
                300,
                "Take people's money, and invest it better than they can.",
                new int[]{},
                currentGame,
                R.drawable.bank
        ), map);
        AddToMap(new Investment(
                4,
                "Hotel Chain",
                1500,
                360,
                "Buy hotels that you rent(nem biztos h ez a jo angol szo) to tourists.",
                new int[]{},
                currentGame,
                R.drawable.hotel_chain
        ), map);
        AddToMap(new Investment(
                5,
                "Oil Rig",
                15000,
                3000,
                "Dig deep enough in your garden, and find a valuable material, called oil.",
                new int[]{},
                currentGame,
                R.drawable.oilrig
        ), map);

        return map;
    }

}
