package hu.uniobuda.nik.betherichest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        imageView = (ImageView) listItemView.findViewById(R.id.upgradeIcon);
        labelTextView = (TextView) listItemView.findViewById(R.id.multiplier);
        relativeLayout = (RelativeLayout) listItemView.findViewById(R.id.layout);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
//
//        android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//        layoutParams.width = (int) (size.x / 4 - size.x * 0.3);
//        imageView.setLayoutParams(layoutParams);

//        imageView.requestLayout();
//        imageView.getLayoutParams().width = (int) (size.x / 4 - size.x * 0.3);


        Upgrade upgrade = items.get(position);
        Glide
                .with(parent.getContext())
                .load(upgrade.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(size.x / 5, size.x / 5);
        relativeLayout.setLayoutParams(layoutParams);
//        layoutParams = new RelativeLayout.LayoutParams((int) (size.x / 4 * 0.7), (int) (size.x / 4 * 0.7));
//        imageView.setLayoutParams(layoutParams);


        labelTextView.setTextColor(upgrade.getColor());
        labelTextView.setText(upgrade.getEffectExtraInfo());

        SetTextColorByAvailability(upgrade);

        CreateColorfulBorder(upgrade);

        ConvertThousandsToSIUnit(upgrade);

        return listItemView;
    }

    private void SetTextColorByAvailability(Upgrade upgrade) {
        // sets text color based on availability
        if (upgrade.isBuyable()) {
            priceTextView.setTextColor(Color.parseColor("#90EE90"));
        } else {
            priceTextView.setTextColor(Color.parseColor("#F2003C"));
        }
    }

    private void CreateColorfulBorder(Upgrade upgrade) {
        // makes a dynamic border around the relativlayout which contains the image and the effect text
        GradientDrawable gd = new GradientDrawable();
        // different borderSize in pixels for different density displays
        gd.setStroke((int) getPixelFromDP(3), upgrade.getColor());
        gd.setCornerRadius(getPixelFromDP(13));
        relativeLayout.setBackground(gd);
    }

    private void ConvertThousandsToSIUnit(Upgrade upgrade) {

        long price = upgrade.getPrice();
        if (price < 10000) {
            priceTextView.setText(nf.format(price));
        } else if (price >= 10000 && price < 1000000) {
            priceTextView.setText(nf.format(price / 1000d) + "K");
        } else if (price >= 1000000 && price < 1000000000) {
            priceTextView.setText(nf.format(price / 1000000d) + "M");
        } else if (price >= 1000000000) {
            priceTextView.setText(nf.format(price / 1000000000d) + "B");
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
