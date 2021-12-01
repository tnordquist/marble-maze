package edu.cnm.deepdive.marblemaze.service;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.marblemaze.model.dao.GameDao;
import edu.cnm.deepdive.marblemaze.model.entity.Game;
import edu.cnm.deepdive.marblemaze.model.pojo.Maze;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GameRepository {

  private final Application context;
  private final GameDao gameDao;

  public GameRepository(Application context) {
    this.context = context;
    gameDao = MarbleMazeDatabase
        .getInstance()
        .getGameDao();
    Log.d(getClass().getSimpleName(),"Starting Maze");
    Maze maze = generateMaze(10, 10, new Random());
    Log.d(getClass().getSimpleName(), maze.toString());
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

  private Maze generateMaze(int rows, int columns, Random rng) {
    return new Maze(rows, columns, rng);
  }

}
