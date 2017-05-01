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
                "Lemonade stand",              //NAME
                10,                 //BASE PRICE
                0.1,                //BASE MONEY PER SECOND
                "Home made lemonade with fresh lemon is the best refreshing drink in the summer.", //DESCRIPTION
                new int[]{0, 1, 2},      //UPGRADES THAT AFFECT THIS INVESTMENT
                currentGame,
                R.drawable.lemonadestand      //ICON
        ), map);
        AddToMap(new Investment(
                1,
                "Trampoline",
                80,
                0.5,
                "A cheap trampoline that children can use next to a playground for a small price.",
                new int[]{0, 1, 2},
                currentGame,
                R.drawable.trampoline
        ), map);
        AddToMap(new Investment(
                2,
                "Pedalo",
                400,
                1,
                "Get a used pedalo and rent it out on the beach, it's a lot of fun.",
                new int[]{0, 1, 2},
                currentGame,
                R.drawable.pedalo
        ), map);
        AddToMap(new Investment(
                3,
                "Bouncy Castle",
                900,
                2,
                "One of every children's favourite activities, place it in a park and enjoy the income.",
                new int[]{0, 1, 2},
                currentGame,
                R.drawable.bouncycastle
        ), map);
        AddToMap(new Investment(
                4,
                "Ice Cream Kiosk",
                2000,
                5,
                "Make delicious ice creams in a small kiosk, people will love it.",
                new int[]{6, 7, 8},
                currentGame,
                R.drawable.icecream
        ), map);
        AddToMap(new Investment(
                5,
                "Photo Studio",
                4400,
                10,
                "Professional environment for photographers.",
                new int[]{0, 1, 2},
                currentGame,
                R.drawable.photostudio
        ), map);
        AddToMap(new Investment(
                6,
                "Hot Dog Truck",
                12800,
                35,
                "When hungry people see this car, they cannot resist to buy a delicious hot-dog.",
                new int[]{0, 1, 2},
                currentGame,
                R.drawable.hotdogtruck
        ), map);
        AddToMap(new Investment(
                7,
                "Race Car Simulator",
                31000,
                90,
                "For those who are curious how it feels like driving a race car.",
                new int[]{9, 10, 11},
                currentGame,
                R.drawable.racecarsimulator
        ), map);
        AddToMap(new Investment(
                8,
                "Apartment",
                86600,
                250,
                "Buy apartments that you rent out to people.",
                new int[]{12, 13, 14},
                currentGame,
                R.drawable.apartment
        ), map);
        AddToMap(new Investment(
                9,
                "Bank",
                500000,
                700,
                "Take people's money, and invest it better than they can.",
                new int[]{12, 13, 14},
                currentGame,
                R.drawable.bank
        ), map);
        AddToMap(new Investment(
                10,
                "Casino",
                30000000,
                150000,
                "Manage a casino to get a lot of money from rich risk takers.",
                new int[]{12, 13, 14},
                currentGame,
                R.drawable.casino
        ), map);
        AddToMap(new Investment(
                11,
                "Amusement park",
                170000000,
                600000,
                "An amusement park attracts thousands of visitors daily.",
                new int[]{12, 13, 14},
                currentGame,
                R.drawable.funfair
        ), map);
        AddToMap(new Investment(
                12,
                "Hotel Chain",
                1500000000,
                4000000,
                "Tourist have to stay somewhere. Let's be it one of your hotels.",
                new int[]{15, 16, 17},
                currentGame,
                R.drawable.hotel_chain
        ), map);
        AddToMap(new Investment(
                13,
                "Oil Rig",
                6000000000d,
                500000000,
                "Dig deep enough in your garden, and find a valuable material, called oil.",
                new int[]{18, 19, 20},
                currentGame,
                R.drawable.oilrig
        ), map);

        return map;
    }

}
