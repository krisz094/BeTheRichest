package hu.uniobuda.nik.betherichest.Conditions;

import hu.uniobuda.nik.betherichest.Interfaces.ConditionalProvider;
import hu.uniobuda.nik.betherichest.GameObjects.Game;

/**
 * Created by krisz on 2017. 05. 17..
 */

public class RankOfIDNeeded implements ConditionalProvider {
    int rank;
    int id;
    public RankOfIDNeeded(int id, int rank) {
        this.rank = rank;
        this.id = id;
    }

    @Override
    public Boolean Evaluate(Game currentGame) {
        return currentGame.gameState.getInvestmentRankById(id) >= rank;
    }
}