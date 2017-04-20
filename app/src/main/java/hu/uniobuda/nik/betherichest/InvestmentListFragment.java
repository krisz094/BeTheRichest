package hu.uniobuda.nik.betherichest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.telecom.Call;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;

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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = Game.Get();

        List<Investment> items = game.getInvestments();

        final InvestmentAdapter adapter = new InvestmentAdapter(items);

        final ListView listView = (ListView) rootView.findViewById(R.id.investment_listview);
        listView.setAdapter(adapter);

        final TextView CurrMoneyText = (TextView) rootView.findViewById(R.id.currentMoneyText);
        //currentMoneytext.setText(game.getCurrentMoneyAsString());

        game.setOnMoneyChanged(new Game.MoneyChangedListener() {
            @Override
            public void onTotalMoneyChanged(String totalMoney) {
                CurrMoneyText.setText(totalMoney);
            }

            @Override
            public void onMoneyPerTapChanged(String moneyPerTap) {

            }

            @Override
            public void onMoneyPerSecChanged(String moneyPerSec) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Investment selectedInvestment = adapter.getItem(position);
                if (selectedInvestment.buy()) {
                    //game.buyInvestment(selectedInvestment.getId());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(
                            getContext(),
                            "You don't have enough money",
                            Toast.LENGTH_LONG
                    ).show();
                }

            }
        });
    }
}
