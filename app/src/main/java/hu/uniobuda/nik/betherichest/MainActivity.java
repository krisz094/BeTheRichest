package hu.uniobuda.nik.betherichest;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.Fragments.GamblingListFragment;
import hu.uniobuda.nik.betherichest.Fragments.InvestmentListFragment;
import hu.uniobuda.nik.betherichest.Fragments.LeaderboardListFragment;
import hu.uniobuda.nik.betherichest.Fragments.UpgradeListFragment;
import hu.uniobuda.nik.betherichest.GameObjects.Game;


public class MainActivity extends AppCompatActivity {

    Game game = Game.Get();
    DatabaseHandler DBHandler;

    TextView currMoneyText;
    TextView moneyPerSecText;
    TextView moneyPerTapText;
    ImageView dollarImage;

    ActionBar actionBar;
    //ImageView helpImage;

    RelativeLayout mainRelativeLayout;

    Animation shrink;
    Animation growAndFade;

    Timer timer;

    RelativeLayout.LayoutParams params;

    static Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        DBHandler = new DatabaseHandler(this);

        //game.gameState.currentMoney=DBHandler.loadMoney();
        //if(game.gameState.InvestmentIdRank.size()==0) {
        //game.buyInvestment(1);

        //}
        try {
            game.loadGame(DBHandler);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        InitializeUIElements();
    }

    @Override
    protected void onResume() {
        super.onResume();

        InitializeEventListeners();
    }

    private void InitializeEventListeners() {
        game.setOnMoneyChanged(new Game.MoneyChangedListener() {
            @Override
            public void onTotalMoneyChanged(String totalMoney) {
                currMoneyText.setText(totalMoney);
            }

            @Override
            public void onMoneyPerTapChanged(String moneyPerTap) {
                moneyPerTapText.setText(moneyPerTap);
            }

            @Override
            public void onMoneyPerSecChanged(String moneyPerSec) {
                moneyPerSecText.setText(moneyPerSec);
            }
        });

        dollarImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dollarOnTouch(event);
                return false;
            }
        });

    }


    private void dollarOnTouch(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.click();
            dollarImage.startAnimation(shrink);

            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            final TextView tapText = (TextView) View.inflate(this, R.layout.tap_money_text, null);
            tapText.setText("+" + String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format((int) game.getMoneyPerTap()) + "$"));

            tapText.measure(0, 0);

            params.setMargins(getNewRandomXPosition(tapText), getNewRandomYPosition(), 0, 0);
            tapText.setLayoutParams(params);
            mainRelativeLayout.addView(tapText);

            Animation growAndFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_and_fade);
            growAndFade.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mainRelativeLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mainRelativeLayout.removeView(tapText);
                        }
                    });
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tapText.startAnimation(growAndFade);
        }
    }

    /**
     * Generates a random x coordinate for the textView positioned on the dollar image
     *
     * @param tapText Taptext is needed to avoid the text sticking out of display, which causes ugly appearance
     * @return
     */
    private int getNewRandomXPosition(TextView tapText) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int left = dollarImage.getLeft();
        int right = dollarImage.getRight();

        int marginLeft = rnd.nextInt(right - left) + left;
        if (marginLeft >= size.x - tapText.getMeasuredWidth()) {    // Ha kilógna a text, toljuk balra annak szélességével
            marginLeft -= tapText.getMeasuredWidth();
        }
        return marginLeft;
    }

    private int getNewRandomYPosition() {
        int top = dollarImage.getTop();
        int bottom = dollarImage.getBottom();
        return rnd.nextInt(bottom - top) + top - actionBar.getHeight() / 2;
    }

    private void InitializeTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currMoneyText.setText(String.valueOf(game.getCurrentMoney()));
                    }
                });
            }
        }, 0, 1000 / 10);
    }

    private void InitializeUIElements() {
        InitializeActionBar();
        currMoneyText = (TextView) findViewById(R.id.currMoneyText);
        moneyPerSecText = (TextView) findViewById(R.id.moneyPerSecText);
        moneyPerTapText = (TextView) findViewById(R.id.moneyPerTapText);
        dollarImage = (ImageView) findViewById(R.id.dollar);
        ImageView smallDollar =  (ImageView) findViewById(R.id.smallDollar);
        smallDollar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CheatClick(v);
                return true;
            }
        });

        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerTapAsString());

        shrink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink);
        growAndFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_and_fade);

        mainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
    }

    /*private void InitializeActionBar() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setIcon(R.mipmap.actionbar_icon);
        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(R.drawable.bluewood);
        actionBar.setBackgroundDrawable(drawable);
    }*/
    private void InitializeActionBar() {

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setIcon(R.mipmap.actionbar_icon);
        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(R.drawable.bluewood);
        actionBar.setBackgroundDrawable(drawable);


//        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
//                | ActionBar.DISPLAY_SHOW_CUSTOM);
//        helpImage = new ImageView(actionBar.getThemedContext());
//        helpImage.setScaleType(ImageView.ScaleType.CENTER);
//        helpImage.setImageResource(R.mipmap.help_mipmap);
//
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                helpImage.setVisibility(View.GONE);
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                //ft.setCustomAnimations(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
//                ft.addToBackStack(HelpFragment.class.getName());
//                ft.replace(R.id.help_container, new HelpFragment());
//                ft.commit();
//            }
//        };
//        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams( ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
//                | Gravity.CENTER_VERTICAL
//        );
//
//        helpImage.setLayoutParams(layoutParams);
//        actionBar.setCustomView(helpImage);
//        helpImage.setOnClickListener(listener);
    }

    private void refreshView() {
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerTapAsString());
    }

    public void InvestmentsClick(View view) {
        if (noUpgradesToast != null) {
            noUpgradesToast.cancel();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.addToBackStack(InvestmentListFragment.class.getName());
        ft.replace(R.id.investment_list_container, new InvestmentListFragment());
        ft.commit();

        setDollarMargin(0);
    }

    private void setDollarMargin(int marginTop) {
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.moneyPerTapText);
        params.setMargins(0, marginTop, 0, 0);
        dollarImage.setLayoutParams(params);
    }

    Toast noUpgradesToast = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setDollarMargin((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50,
                getResources().getDisplayMetrics()));
       // helpImage.setVisibility(View.VISIBLE);

    }

    public void UpgradesClick(View view) {
        if (game.getDisplayableUpgrades().size() < 1) {
            if (noUpgradesToast != null) {
                noUpgradesToast.cancel();
            }
            noUpgradesToast =
            Toast.makeText(
                    MainActivity.this,
                    "No upgrades available",
                    Toast.LENGTH_SHORT
            );
            noUpgradesToast.show();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            ft.replace(R.id.upgrade_container, new UpgradeListFragment());
            ft.addToBackStack(UpgradeListFragment.class.getName());
            ft.commit();
        }
    }

    public void CheatClick(View view) {
        game.earnMoney(1000000000);
    }

    public void GamblingClick(View view) {
        if (noUpgradesToast != null) {
            noUpgradesToast.cancel();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.replace(R.id.gambling_container, new GamblingListFragment());
        ft.addToBackStack(GamblingListFragment.class.getName());
        ft.commit();
    }

    public void LeaderboardClick(View view) {
        if (noUpgradesToast != null) {
            noUpgradesToast.cancel();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.replace(R.id.leaderboard_list_container, new LeaderboardListFragment());
        ft.addToBackStack(LeaderboardListFragment.class.getName());
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //game.saveGame(DBHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (noUpgradesToast != null) {
            noUpgradesToast.cancel();
        }
        game.saveGame(DBHandler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //game.saveGame(DBHandler);
    }

    public void closeHelp(View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.help_container);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        //helpImage.setVisibility(View.VISIBLE);
    }
}
