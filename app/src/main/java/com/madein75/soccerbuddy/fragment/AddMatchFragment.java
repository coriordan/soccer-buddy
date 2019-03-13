package com.madein75.soccerbuddy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.madein75.soccerbuddy.activity.MainActivity;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.SkillLevel;
import com.madein75.soccerbuddy.widget.DatePickerLayout;
import com.madein75.soccerbuddy.widget.TimePickerLayout;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchFragment extends Fragment {

    public static final String EXTRA_MATCH_ITEM = "com.example.soccerbuddy.extras.MATCH_ITEM";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPlayersRequired;
    private DatePickerLayout datePickerFixtureDate;
    private TimePickerLayout timePickerKickOffTime;
    private RadioGroup radioGroupSkillLevels;
    private TextView textViewSelectedLevel;

    private Match matchItem;

    public AddMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        final View details = inflater.inflate(R.layout.fragment_add_match,
                container,
                false);

        editTextTitle = details.findViewById(R.id.title);
        editTextDescription = details.findViewById(R.id.description);
        editTextPlayersRequired = details.findViewById(R.id.playersRequired);
        datePickerFixtureDate = details.findViewById(R.id.date);
        timePickerKickOffTime = details.findViewById(R.id.time);
        radioGroupSkillLevels = details.findViewById(R.id.skillLevels);
        textViewSelectedLevel = details.findViewById(R.id.selected_level);

        radioGroupSkillLevels.setOnCheckedChangeListener(
                new IconPickerWrapper(textViewSelectedLevel));
        radioGroupSkillLevels.check(R.id.easy);

        return details;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_match_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveMatch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveMatch() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int players = TextUtils.isEmpty(editTextPlayersRequired.getText().toString()) ?
                0 : Integer.parseInt(editTextPlayersRequired.getText().toString());
        Date fixtureDate = datePickerFixtureDate.getDate();
        Date kickOffTime = timePickerKickOffTime.getTime();
        SkillLevel skillLevel = SkillLevel
                                    .forIdResource(radioGroupSkillLevels.getCheckedRadioButtonId());

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.provide_missing_match_details), Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference matchesRef = FirebaseFirestore.getInstance()
                .collection("matches");

        matchesRef.add(new Match(title, description, players, new Date(), fixtureDate, kickOffTime, skillLevel.name()));

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
