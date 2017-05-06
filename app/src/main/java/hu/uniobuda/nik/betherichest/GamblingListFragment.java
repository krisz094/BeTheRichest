package hu.uniobuda.nik.betherichest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Gambling;
import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;

/**
 * Created by Szabi on 2017-04-27.
 */

public class GamblingListFragment extends Fragment {
    View rootView;
    TextView timerText;
    boolean isTimerRunning = true;

    public static GamblingListFragment newInstance() {

        Bundle args = new Bundle();

        GamblingListFragment fragment = new GamblingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gambling_list, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Gambling> items = Game.Get().getGamblings();

        final GamblingAdapter adapter = new GamblingAdapter(items);

        final ListView listView = (ListView) rootView.findViewById(R.id.gambling_listview);
        listView.setAdapter(adapter);

        timerText = (TextView) rootView.findViewById(R.id.timer);
        final CountDownTimer timer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText("Time remaining: " + millisUntilFinished / 1000);
            }


            @Override
            public void onFinish() {
                timerText.setText("Time to gamble!");
                isTimerRunning = false;
            }


        }.start();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gambling gambling = adapter.getItem(position);
                if (!isTimerRunning) {
                    adapter.notifyDataSetChanged();
                    double a = gambling.getChance();
                    Toast.makeText(
                            getContext(),
                            "You won" + gambling.getMaxWinAmount() + "$",
                            Toast.LENGTH_LONG
                    ).show();
                    timer.start();
                    isTimerRunning = true;

                } else {
                    Toast.makeText(
                            getContext(),
                            "You have to wait for the timer to finish.",
                            Toast.LENGTH_LONG
                    ).show();
                }

            }
        });
    }
}
