package hu.uniobuda.nik.betherichest;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Game game = Game.Get();

    TextView CurrMoneyText;
    TextView MoneyPerSecText;
    TextView MoneyPerTapText;
    ImageView TapBtn;
    List<Investment> investments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrMoneyText = (TextView) findViewById(R.id.currMoneyText);
        MoneyPerSecText = (TextView) findViewById(R.id.moneyPerSecText);
        MoneyPerTapText = (TextView) findViewById(R.id.moneyPerTapText);
        TapBtn = (ImageView) findViewById(R.id.clickbtn);

        investments = game.getInvestments();
        List<Upgrade> upgrades = game.getUpgrades();

        //FONTOS!!!! hogy az investments.get(0) NEM!!! feltétlenül a 0ás ID-jű investmentet adja vissza
        //lehet h a listában az ID-k így néznek ki: 3, 6, 300, 325, 567, stb., de az ID-k garantáltan növekvő sorrendben vannak!

        Investment inv0 = investments.get(0);

        Upgrade upg0 = upgrades.get(0);

        // Investmentek vásárlására két lehetőség van:
        //1. a game classból id alapján vásárolni
        // game.buyInvestment(0); // vesz a 0 id-jű investmentből -> 0.1m/sec

        //2. egy investment buy() metódussal vásárolni
        // inv0.buy(); // vesz a 0 id-jű investmentből -> 0.2m/sec

        // Upgrade-ekre ugyanez igaz:
        //1. game classból id alapján vásárlás
        // game.buyUpgrade(2); //megveszi a 2-es idjű upgrade-et (klikk duplázás) -> 2.0m/click

        //2. upgrade buy() metódusával megvásárolni az adott upgrade-et
        // upg0.buy();
        //a 0-ás id-jű upgrade megkétszerezi a 0 indexű investment termelését -> 0.4m/sec
        // !!!NEM FELTÉTLENÜL AZ AZONOS ID-JŰT DUPLÁZZA EGY UPGRADE, CSAK EBBEN AZ ESETBEN LETT PONT ÍGY MEGHATÁROZVA!!!

        //!!!SEM A GAME.BUYUPGRADE(), SEM A GAME.BUYINVESTMENT() NEM VÉGEZ ELLENŐRZÉST, HOGY VAN-E ELÉG PÉNZ MEGVENNI,
        //CSAK AZ UPG.BUY() ÉS INV.BUY()FORMÁTUM VÉGEZ ILYET! A MÁSIK ESET HASZNÁLATAKOR MAGUNKNAK KELL GONDOSKODNI AZ ELLENŐRZÉSRŐL!!!

        //ez a függvény kell h frissüljenek az adatok az activityben
        refreshView();

        //100ms-enként frissíti a jelenlegi pénz tartalmát, DE NEM NÖVELI MEG!!! azt a Game saját magában elvégzi
        //ez csak a kirajzolásról gondoskodik
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


        //5 másodperc múlva megvesz pár cuccot automatikusan, csak a demózás gyanánt
//        Timer T2 = new Timer();
//
//        T2.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        game.buyInvestment(0);
//                        game.buyInvestment(1);
//                        game.buyUpgrade(3);
//                        refreshView(); //VÁSÁRLÁS UTÁN FRISSÍTENI KELL A NÉZETET!!
//                    }
//                });
//            }
//        }, 5000);
//
//        //TESZT VÉGE

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
