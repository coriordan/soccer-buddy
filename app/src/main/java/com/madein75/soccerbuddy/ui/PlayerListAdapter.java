package com.madein75.soccerbuddy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Player;

import java.util.List;

public class PlayerListAdapter extends ArrayAdapter<Player> {

    private Context context;
    private List<Player> playerItems;
    private LayoutInflater inflater;

    public PlayerListAdapter(Context context, int resource, List<Player> playerItems) {
        super(context, resource, playerItems);

        this.context = context;
        this.playerItems = playerItems;
    }

    @Override
    public int getCount() {
        return playerItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        convertView = inflater.inflate(R.layout.player_list_row, parent, false);

        TextView playerName = (TextView) convertView.findViewById(R.id.player_name);

        Player p = playerItems.get(position);

        playerName.setText(p.getName());

        return convertView;
    }
}
