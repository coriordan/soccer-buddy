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

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.madein75.soccerbuddy.activity.MainActivity;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.SkillLevel;
import com.madein75.soccerbuddy.widget.DatePickerLayout;
import com.madein75.soccerbuddy.widget.TimePickerLayout;

import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchFragment extends Fragment {

    private static final String TAG = AddMatchFragment.class.getName();
    public static final String EXTRA_MATCH_ITEM = "com.example.soccerbuddy.extras.MATCH_ITEM";

    @BindView(R.id.title)
    EditText editTextTitle;

    @BindView(R.id.description)
    EditText editTextDescription;

    @BindView(R.id.playersRequired)
    EditText editTextPlayersRequired;

    @BindView(R.id.date)
    DatePickerLayout datePickerFixtureDate;

    @BindView(R.id.time)
    TimePickerLayout timePickerKickOffTime;

    @BindView(R.id.skillLevels)
    RadioGroup radioGroupSkillLevels;

    @BindView(R.id.selected_level)
    TextView textViewSelectedLevel;

    private Match matchItem;
    private Unbinder unbinder;

    private MapFragment mapFragment;

    public AddMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        final View view = inflater.inflate(R.layout.fragment_add_match,
                container, false);

        unbinder = ButterKnife.bind(this, view);

        radioGroupSkillLevels.setOnCheckedChangeListener(
                new IconPickerWrapper(textViewSelectedLevel));
        radioGroupSkillLevels.check(R.id.easy);

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        return view;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        LatLng location = mapFragment.getMarkedLocation();

        CollectionReference matchesRef = FirebaseFirestore.getInstance()
                .collection("Matches");

        Match match = new Match(
                FirebaseAuth.getInstance().getCurrentUser(),
                title,
                description,
                players,
                new Date(),
                fixtureDate,
                kickOffTime,
                skillLevel.name(),
                new GeoPoint(location.latitude, location.longitude),
                Collections.<String>emptyList());

        matchesRef.add(match);

        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
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
