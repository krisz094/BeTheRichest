package hu.uniobuda.nik.betherichest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
    TextView priceTextView;
    ImageView imageView;
    TextView labelTextView;
    RelativeLayout relativeLayout;
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
    Context context;

    public UpgradeAdapter(List<Upgrade> items, Context context) {
        this.items = items;
        this.context = context;
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
        } else {
            priceTextView.setTextColor(Color.parseColor("#F2003C"));
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
        CreateColorfulBorder(upgrade);


        ConvertThousandsToSIUnit(upgrade);

        return listItemView;
    }

    private void CreateColorfulBorder(Upgrade upgrade) {
        // makes a dynamic border around the relativlayout which contains the image and the effect text
        GradientDrawable gd = new GradientDrawable();
        // different borderSize in pixels for different density displays
        gd.setStroke((int) getPixelFromDP(3), upgrade.getColor());
        gd.setCornerRadius(getPixelFromDP(15));
        relativeLayout.setBackground(gd);
    }

    private void ConvertThousandsToSIUnit(Upgrade upgrade) {
        long price = upgrade.getPrice();
        if (price < 10000) {
            priceTextView.setText(nf.format(upgrade.getPrice()));
        } else if (price >= 10000 && price < 1000000) {
            priceTextView.setText(nf.format(price / 1000) + "K");
        } else if (price >= 1000000 && price < 1000000000) {
            priceTextView.setText(nf.format(price / 1000000) + "M");
        } else if (price >= 1000000000) {
            priceTextView.setText(nf.format(price / 1000000000) + "B");
        }
    }

    private float getPixelFromDP(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
