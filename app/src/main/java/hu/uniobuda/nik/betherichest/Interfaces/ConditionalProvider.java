package hu.uniobuda.nik.betherichest.Interfaces;

import hu.uniobuda.nik.betherichest.GameObjects.Game;

/**
 * Created by krisz on 2017. 04. 01..
 */
/*
public abstract class ConditionalProvider {
    public abstract Boolean Evaluate(Game currentGame);
}*/

public interface ConditionalProvider {
    public Boolean Evaluate(Game currentGame);
}
