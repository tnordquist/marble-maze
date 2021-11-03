package edu.cnm.deepdive.marblemaze.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import com.google.gson.annotations.Expose;
import java.util.Date;

@Entity(
    tableName = "game",
    indices = {
        @Index(value = {"service_key"}, unique = true)
    }
)
public class Game {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "game_id")
  private long id;

  @NonNull
  @Expose
  @ColumnInfo(index = true)
  private Date created = new Date();


  @ColumnInfo(index = true)
  private int Size;
  private int speed;
  private int time;
  private int completed;

  public int getSize() {
    return Size;
  }

  public void setSize(int size) {
    Size = size;
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

  public int getCompleted() {
    return completed;
  }

  public void setCompleted(int completed) {
    this.completed = completed;
  }
}