package edu.cnm.deepdive.marblemaze.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(
    tableName = "game"
)
public class Game {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "game_id")
  private long id;


  @ColumnInfo(index = true)
  private int size;
  private int speed;
  private int time;
  private boolean completed;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}