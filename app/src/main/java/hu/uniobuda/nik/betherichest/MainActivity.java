package hu.uniobuda.nik.betherichest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int money = 0;
    int burgerPrice = 5;

    Game game = Game.Get();

    TextView currentMoneyLabel;
    TextView burgerPriceLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        InvestmentListFragment fragment = InvestmentListFragment.newInstance();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
*/
        currentMoneyLabel = (TextView) findViewById(R.id.dollars);
        currentMoneyLabel.setText(Integer.toString(money));
        burgerPriceLabel = (TextView) findViewById(R.id.burgerPrice);
        burgerPriceLabel.setText(Integer.toString(burgerPrice));
    }

    public void counter(View view) {
        /**
         * //TODO
         * state.Earn(MONEY PER SEC -> AMI HONNAN JÖN KI?)
         */
        money++;
        currentMoneyLabel.setText(Integer.toString(money));
    }

    public void burgerCounter(View view) {
        if (money >= burgerPrice) {
            money -= burgerPrice;
            burgerPrice *= 3;
            burgerPriceLabel.setText(Integer.toString(burgerPrice));
            Timer T = new Timer();

            T.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            money += 3;
                            currentMoneyLabel.setText(Integer.toString(money));
                        }
                    });
                }
            }, 1000, 1000);
        }
    }
}
