package hu.uniobuda.nik.betherichest.Factories;

import java.util.HashMap;

import hu.uniobuda.nik.betherichest.GameObjects.Gambling;
import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;
import hu.uniobuda.nik.betherichest.R;

/**
 * Created by Szabi on 2017.05.01..
 */

public class GamblingFactory {
    private static void AddToMap(Gambling gambling, HashMap<Integer, Gambling> map) {
        map.put(gambling.getId(), gambling);
    }

    public static HashMap<Integer, Gambling> createGamblings(Game currentGame) {
        HashMap<Integer, Gambling> map = new HashMap<>();

        AddToMap(new Gambling(
               "Scratch Off",
                "Scratch off the paper, and see if Fortuna is by your side.",
                200,
                1000,
                30,
                R.drawable.scratchoff,
                currentGame
        ), map);
        AddToMap(new Gambling(
                "Horse Race",
                "Bet on the fastest horses to win a cup of money",
                1000,
                5000,
                16,
                R.drawable.horserace,
                currentGame
        ), map);

//        AddToMap(new Gambling(
//                "Play Poker",
//                "Just don't forget to use your Poker Face",
//                10000,
//                50000,
//                10,
//                R.drawable.slotmachine,
//                currentGame
//        ), map);


        AddToMap(new Gambling(
                "Slot Machine",
                "Patience, patience, once you'll get the jackpot",
                20000,
                100000,
                1,
                R.drawable.slotmachine,
                currentGame
        ), map);

        return map;
    }
}
