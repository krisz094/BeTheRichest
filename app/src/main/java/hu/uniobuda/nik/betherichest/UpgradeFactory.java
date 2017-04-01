package hu.uniobuda.nik.betherichest;

import java.util.ArrayList;
import java.util.List;

import hu.uniobuda.nik.betherichest.Effects.DoublerEffect;

/**
 * Created by krisz on 2017. 04. 01..
 */

public class UpgradeFactory {
    public static List<Upgrade> GetUpgrades() {
        List<Upgrade> list = new ArrayList<>();
        list.add(new Upgrade("valami","100ba kerül, 0 id-jű dolgot megduplázza", 100,0, new DoublerEffect()));
        return list;
    }
}
