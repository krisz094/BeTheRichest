package hu.uniobuda.nik.betherichest;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);

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

        //nameTextView = (TextView) listItemView.findViewById(R.id.name);
        priceTextView = (TextView) listItemView.findViewById(R.id.price);
        //descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        imageView = (ImageView) listItemView.findViewById(R.id.invIcon);
        labelTextView=(TextView) listItemView.findViewById(R.id.multiplier);

        Upgrade upgrade = items.get(position);
        Glide
                .with(parent.getContext())
                .load(upgrade.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        //nameTextView.setText(upgrade.getName());

        if (upgrade.isBuyable()) {
            labelTextView.setTextColor(Color.parseColor("#BB0c6f04"));
        } else {
            labelTextView.setTextColor(Color.parseColor("#BB760c07"));
        }
        switch(upgrade.getEffect()) {

            case "DoublerEffect":
                labelTextView.setText("X2");
                break;
            case "GlobalIncrementEffect":
                labelTextView.setText("GI"); // TODO TO CHANGE
                break;
            case "MultiplierEffect":
                labelTextView.setText("ME"); // TODO TO CHANGE
                break;

        }

        priceTextView.setText(nf.format(upgrade.getPrice()));
        //descriptionTextView.setText(upgrade.getDescription());

        //imageView.setBackgroundResource(upgrade.getImageResource());

        return listItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
