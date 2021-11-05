package edu.cnm.deepdive.marblemaze.service;

import androidx.room.Database;
import edu.cnm.deepdive.marblemaze.model.entity.Game;

@Database(
    entities = {Game.class},
    views = {GameSummary.class},
    version = 1,
    exportSchema = true
)

}
