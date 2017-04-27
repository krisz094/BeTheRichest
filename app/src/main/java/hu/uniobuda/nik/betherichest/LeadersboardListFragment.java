package hu.uniobuda.nik.betherichest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Leaders;

/**
 * Created by Kristof on 2017. 04. 18..
 */

public class LeadersboardListFragment extends android.support.v4.app.Fragment {
    View rootView;
    Game game;

    public static LeadersboardListFragment newInstance() {

        Bundle args = new Bundle();

        LeadersboardListFragment fragment = new LeadersboardListFragment();
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

        List<Leaders> leaders = null;
        try {
            leaders = game.getLeaders();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        final LeadersAdapter adapter = new LeadersAdapter(leaders);
        ListView listView = (ListView) rootView.findViewById(R.id.leaderboard_listview);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leaderboard_list,container,false);
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
