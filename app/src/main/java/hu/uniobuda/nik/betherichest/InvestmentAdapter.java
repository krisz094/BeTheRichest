package hu.uniobuda.nik.betherichest;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
    TextView totalDPSTextView;
    ImageView imageView;

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);


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
        totalDPSTextView = (TextView) listItemView.findViewById(R.id.total);
        imageView = (ImageView) listItemView.findViewById(R.id.upgradeIcon);

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


        priceTextView.setText(nf.format(investment.getPrice()));
        rankTextView.setText(String.valueOf(investment.getRank()));
        dpsPerRankTextView.setText("DPS: " + String.valueOf(nf.format(investment.getMoneyPerSecPerRank())));
        totalDPSTextView.setText("Total: " + String.valueOf(nf.format((investment.getMoneyPerSec())) + " (" + String.format("%.2f", investment.getDPSPercentage()) + "%)"));
        imageView.setBackgroundResource(investment.getImageResource());

        return listItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
