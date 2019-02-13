package com.example.soccerbuddy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.soccerbuddy.model.Match;

import java.util.List;
@Dao
public interface MatchDao {

    @Query("SELECT * FROM matches ORDER BY created_at DESC")
    LiveData<List<Match>> selectAll();

    @Query("SELECT * FROM matches WHERE id =:matchId")
    LiveData<Match> getMatch(long matchId);

    @Insert
    long insert(Match item);

    @Update
    void update(Match item);

    @Delete
    void delete(Match item);
}
