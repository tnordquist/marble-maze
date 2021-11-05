package edu.cnm.deepdive.marblemaze.service;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import edu.cnm.deepdive.marblemaze.model.entity.Game;

@Database(
    entities = {Game.class},
    version = 1,
    exportSchema = true
)
public abstract class MarblemazeDatabase extends RoomDatabase {

}
