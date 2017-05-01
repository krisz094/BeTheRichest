package hu.uniobuda.nik.betherichest.GameObjects;

/**
 * Created by Szabi on 2017.05.01..
 */

public class Gambling {
    private static int id;
    private String name;
    private String description;
    private int minWinAmount;
    private int maxWinAmount;
    private double chance;
    private int imageResource;

    public Gambling(String name, String description, int minwinAmount, int maxwinAmount, double chance, int imageResource) {
        id++;
        this.name = name;
        this.description = description;
        this.minWinAmount = minwinAmount;
        this.maxWinAmount = maxwinAmount;
        this.chance = chance;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
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
        return minWinAmount;
    }

    public int getMaxWinAmount() {
        return maxWinAmount;
    }

    public double getChance() {
        return chance;
    }
}
