package hu.uniobuda.nik.betherichest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;

public class MainActivity extends AppCompatActivity {

    Game game = Game.Get();

    TextView CurrMoneyText;
    TextView MoneyPerSecText;
    TextView MoneyPerTapText;
    ImageView TapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrMoneyText = (TextView) findViewById(R.id.currMoneyText);
        MoneyPerSecText = (TextView) findViewById(R.id.moneyPerSecText);
        MoneyPerTapText = (TextView) findViewById(R.id.moneyPerTapText);
        TapBtn = (ImageView) findViewById(R.id.clickbtn);

        MoneyPerSecText.setText(game.getMoneyPerSecAsString());
        MoneyPerTapText.setText(game.getMoneyPerClickAsString());


        game.buyInvestment(5); // megveszem a legkomolyabbat h jojjon a penz. hehe.
        refreshView(); // ezt a fgv-t be kene epiteni a game osztalyba, de ahhoz tarolni kene benne a viewre egy referenciat..

        List<Investment> invs = game.getInvestments();

        Double elso = invs.get(0).getMoneyPerSec();
        Double masodik = invs.get(0).getMPSPerRank();

        Timer T = new Timer();
        T.schedule(new TimerTask() {
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

        TapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.click();
            }
        });
    }

    private void refreshView() {
        MoneyPerSecText.setText(game.getMoneyPerSecAsString());
        MoneyPerTapText.setText(game.getMoneyPerClickAsString());
    }

    public void InvestmentsClick(View view) {
        Toast.makeText(
                MainActivity.this,
                "Investments",
                Toast.LENGTH_LONG
        ).show();

        setContentView(R.layout.activity_details);
        InvestmentListFragment fragment = InvestmentListFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
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
                "Leaderboard",
                Toast.LENGTH_LONG
        ).show();
    }
}
