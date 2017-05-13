package hu.uniobuda.nik.betherichest.Effects;

import hu.uniobuda.nik.betherichest.Interfaces.IEffectable;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class DoublerEffect implements IEffectable {
    @Override
    public double effect(double input) {
        return input * 2;
    }

    @Override
    public String getExtraInfo() {
        return "X2";
    }
}
