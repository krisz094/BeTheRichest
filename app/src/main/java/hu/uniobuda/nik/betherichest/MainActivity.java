package hu.uniobuda.nik.betherichest;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
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
        setContentView(R.layout.activity_main);

        InitializeUIElements();



        //InitializeTimer();
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

    private void refreshView() {
        MoneyPerSecText.setText(game.getMoneyPerSecAsString());
        MoneyPerTapText.setText(game.getMoneyPerClickAsString());
    }

    public void InvestmentsClick(View view) {

        setContentView(R.layout.activity_details);
        InvestmentListFragment fragment = InvestmentListFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();

//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.fragment_container, fragment);
//        transaction.commit();
    }

    public void UpgradesClick(View view) {
        Toast.makeText(
                MainActivity.this,
                "Upgrades",
                Toast.LENGTH_LONG
        ).show();
    }

    public void GamblingClick(View view) {
        Toast.makeText(
                MainActivity.this,
                "Gambling",
                Toast.LENGTH_LONG
        ).show();
    }

    public void LeaderboardClick(View view) {
        Toast.makeText(
                MainActivity.this,
                "Back button\nSlow animation\nBitmap scaling",
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //timer.cancel();
        //timer.purge();
    }

    public void DollarClick(View view) {
        game.click();
        shake.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                Log.d("HE", "HEHEHHE: " + input);
                return input;
            }
        });
        TapBtn.startAnimation(shake);
    }
}
