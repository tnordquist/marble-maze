package edu.cnm.deepdive.marblemaze.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.marblemaze.model.dao.GameDao;
import edu.cnm.deepdive.marblemaze.model.entity.Game;
import edu.cnm.deepdive.marblemaze.service.MarbleMazeDatabase.Converters;
import java.util.Date;

@Database(
    entities = {Game.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class MarbleMazeDatabase extends RoomDatabase {

  private static Application context;

  public static void setContext(Application context) {
    MarbleMazeDatabase.context = context;
  }

  public static MarbleMazeDatabase getInstance() {
    return MarbleMazeDatabase.InstanceHolder.INSTANCE;
  }

  public abstract GameDao getGameDao();

  private static class InstanceHolder {

    private static final MarbleMazeDatabase INSTANCE =
        Room.databaseBuilder(context,
                MarbleMazeDatabase.class,
                "marblemaze-db")
            .build();

  }

  public static class Converters {

    @TypeConverter
    public static Long dateToLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date longToDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }


}
