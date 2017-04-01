package hu.uniobuda.nik.betherichest;

/**
 * Created by krisz on 2017. 04. 01..
 */

public abstract class ConditionalProvider {
    public abstract Boolean Evaluate(Game currentGame);
}

class RankOfIDNeeded extends ConditionalProvider {
    int rank;
    int id;
    public RankOfIDNeeded(int id, int rank) {
        this.rank = rank;
        this.id = id;
    }

    @Override
    public Boolean Evaluate(Game currentGame) {
        return currentGame.gameState.GetInvestmentRankById(id) >= rank;
    }
}

class UpgradeWithIDUnlocked extends  ConditionalProvider{

    public UpgradeWithIDUnlocked(int id) {
        this.id = id;
    }

    int id;

    @Override
    public Boolean Evaluate(Game currentGame) {
        return currentGame.gameState.GetUpgradeBoughtById(id);
    }
}