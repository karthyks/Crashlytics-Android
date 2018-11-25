package com.github.karthyks.crashlytics.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EventDao {

  @Query("SELECT * FROM event")
  List<Event> getEvents();

  @Insert
  void insertEvent(Event... events);

  @Query("DELETE FROM event")
  void deleteAll();

  @Delete
  void deleteEvent(Event... events);
}