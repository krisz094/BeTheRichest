package hu.uniobuda.nik.betherichest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import hu.uniobuda.nik.betherichest.Factories.LeaderFactory;
import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Leader;

/**
 * Created by Kristof on 2017. 04. 18..
 */

public class LeaderboardListFragment extends android.support.v4.app.Fragment {
    View rootView;
    Game game;

    public static LeaderboardListFragment newInstance() {

        Bundle args = new Bundle();

        LeaderboardListFragment fragment = new LeaderboardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        game = Game.Get();

        try {
            ListView listView = (ListView) rootView.findViewById(R.id.leaderboard_listview);
            LeaderFactory lf = new LeaderFactory();
            List<Leader> leaders = lf.parse(getActivity().getAssets().open("richest_people.xml"));
            final LeaderAdapter adapter = new LeaderAdapter(leaders);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.leaderboard_list, container, false);
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

