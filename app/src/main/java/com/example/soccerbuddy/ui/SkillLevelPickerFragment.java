package com.example.soccerbuddy.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.soccerbuddy.R;
import com.example.soccerbuddy.model.SkillLevel;

public class SkillLevelPickerFragment extends Fragment {

    private RadioGroup skillLevels;
    private TextView selectedLevelLabel;

    public SkillLevelPickerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View picker = inflater.inflate(R.layout.fragment_skill_level_picker,
                container,
                false);

        skillLevels = (RadioGroup) picker.findViewById(R.id.skillLevels);
        selectedLevelLabel = (TextView) picker.findViewById(R.id.selected_level);
        skillLevels.setOnCheckedChangeListener(
                new IconPickerWrapper(selectedLevelLabel));
        skillLevels.check(R.id.easy);
        return picker;
    }

    public void setSelectedSkillLevel(final SkillLevel level) {
        skillLevels.check(level.getIdResource());
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
