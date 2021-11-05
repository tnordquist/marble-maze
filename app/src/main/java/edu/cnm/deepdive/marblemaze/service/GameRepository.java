package edu.cnm.deepdive.marblemaze.service;

import android.app.Application;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.marblemaze.model.dao.GameDao;
import edu.cnm.deepdive.marblemaze.model.entity.Game;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.List;

public class GameRepository {

  private final Application context;
  private final GameDao gameDao;

  public GameRepository(Application context) {
    this.context = context;
    gameDao = MarblemazeDatabase
        .getInstance()
        .getGameDao();
  }

  public LiveData<Game> get(long gameId) {
    return gameDao.select(gameId);
  }

  public LiveData<List<Game>> getAll() {
    return gameDao.selectAll();
  }

  public Single<Game> save(Game game) {
    Single<Game> task;
    if (game.getId() == 0) {
      game.setCreated(new Date());
      task = gameDao
          .insert(game)
          .map((id) -> {
            game.setId(id);
            return game;
          });
    } else {
      task = gameDao
          .update(game)
          .map((count) -> game);
    }
    return task.subscribeOn(Schedulers.io());
  }

  public Completable delete(Game game) {
    return (game.getId() == 0)
        ? Completable.complete()
        : gameDao
            .delete(game)
            .ignoreElement()
            .subscribeOn(Schedulers.io());
  }

}
