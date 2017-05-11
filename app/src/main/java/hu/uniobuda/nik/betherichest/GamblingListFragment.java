package hu.uniobuda.nik.betherichest;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.GameObjects.Gambling;
import hu.uniobuda.nik.betherichest.GameObjects.Game;

/**
 * Created by Szabi on 2017-04-27.
 */

public class GamblingListFragment extends Fragment {
    Game game;
    View rootView;
    TextView timerText;
    ImageView rotatingImage;
    boolean isTimerRunning = false;
    //Calendar lastGamblingDate;
    //Calendar nextAllowedGamblingDate;
    Animation growAndRotate;
    static final int TIME_BETWEEN_TWO_GAMBLING = 12;
    Timer T;

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
        T = new Timer();
        growAndRotate = AnimationUtils.loadAnimation(getContext(), R.anim.grow_and_rotate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        game = Game.Get();
        List<Gambling> items = game.getGamblings();
        final GamblingAdapter adapter = new GamblingAdapter(items);
        final ListView listView = (ListView) rootView.findViewById(R.id.gambling_listview);
        listView.setAdapter(adapter);

        timerText = (TextView) rootView.findViewById(R.id.timer);
        rotatingImage = (ImageView) rootView.findViewById(R.id.rotatingImage);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Gambling gambling = adapter.getItem(position);
                if (!isTimerRunning) {

                    rotatingImage.setBackgroundResource(gambling.getImageResource());
                    rotatingImage.startAnimation(growAndRotate);

                    growAndRotate.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            StartTimer();
                            isTimerRunning = true;
                            setGamblingDates();
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            int wonMoney = CalculateWonMoney(adapter.getItem(position));
                            Toast.makeText(
                                    getContext(),
                                    String.format(wonMoney != 0 ? getString(R.string.gambling_won_money) : getString(R.string.gambling_no_win), wonMoney),
                                    Toast.LENGTH_LONG
                            ).show();
//                            StartTimer();
//                            isTimerRunning = true;
//                            setGamblingDates();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });


                } else {
                    Toast.makeText(
                            getContext(),
                            R.string.gambling_wait,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private int CalculateWonMoney(Gambling gambling) {
        Random rnd = new Random();
        if (rnd.nextInt(10000) < gambling.getChance() * 100) {
            int minValue = gambling.getMinWinAmount();
            int maxValue = gambling.getMaxWinAmount();
            return rnd.nextInt(maxValue - minValue) + minValue;
        } else {
            return 0;
        }
    }

    private void StartTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerText.setText(String.valueOf(getCalculatedRemainingTimeString()));
                    }
                });
            }
        }, 0, 1000);
    }


    /**
     * Calculates the difference between the last time a gambling item was clicked and the actual date
     *
     * @return difference as formatted string, which will be displayed on the UI
     */
    private String getCalculatedRemainingTimeString() {
        if (game.gameState.getNextAllowedGamblingDate() != null) { //itt

            Calendar today = Calendar.getInstance();
            long diffInMilliSeconds = (game.gameState.getNextAllowedGamblingDate().getTimeInMillis() - today.getTimeInMillis());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(diffInMilliSeconds);
            String formattedTime = String.format(getResources().getString(R.string.gambling_time_remaining), cal.get(Calendar.HOUR_OF_DAY) - 1, cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

            if (diffInMilliSeconds <= 0) {
                T.purge();
                isTimerRunning = false;
                return getResources().getString(R.string.gambling_header);
            }
            return formattedTime;
        }
        return getResources().getString(R.string.gambling_header);
    }

    /**
     * stores the last time a gambling item was clicked and the time when they will be available again
     */
    private void setGamblingDates() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //lastGamblingDate = cal;
        game.gameState.setLastGamblingDate(cal);
        cal.add(Calendar.HOUR_OF_DAY, TIME_BETWEEN_TWO_GAMBLING);
        //nextAllowedGamblingDate = cal;
        game.gameState.setNextAllowedGamblingDate(cal);

    }

    @Override
    public void onPause() {
        super.onPause();
        T.cancel();
    }
}
