package hu.uniobuda.nik.betherichest.Effects;

import hu.uniobuda.nik.betherichest.IEffectable;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class DoublerEffect implements IEffectable {
    @Override
    public double Effect(double input) {
        return input * 2;
    }
}
