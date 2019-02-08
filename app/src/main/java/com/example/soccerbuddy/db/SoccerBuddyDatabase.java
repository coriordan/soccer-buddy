package com.example.soccerbuddy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.example.soccerbuddy.model.Match;
import com.example.soccerbuddy.model.SkillLevel;

import java.util.Date;

@Database(entities = {Match.class},
        version=1,
        exportSchema = false)
@TypeConverters(SoccerBuddyDatabase.class)
public abstract class SoccerBuddyDatabase extends RoomDatabase {

    public abstract MatchDao matchDao();

    @TypeConverter
    public static Long fromDate(final Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(final Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String fromSkillLevel(final SkillLevel value) {
        return value == null ? null : value.name();
    }

    @TypeConverter
    public static SkillLevel toSkillLevel(final String name) {
        return name == null ? null : SkillLevel.valueOf(name);
    }

}
