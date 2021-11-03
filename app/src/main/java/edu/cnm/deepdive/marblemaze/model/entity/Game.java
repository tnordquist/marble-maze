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
  private Difficulty difficulty;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }


  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    @TypeConverter
    public static Integer difficultyToInteger(Difficulty difficulty) {
      return (difficulty != null) ? difficulty.ordinal() : null;
    }

    @TypeConverter
    public static Difficulty integerToDifficulty(Integer integer) {
      return (integer != null) ? Difficulty.values()[integer] : null;
    }
  }

    public Difficulty difficultyname() {
      Difficulty[] values = Difficulty.values();
      return values[getDifficulty().ordinal()];
    }
}

