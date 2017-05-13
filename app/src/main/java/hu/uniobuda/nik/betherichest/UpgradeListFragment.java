package hu.uniobuda.nik.betherichest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Upgrade;

/**
 * Created by Márk on 2017. 05. 06..
 */

public class UpgradeListFragment extends android.support.v4.app.Fragment {
    View rootView;
    Game game;
    Timer timer;
    List<Upgrade> items;

    public static InvestmentListFragment newInstance() {

        Bundle args = new Bundle();

        InvestmentListFragment fragment = new InvestmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.upgrades_list, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (noMoneyToast != null) {
            noMoneyToast.cancel();
        }
    }
    Toast noMoneyToast = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = Game.Get();

        items = game.getDisplayableUpgrades();
        final UpgradeAdapter adapter = new UpgradeAdapter(items);
        //itt nem találja meg a upgrade Listviewt és ezért null lesz a Listview
        final GridView listView = (GridView) rootView.findViewById(R.id.upgrade_listview);
        listView.setAdapter(adapter);

        game.setOnMoneyChanged2(new Game.MoneyChangedListener2() {
            @Override
            public void onTotalMoneychanged2() {
                adapter.setItems(game.getDisplayableUpgrades());
                adapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Upgrade selectedUpgrade = adapter.getItem(position);
                boolean success = selectedUpgrade.buy();
                if (success) {
                    adapter.setItems(game.getDisplayableUpgrades());
                    adapter.notifyDataSetChanged();
                } else {
                    if (noMoneyToast != null) {
                        noMoneyToast.cancel();
                    }
                    noMoneyToast =
                    Toast.makeText(
                            getContext(),
                            "You don't have enough money",
                            Toast.LENGTH_SHORT
                    );
                    noMoneyToast.show();
                }
            }
        });
    }
}
