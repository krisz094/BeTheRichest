package hu.uniobuda.nik.betherichest;

import android.graphics.Color;
import android.os.Bundle;
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
    TextView wonMoneyText;
    ImageView rotatingImage;

    Animation growAndRotate;
    Animation grow;
    Animation fade;

    static final int TIME_BETWEEN_TWO_GAMBLING = 6;
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

        game = Game.Get();

        T = new Timer();
        StartTimer();

        growAndRotate = AnimationUtils.loadAnimation(getContext(), R.anim.grow_and_rotate);
        grow = AnimationUtils.loadAnimation(getContext(), R.anim.grow);
        fade = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    Toast tooMuchToast = null;

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Gambling> items = game.getGamblings();
        final GamblingAdapter adapter = new GamblingAdapter(items);
        final ListView listView = (ListView) rootView.findViewById(R.id.gambling_listview);
        listView.setAdapter(adapter);

        timerText = (TextView) rootView.findViewById(R.id.timer);
        wonMoneyText = (TextView) rootView.findViewById(R.id.wonMoney);
        rotatingImage = (ImageView) rootView.findViewById(R.id.rotatingImage);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Gambling gambling = adapter.getItem(position);
                if (!game.gameState.getIsGamblingTimerRunning()) {

                    rotatingImage.setBackgroundResource(gambling.getImageResource());
                    rotatingImage.startAnimation(growAndRotate);

                    setAnimationListeners(position);
                } else {
                    if (tooMuchToast != null) {
                        tooMuchToast.cancel();
                    }
                    tooMuchToast =
                    Toast.makeText(
                            getContext(),
                            R.string.gambling_wait,
                            Toast.LENGTH_SHORT
                    );
                    tooMuchToast.show();
                }
            }

            private void setAnimationListeners(final int position) {
                growAndRotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        StartTimer();
                        game.gameState.setIsGamblingTimerRunning(true);
                        setGamblingDates();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int wonMoney = CalculateWonMoney(adapter.getItem(position));
                        String text;
                        if (wonMoney == 0) {
                            text = getString(R.string.gambling_no_win);
                            wonMoneyText.setTextColor(getResources().getColor(R.color.darkRed));
                        } else {
                            text = "You won " + wonMoney + "$";
                            game.earnMoney(wonMoney);
                            wonMoneyText.setTextColor(getResources().getColor(R.color.orange));
                        }
                        wonMoneyText.setVisibility(View.VISIBLE);
                        wonMoneyText.setText(text);
                        wonMoneyText.startAnimation(grow);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                grow.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rotatingImage.startAnimation(fade);
                        wonMoneyText.startAnimation(fade);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                fade.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rotatingImage.setVisibility(View.GONE);
                        wonMoneyText.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
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
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerText.setText(String.valueOf(getCalculatedRemainingTimeString()));
                        }
                    });
                } catch (Exception ignored) {

                }
            }
        }, 0, 1000);
    }


    /**
     * Calculates the difference between the last time a gambling item was clicked and the actual date
     *
     * @return difference as formatted string, which will be displayed on the UI
     */
    private String getCalculatedRemainingTimeString() {
        String a;
        if (game.gameState.getNextAllowedGamblingDate() != null) {

            Calendar dateNow = Calendar.getInstance();
            long diffInMilliSeconds = game.gameState.getNextAllowedGamblingDate().getTimeInMillis() - dateNow.getTimeInMillis();

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(diffInMilliSeconds);

            String formattedTime = "";
            try {
                formattedTime = String.format(getResources().getString(R.string.gambling_time_remaining), cal.get(Calendar.HOUR_OF_DAY) - 1, cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
            } catch (Exception ignored) {

            }

            if (diffInMilliSeconds <= 0) {  // if the timer is down
                T.purge();
                game.gameState.setIsGamblingTimerRunning(false);
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
        cal.add(Calendar.MINUTE, TIME_BETWEEN_TWO_GAMBLING);
        game.gameState.setNextAllowedGamblingDate(cal);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        T.purge();
    }

    @Override
    public void onPause() {
        super.onPause();
        T.purge();
    }
}
