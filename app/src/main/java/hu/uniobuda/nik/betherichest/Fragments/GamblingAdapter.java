package hu.uniobuda.nik.betherichest.Fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hu.uniobuda.nik.betherichest.GameObjects.Gambling;
import hu.uniobuda.nik.betherichest.R;


/**
 * Created by Szabi on 2017.05.01..
 */

public class GamblingAdapter extends BaseAdapter {
    private List<Gambling> items;

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView winAmountTextView;
    private TextView chanceTextView;
    private ImageView imageView;

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    public GamblingAdapter(List<Gambling> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Gambling getItem(int position) {
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
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_gambling, null);
        } else {
            listItemView = view;
        }

        nameTextView = (TextView) listItemView.findViewById(R.id.name);
        descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        winAmountTextView = (TextView) listItemView.findViewById(R.id.winAmount);
        chanceTextView = (TextView) listItemView.findViewById(R.id.chance);

        imageView = (ImageView) listItemView.findViewById(R.id.gambIcon);

        Gambling gambling = getItem(position);
        Glide
                .with(parent.getContext())
                .load(gambling.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);


        nameTextView.setText(gambling.getName());

        descriptionTextView.setText(String.valueOf(gambling.getDescription()));
        winAmountTextView.setText("Win amount: " + nf.format(gambling.getMinWinAmount()) + " - " + nf.format(gambling.getMaxWinAmount()) + "$");
        chanceTextView.setText("Chance: " + String.valueOf((int) gambling.getChance()) + "%");
        imageView.setBackgroundResource(gambling.getImageResource());

        return listItemView;
    }
}
