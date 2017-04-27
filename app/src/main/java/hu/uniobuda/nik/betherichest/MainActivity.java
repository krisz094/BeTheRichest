package hu.uniobuda.nik.betherichest;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.GameObjects.Game;

public class MainActivity extends AppCompatActivity {

    Game game = Game.Get();

    TextView CurrMoneyText;
    TextView MoneyPerSecText;
    TextView MoneyPerTapText;
    ImageView TapBtn;
    Animation shake;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

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
                CurrMoneyText.setText(totalMoney);
            }

            @Override
            public void onMoneyPerTapChanged(String moneyPerTap) {
                MoneyPerTapText.setText(moneyPerTap);
            }

            @Override
            public void onMoneyPerSecChanged(String moneyPerSec) {
                MoneyPerSecText.setText(moneyPerSec);
            }
        });
    }

    private void InitializeTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CurrMoneyText.setText(game.getCurrentMoneyAsString());
                    }
                });
            }
        }, 0, 1000 / 10);
    }

    private void InitializeUIElements() {
        CurrMoneyText = (TextView) findViewById(R.id.currMoneyText);
        MoneyPerSecText = (TextView) findViewById(R.id.moneyPerSecText);
        MoneyPerTapText = (TextView) findViewById(R.id.moneyPerTapText);
        TapBtn = (ImageView) findViewById(R.id.clickbtn);

        MoneyPerSecText.setText(game.getMoneyPerSecAsString());
        MoneyPerTapText.setText(game.getMoneyPerClickAsString());

        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink);

    }

    private void refreshView() {
        MoneyPerSecText.setText(game.getMoneyPerSecAsString());
        MoneyPerTapText.setText(game.getMoneyPerClickAsString());
    }

    public void InvestmentsClick(View view) {
/*
        setContentView(R.layout.activity_details);
        InvestmentListFragment fragment = InvestmentListFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();*/

//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.fragment_container, fragment);
//        transaction.commit();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.addToBackStack(InvestmentListFragment.class.getName());
        ft.replace(R.id.investment_list_container, new InvestmentListFragment());
        ft.commit();
    }

    public void GamblingClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.replace(R.id.gambling_container, new GamblingFragment());
        ft.addToBackStack(GamblingFragment.class.getName());
        ft.commit();
    }

    public void UpgradesClick(View view) {
        Toast.makeText(
                MainActivity.this,
                "Upgrades",
                Toast.LENGTH_LONG
        ).show();
    }

    public void LeaderboardClick(View view) {

        setContentView(R.layout.leaderboard_container);
        LeadersboardListFragment fragment = LeadersboardListFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.leaderboard_fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //timer.cancel();
        //timer.purge();
    }

    public void DollarClick(View view) {
        game.click();
        TapBtn.startAnimation(shake);
    }
}
