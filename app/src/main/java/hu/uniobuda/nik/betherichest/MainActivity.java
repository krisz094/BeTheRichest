package hu.uniobuda.nik.betherichest;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
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
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Upgrade;
import hu.uniobuda.nik.betherichest.Interfaces.DatabaseHandler;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    Game game = Game.Get();
    DatabaseHandler DBHandler;

    TextView currMoneyText;
    TextView moneyPerSecText;
    TextView moneyPerTapText;
    TextView tapMoneyText;
    ImageView tapBtn;

    RelativeLayout mainRelativeLayout;

    Animation shrink;
    Animation growAndFade;

    Timer timer;

    RelativeLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setIcon(R.mipmap.actionbar_icon);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.rgb(139,69,19));
        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(R.drawable.bluewood);
        actionBar.setBackgroundDrawable(drawable);



        DBHandler = new DatabaseHandler(this);

        //game.gameState.currentMoney=DBHandler.loadMoney();
        //if(game.gameState.InvestmentIdRank.size()==0) {
        //game.buyInvestment(1);
        //}
        game.loadGame(DBHandler);

        //DBHandler.loadInvestments(game.gameState.InvestmentIdRank);

        InitializeUIElements();

        //InitializeTimer();
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

        tapBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dollarOnTouch(event);
                return false;
            }
        });

    }

    private void dollarOnTouch(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    TextView tapText = new TextView(MainActivity.this);
//
//                    tapText.setTextSize(35);
//                    tapText.setTextColor(Color.parseColor("#e5ba0a"));
//                    tapText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//                    tapText.setText("+" + String.valueOf(game.getMoneyPerClick() + "$"));
//                    params.setMargins((int) (event.getX() - tapText.getWidth() / 2), (int) (event.getY() + tapText.getTextSize()), 0, 0);
//                    tapText.setLayoutParams(params);
//                    mainRelativeLayout.addView(tapText);
//                    TextGrowthAnimationListener listener = new TextGrowthAnimationListener(tapText, mainRelativeLayout);
//                    growth.setAnimationListener(listener);
//                    tapText.startAnimation(growth);

            game.click();
            tapBtn.startAnimation(shrink);
            tapMoneyText.startAnimation(growAndFade);

            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            int marginLeft = (int) (event.getX() - tapMoneyText.getWidth() / 2);
            int marginTop = (int) (event.getY() + tapMoneyText.getTextSize() * 2);

            marginLeft = getMarginLeft(event, marginLeft);

            params.setMargins(marginLeft, marginTop, 0, 0);
            tapMoneyText.setText("+" + String.valueOf((NumberFormat.getNumberInstance(Locale.US).format(game.getMoneyPerClick()) + "$")));
            tapMoneyText.setLayoutParams(params);
        }
    }

    private int getMarginLeft(MotionEvent event, int marginLeft) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if (event.getX() > size.x - tapMoneyText.getWidth()) {
            marginLeft -= tapMoneyText.getWidth() / 2;
        }
        if (marginLeft < 0) {
            marginLeft = 0;
        }
        return marginLeft;
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
        currMoneyText = (TextView) findViewById(R.id.currMoneyText);
        moneyPerSecText = (TextView) findViewById(R.id.moneyPerSecText);
        moneyPerTapText = (TextView) findViewById(R.id.moneyPerTapText);
        tapMoneyText = (TextView) findViewById(R.id.tapMoneyText);
        tapBtn = (ImageView) findViewById(R.id.dollar);

        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerClickAsString());

        shrink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink);
        growAndFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_and_fade);

        mainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
    }

    private void refreshView() {
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerClickAsString());
    }

    public void InvestmentsClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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
        tapBtn.setLayoutParams(params);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setDollarMargin((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50,
                getResources().getDisplayMetrics()));
    }

    public void UpgradesClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.replace(R.id.upgrade_container, new UpgradeListFragment());
        ft.addToBackStack(UpgradeListFragment.class.getName());
        ft.commit();
    }

    public void GamblingClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.replace(R.id.gambling_container, new GamblingListFragment());
        ft.addToBackStack(GamblingListFragment.class.getName());
        ft.commit();
    }

    public void LeaderboardClick(View view) {
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
        //timer.cancel();
        //timer.purge();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //DBHandler.saveMoney(game.getCurrentMoney());
        game.saveGame(DBHandler);


    }
}
