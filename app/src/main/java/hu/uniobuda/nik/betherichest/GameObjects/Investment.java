package hu.uniobuda.nik.betherichest.GameObjects;

/**
 * Created by Szabi on 2017.03.31..
 */


public class Investment {
    private String name;
    private int basePrice;
    private double baseDpS;
    private String description;
    private int id;
    private int[] relevantUpgradeIDs;
    private Game currentGame;
    private final double coeff = 1.05;
    private int imageResource;


    public Investment(int id, String name, int basePrice, double baseDpS, String description, int[] relevantUpgradeIDs, Game currentGame, int imageResource) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        this.relevantUpgradeIDs = relevantUpgradeIDs;
        this.currentGame = currentGame;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the current rank of this investment
     *
     * @return Current rank
     */
    public int getRank() {
        return currentGame.gameState.getInvestmentRankById(id);
    }

    public int getPrice() {
        return (int) Math.round(basePrice * Math.pow(coeff, getRank()));
    }
    public String getPriceAsString() {
        return String.valueOf(getPrice());
    }


    /**
     * Gets money made by this investment per second, including the effect of upgrades
     *
     * @return Money Made Per Second
     */
    public Double getMoneyPerSec() {
        Double MPS = getRank() * baseDpS;
        for (Integer ID : relevantUpgradeIDs) {
            if (currentGame.gameState.getUpgradeBoughtById(ID)) {
                MPS = currentGame.getUpgrades().get(ID).execute(MPS);
            }
        }
        return MPS;
    }

    public Double getMPSPerRank() {
        Double MPS = baseDpS;
        for (Integer ID : relevantUpgradeIDs) {
            if (currentGame.gameState.getUpgradeBoughtById(ID)) {
                MPS = currentGame.getUpgrades().get(ID).execute(MPS);
            }
        }
        return MPS;
    }

    public void buy() {
        if (isBuyable()) {
            currentGame.buyInvestment(id);
        }
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isBuyable() {
        return currentGame.getCurrentMoney() >= getPrice();
    }


}
