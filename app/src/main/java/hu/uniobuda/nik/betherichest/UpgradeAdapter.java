package hu.uniobuda.nik.betherichest;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hu.uniobuda.nik.betherichest.GameObjects.Upgrade;

/**
 * Created by Márk on 2017. 05. 06..
 */

public class UpgradeAdapter extends BaseAdapter {

    private List<Upgrade> items;
    //TextView nameTextView;
    TextView dpsPerRankTextView;
    TextView descriptionTextView;
    TextView priceTextView;
    ImageView imageView;
    TextView labelTextView;
    RelativeLayout relativeLayout;
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    public UpgradeAdapter(List<Upgrade> items) {
        this.items = items;
    }

    public void setItems(List<Upgrade> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Upgrade getItem(int position) {
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
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_upgrade, null);
        } else {
            listItemView = view;
        }


        priceTextView = (TextView) listItemView.findViewById(R.id.price);
        imageView = (ImageView) listItemView.findViewById(R.id.invIcon);
        labelTextView = (TextView) listItemView.findViewById(R.id.multiplier);
        relativeLayout = (RelativeLayout) listItemView.findViewById(R.id.layout);

        Upgrade upgrade = items.get(position);
        Glide
                .with(parent.getContext())
                .load(upgrade.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        // sets text color based on availability
        if (upgrade.isBuyable()) {
            priceTextView.setTextColor(Color.parseColor("#90EE90"));
            //labelTextView.setTextColor(Color.parseColor("#DDffffff"));
        } else {
            priceTextView.setTextColor(Color.parseColor("#F2003C"));
            //labelTextView.setTextColor(Color.parseColor("#DDffffff"));
        }

        labelTextView.setTextColor(upgrade.getColor());
        /*switch (upgrade.getEffect()) {

            case "DoublerEffect":
                labelTextView.setText("X2");
                break;
            case "GlobalIncrementEffect":
                labelTextView.setText(upgrade.getEffectExtraInfo()); // TODO TO CHANGE
                break;
            case "MultiplierEffect":
                labelTextView.setText("ME"); // TODO TO CHANGE
                break;

        }*/

        labelTextView.setText(upgrade.getEffectExtraInfo());

        // makes a dynamic border around the relativlayout which contains the image and the effect text
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(2, upgrade.getColor());
        gd.setCornerRadius(8);
        relativeLayout.setBackground(gd);
        long price = upgrade.getPrice();
        if (price < 10000) {
            priceTextView.setText(nf.format(upgrade.getPrice()));
        } else if (price >= 10000 && price < 1000000) {
            priceTextView.setText(String.format("%.1f", (double)price/1000) + "K");
        } else if (price >= 1000000 && price < 1000000000) {
            priceTextView.setText(String.format("%.1f", (double)price/1000000) + "M");
        } else if (price >= 1000000000) {
            priceTextView.setText(String.format("%.1f", (double)price/1000000) + "B");
        }

        return listItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
