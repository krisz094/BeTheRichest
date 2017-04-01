package hu.uniobuda.nik.betherichest;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2017.03.31..
 */
public class InvestmentFactory {
    public static List<Investment> CreateInvestments(  ) {
        List<Investment> investments = new ArrayList<>();
        investments.add(new Investment("ASD",15,0.1,"Put your money into the bank, for a small amount of interest."));
        investments.add(new Investment("Hamburger Stand",15,0.1,"Make delicious hamburgers on the street, to get some money from hungry people."));
        return investments;
    }
}
