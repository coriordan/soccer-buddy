package com.madein75.soccerbuddy.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.madein75.soccerbuddy.model.Match;
import com.madein75.soccerbuddy.model.SkillLevel;

import java.util.Date;

@Database(entities = {Match.class},
        version=2,
        exportSchema = true)
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

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE matches "
                        + " ADD COLUMN kickoffTime INTEGER");
        }
    };

}

