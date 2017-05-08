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
                "Lemonade stand",              //NAME
                10,                 //BASE PRICE
                0.1,                //BASE MONEY PER SECOND
                "Home made lemonade with fresh lemon is the best refreshing drink in the summer.", //DESCRIPTION
                currentGame,
                R.drawable.lemonadestand      //ICON
        ), map);
        AddToMap(new Investment(
                "Trampoline",
                80,
                0.4,
                "A cheap trampoline that children can use next to a playground for a small price.",
                currentGame,
                R.drawable.trampoline
        ), map);
        AddToMap(new Investment(
                "Pedalo",
                320,
                1,
                "Get a used pedalo and rent it out on the beach, it's a lot of fun.",
                currentGame,
                R.drawable.pedalo
        ), map);
        AddToMap(new Investment(
                "Bouncy Castle",
                790,
                2.2,
                "One of every children's favourite activities, place it in a park and enjoy the income.",
                currentGame,
                R.drawable.bouncycastle
        ), map);
        AddToMap(new Investment(
                "Ice Cream Kiosk",
                2020,
                5,
                "Make delicious ice creams in a small kiosk, people will love it.",
                currentGame,
                R.drawable.icecream
        ), map);
        AddToMap(new Investment(
                "Photo Studio",
                4800,
                11,
                "Professional environment for photographers.",
                currentGame,
                R.drawable.photostudio
        ), map);
        AddToMap(new Investment(
                "Hot Dog Truck",
                12800,
                33,
                "When hungry people see this car, they cannot resist to buy a delicious hot-dog.",
                currentGame,
                R.drawable.hotdogtruck
        ), map);
        AddToMap(new Investment(
                "Race Car Simulator",
                31000,
                75,
                "For those who are curious how it feels like driving a race car.",
                currentGame,
                R.drawable.racecarsimulator
        ), map);
        AddToMap(new Investment(
                "Apartment",
                92000,
                220,
                "Buy apartments that you rent out to people.",
                currentGame,
                R.drawable.apartment
        ), map);
        AddToMap(new Investment(
                "Family House",
                229000,
                500,
                "Buy houses that you sell.",
                currentGame,
                R.drawable.familyhouse
        ), map);
        AddToMap(new Investment(
                "Yacht",
                468000,
                970,
                "",
                currentGame,
                R.drawable.yacht
        ), map);
        AddToMap(new Investment(
                "Server Farm",
                1076000,
                2150,
                "",
                currentGame,
                R.drawable.serverfarm
        ), map);
        AddToMap(new Investment(
                "Mine Truck",
                3640000,
                6110,
                "",
                currentGame,
                R.drawable.minetruck
        ), map);
        AddToMap(new Investment(
                "Private Airplane",
                6150000,
                9980,
                "",
                currentGame,
                R.drawable.airplane
        ), map);
        AddToMap(new Investment(
                "Office Building",
                10722000,
                16300,
                "",
                currentGame,
                R.drawable.officebuilding
        ), map);
        AddToMap(new Investment(
                "Luxury Real Estate",
                19000000,
                30100,
                "",
                currentGame,
                R.drawable.luxuryrealestate
        ), map);
        AddToMap(new Investment(
                "Casino",
                39999000,
                58200,
                "Manage a casino to get a lot of money from rich risk takers.",
                currentGame,
                R.drawable.casino
        ), map);
        AddToMap(new Investment(
                "Amusement park",
                65800000,
                98700,
                "An amusement park attracts thousands of visitors daily.",
                currentGame,
                R.drawable.funfair
        ), map);
        AddToMap(new Investment(
                "Hotel Chain",
                98900000,
                146000,
                "Tourist have to stay somewhere. Let's be it one of your hotels.",
                currentGame,
                R.drawable.hotelchain
        ), map);
        AddToMap(new Investment(
                "Oil Rig",
                150000000d,
                235000,
                "Dig deep enough in your garden, and find a valuable material, called oil.",
                currentGame,
                R.drawable.oilrig
        ), map);

        return map;
    }

}
