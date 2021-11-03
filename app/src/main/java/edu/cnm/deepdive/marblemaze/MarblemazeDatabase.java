package edu.cnm.deepdive.marblemaze;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.marblemaze.MarblemazeDatabase.Converters;
import edu.cnm.deepdive.marblemaze.model.dao.GameDao;
import edu.cnm.deepdive.marblemaze.model.dao.MarbleDao;
import java.util.Date;

@Database(
    entities = {GameDao.class, MarbleDao.class},
    views = {GameDao.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class MarblemazeDatabase extends RoomDatabase {

  private static Application context;

  public static void setContext(Application context) {
    MarblemazeDatabase.context = context;
  }

  public static MarblemazeDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public abstract GameDao getGameDao();

  public abstract MarbleDao getGuessDao();

  private static class InstanceHolder {

    private static final MarblemazeDatabase INSTANCE =
        Room.databaseBuilder(context, MarblemazeDatabase.class, "codebreaker-db")
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
