package hu.uniobuda.nik.betherichest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;
import hu.uniobuda.nik.betherichest.R;

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
        rootView = inflater.inflate(R.layout.investment_list, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    Toast noMoneyToast = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = Game.Get();

        List<Investment> items = game.getInvestments();

        final InvestmentAdapter adapter = new InvestmentAdapter(items);

        final ListView listView = (ListView) rootView.findViewById(R.id.investment_listview);
        listView.setAdapter(adapter);

        game.setOnMoneyChanged2(new Game.MoneyChangedListener2() {
            @Override
            public void onTotalMoneychanged2() {
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Investment selectedInvestment = adapter.getItem(position);
                if (selectedInvestment.buy()) {
                    adapter.notifyDataSetChanged();
                }
                else {
                    if (noMoneyToast != null) {
                        noMoneyToast.cancel();
                    }
                    noMoneyToast =
                    Toast.makeText(
                            getContext(),
                            R.string.not_enough_money,
                            Toast.LENGTH_SHORT
                    );
                    noMoneyToast.show();
                }

            }
        });
    }
}
