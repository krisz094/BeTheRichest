package hu.uniobuda.nik.betherichest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Szabi on 2017.04.15..
 */

public class InvestmentListFragment extends android.support.v4.app.Fragment {
    View rootView;
    Game game;

    public static InvestmentListFragment newInstance() {

        Bundle args = new Bundle();

        InvestmentListFragment fragment = new InvestmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_investments_list, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Investment> items = new ArrayList<>();
        items.add(new Investment(1, "Pizza", 10, 30, "Finom", null, null, R.drawable.pizza));
        items.add(new Investment(2, "Ice Cream Shop", 15, 35, "Finom", null, null, R.drawable.icecream));
        items.add(new Investment(3, "Apartmann", 150, 100, "Finom", null, null, R.drawable.apartment));
        items.add(new Investment(4, "Bank", 800, 300, "Finom", null, null, R.drawable.bank));
        items.add(new Investment(4, "Hotel Chain", 1500, 360, "Finom", null, null, R.drawable.hotel_chain));
        items.add(new Investment(4, "Oil Rig", 15000, 3000, "Finom", null, null, R.drawable.oilrig));

        final InvestmentAdapter adapter = new InvestmentAdapter(items);

        ListView listView = (ListView) rootView.findViewById(R.id.investment_listview);
        listView.setAdapter(adapter);


    }


}
