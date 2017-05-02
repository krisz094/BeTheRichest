package hu.uniobuda.nik.betherichest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.provider.CalendarContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;

import java.text.DecimalFormat;
import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;

/**
 * Created by Szabi on 2017.04.15..
 */

public class InvestmentAdapter extends BaseAdapter {

    private List<Investment> items;
    TextView nameTextView;
    TextView priceTextView;
    TextView dpsPerRankTextView;
    TextView rankTextView;
    TextView descriptionTextView;
    TextView totalDPSTextView;
    ImageView imageView;


    public InvestmentAdapter(List<Investment> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Investment getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View listItemView = view;

        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_investment, null);
        } else {
            listItemView = view;
        }

        nameTextView = (TextView) listItemView.findViewById(R.id.name);
        priceTextView = (TextView) listItemView.findViewById(R.id.price);
        dpsPerRankTextView = (TextView) listItemView.findViewById(R.id.dpsPerRank);
        rankTextView = (TextView) listItemView.findViewById(R.id.rank);
        //  descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        totalDPSTextView = (TextView) listItemView.findViewById(R.id.total);
        imageView = (ImageView) listItemView.findViewById(R.id.invIcon);

        Investment investment = items.get(position);
        Glide
                .with(parent.getContext())
                .load(investment.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);


        nameTextView.setText(investment.getName());

        if (investment.isBuyable()) {
            nameTextView.setTextColor(Color.parseColor("#0c6f04"));
        } else {
            nameTextView.setTextColor(Color.parseColor("#760c07"));
        }

        priceTextView.setText(String.format("%,d", (int) investment.getPrice()));
        dpsPerRankTextView.setText("DPS: " + String.valueOf(investment.getMoneyPerSecPerRank()));
        rankTextView.setText(String.valueOf(investment.getRank()));
//        descriptionTextView.setText(investment.getDescription());
        totalDPSTextView.setText("Total: " + String.valueOf(new DecimalFormat("0.0")
                .format((investment.getMoneyPerSec())) + " (" + String.valueOf(String.format("%.2f", investment.getDPSPercentage())) + "%)"));
        ;
        imageView.setBackgroundResource(investment.getImageResource());

        return listItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
