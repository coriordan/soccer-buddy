package com.example.soccerbuddy;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.soccerbuddy.model.Match;
import com.example.soccerbuddy.model.SkillLevel;
import com.example.soccerbuddy.widget.DatePickerLayout;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchDetailsFragment extends Fragment {

    public static final String EXTRA_MATCH_ITEM = "com.example.soccerbuddy.extras.MATCH_ITEM";

    private EditText title;
    private EditText description;
    private EditText playersRequired;
    private DatePickerLayout selectedDate;
    private RadioGroup skillLevels;
    private TextView selectedLevelLabel;

    private Match matchItem;

    public MatchDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View details = inflater.inflate(R.layout.fragment_match_details,
                container,
                false);

        title = (EditText) details.findViewById(R.id.title);
        description = (EditText) details.findViewById(R.id.description);
        playersRequired = (EditText) details.findViewById(R.id.playersRequired);
        selectedDate = (DatePickerLayout) details.findViewById(R.id.date);
        skillLevels = (RadioGroup) details.findViewById(R.id.skillLevels);
        selectedLevelLabel = (TextView) details.findViewById(R.id.selected_level);

        skillLevels.setOnCheckedChangeListener(
                new IconPickerWrapper(selectedLevelLabel));
        skillLevels.check(R.id.easy);

        // create our match model item
        if (matchItem == null) {
            matchItem = new Match();
        }

        Button addButton = details.findViewById(R.id.saveMatch);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatch();
            }
        });

        return details;
    }

    private void addMatch() {
        SoccerBuddyApplication app = (SoccerBuddyApplication) getActivity().getApplication();

        int players = TextUtils.isEmpty(playersRequired.getText().toString()) ?
                0 : Integer.parseInt(playersRequired.getText().toString());

        app.getSoccerBuddyRepository().insertMatch(
                title.getText().toString(),
                description.getText().toString(),
                players,
                selectedDate.getDate(),
                SkillLevel.forIdResource(skillLevels.getCheckedRadioButtonId())
        );

        startActivity(new Intent(this.getActivity(), MainActivity.class));
    }

    class IconPickerWrapper implements RadioGroup.OnCheckedChangeListener {

        private final TextView label;

        public IconPickerWrapper(final TextView label) {
            this.label = label;
        }

        public void setLabelText(final CharSequence text) {
            label.setText(text);
        }

        @Override
        public void onCheckedChanged(final RadioGroup group, final int checkedId) {
            setLabelText(group.findViewById(checkedId).getContentDescription());
        }
    }

}
