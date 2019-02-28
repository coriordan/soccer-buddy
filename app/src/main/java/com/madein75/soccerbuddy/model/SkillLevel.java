package com.madein75.soccerbuddy.model;

import android.support.annotation.IdRes;

import com.madein75.soccerbuddy.R;

public enum SkillLevel {
    EASY(R.id.easy),
    MODERATE(R.id.moderate),
    COMPETITIVE(R.id.competitive);

    @IdRes
    private final int idResource;
    SkillLevel(@IdRes final int idResource) { this.idResource = idResource; }
    @IdRes
    public int getIdResource() { return idResource; }

    public static SkillLevel forIdResource(@IdRes final int id) {
        for (final SkillLevel l : values()) {
            if (l.idResource == id) {
                return l;
            }
        }

        throw new IllegalArgumentException("No skill level for ID: " + id);
    }
}