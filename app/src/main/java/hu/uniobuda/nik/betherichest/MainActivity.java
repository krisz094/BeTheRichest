package hu.uniobuda.nik.betherichest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Game game = Game.Get();

    TextView CurrMoneyText;
    TextView MoneyPerSecText;
    TextView MoneyPerTapText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrMoneyText = (TextView) findViewById(R.id.currMoneyText);
        MoneyPerSecText = (TextView) findViewById(R.id.moneyPerSecText);
        MoneyPerTapText = (TextView) findViewById(R.id.moneyPerTapText);

        game.BuyInvestment(0,5); // 5 darabot vesz a 0 id-jű investmentből => 0.5money/sec
        game.BuyUpgrade(0); //a 0-ás id-jű upgrade megkétszerezi a 0 indexű investment termelését => 1.0 money/sec

        MoneyPerSecText.setText(game.GetMoneyPerSecAsString());
        MoneyPerTapText.setText(game.GetMoneyPerClickAsString());

        Timer T = new Timer();
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CurrMoneyText.setText(game.GetCurrentMoney());
                    }
                });
            }
        }, 0, 1000 / 10);


    }

}
