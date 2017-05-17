package hu.uniobuda.nik.betherichest.Conditions;

import hu.uniobuda.nik.betherichest.Interfaces.ConditionalProvider;
import hu.uniobuda.nik.betherichest.GameObjects.Game;

/**
 * Created by krisz on 2017. 05. 17..
 */

public class UpgradeWithIDUnlocked implements ConditionalProvider {

    public UpgradeWithIDUnlocked(int id) {
        this.id = id;
    }

    int id;

    @Override
    public Boolean Evaluate(Game currentGame) {
        return currentGame.gameState.getUpgradeBoughtById(id);
    }
}
