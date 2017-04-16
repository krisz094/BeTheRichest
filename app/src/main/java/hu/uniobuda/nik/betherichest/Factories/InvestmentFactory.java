package hu.uniobuda.nik.betherichest.Factories;

import java.util.HashMap;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;
import hu.uniobuda.nik.betherichest.R;

/**
 * Created by Szabi on 2017.03.31..
 */
public class InvestmentFactory {

    private static void AddToMap(Investment investment, HashMap<Integer, Investment> map) {
        map.put(investment.getId(), investment);
    }

    public static HashMap<Integer, Investment> createInvestments(Game currentGame) {
        HashMap<Integer, Investment> map = new HashMap<>();

        AddToMap(new Investment(
                0,                  //ID
                "Pizza",              //NAME
                10,                 //BASE PRICE
                0.1,                //BASE MONEY PER SECOND
                "Make delicious pizza on the street, to get some money from hungry people.", //DESCRIPTION
                new int[]{},      //UPGRADES THAT AFFECT THIS INVESTMENT
                currentGame,
                R.drawable.pizza
        ), map);

        AddToMap(new Investment(
                1,
                "Ice Cream Shop",
                100,
                0.5,
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
