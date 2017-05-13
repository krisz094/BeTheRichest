package hu.uniobuda.nik.betherichest.GameObjects;

/**
 * Created by Szabi on 2017.05.01..
 */

public class Gambling {
    private static int id;
    private int entityid;
    private String name;
    private String description;
    private int minWinAmount;
    private int maxWinAmount;
    private double chance;
    private int imageResource;
    private Game currentGame;

    public Gambling(String name, String description, int minwinAmount, int maxwinAmount, double chance, int imageResource, Game currentGame) {
        entityid = id++;
        this.name = name;
        this.description = description;
        this.minWinAmount = minwinAmount;
        this.maxWinAmount = maxwinAmount;
        this.chance = chance;
        this.imageResource = imageResource;
        this.currentGame = currentGame;
    }

    public int getId() {
        return entityid;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMinWinAmount() {
        //return minWinAmount;
        return currentGame.calcGambleRealReward(minWinAmount);
    }

    public int getMaxWinAmount() {
        //return maxWinAmount;
        return currentGame.calcGambleRealReward(maxWinAmount);
    }

    public double getChance() {
        //return chance;
        return currentGame.calcGambleRealChance(chance);
    }
}
