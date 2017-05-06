package hu.uniobuda.nik.betherichest;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hu.uniobuda.nik.betherichest.GameObjects.Leader;

/**
 * Created by Kristof on 2017. 04. 17..
 */

class LeaderAdapter extends BaseAdapter {

    private List<Leader> leaderList;

    LeaderAdapter(List<Leader> leaderList) {
        this.leaderList = leaderList;
    }

    @Override
    public int getCount() {
        return leaderList == null ? 0 : leaderList.size();
    }

    @Override
    public Object getItem(int position) {
        return leaderList == null ? null : leaderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_leaderboard, null);
        }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        TextView moneyTextView = (TextView) listItemView.findViewById(R.id.money);
        TextView personRankTextView = (TextView) listItemView.findViewById(R.id.personRank);


        Leader leader = leaderList.get(position);

        if (leader.isPlayer()) {
            setListItemLayout(listItemView, nameTextView, moneyTextView, personRankTextView, R.drawable.silver, Color.parseColor("#646464"));
        }
        else{
            setListItemLayout(listItemView, nameTextView, moneyTextView, personRankTextView, R.drawable.gold, Color.WHITE);
        }

        nameTextView.setText(leader.getName());
        moneyTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(leader.getMoney()));
        personRankTextView.setText("#" + String.valueOf(getItemId(position + 1)));
        return listItemView;
    }

    private void setListItemLayout(View listItemView, TextView nameTextView, TextView moneyTextView, TextView personRankTextView, int sil, int color) {
        listItemView.setBackgroundResource(sil);
        nameTextView.setTextColor(color);
        moneyTextView.setTextColor(color);
        personRankTextView.setTextColor(color);
    }
}