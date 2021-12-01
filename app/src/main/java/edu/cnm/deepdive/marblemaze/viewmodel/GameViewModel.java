package edu.cnm.deepdive.marblemaze.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.marblemaze.R;
import edu.cnm.deepdive.marblemaze.model.entity.Game;
import edu.cnm.deepdive.marblemaze.model.pojo.Maze;
import edu.cnm.deepdive.marblemaze.service.GameRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class GameViewModel
    extends AndroidViewModel
    implements DefaultLifecycleObserver {

  private final GameRepository repository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Long> gameId;
  private final LiveData<Game> game;
  private final MutableLiveData<Maze> maze;
  private final CompositeDisposable pending;
  private final SharedPreferences preferences;
  private final String mazeSpeedPrefKey;
  private final String mazeSizePrefKey;
  private final int mazeSpeedPrefDefault;
  private final int mazeSizePrefDefault;


  public GameViewModel(@NonNull Application application) {
    super(application);
    repository = new GameRepository(application);
    throwable = new MutableLiveData<>();
    gameId = new MutableLiveData<>();
    game = Transformations.switchMap(gameId, repository::get);
    maze = new MutableLiveData<>();
    pending = new CompositeDisposable();
    Resources resources = application.getResources();
    mazeSpeedPrefKey = resources.getString(R.string.maze_speed_pref_key);
    mazeSizePrefKey = resources.getString(R.string.maze_size_pref_key);
    mazeSpeedPrefDefault = resources.getInteger(R.integer.maze_speed_pref_default);
    mazeSizePrefDefault = resources.getInteger(R.integer.maze_size_pref_default);
    preferences = PreferenceManager.getDefaultSharedPreferences(application);

  }

  public LiveData<Game> getGame() {
    return game;
  }

  public void setGameId(long id) {
    gameId.setValue(id);
  }

  public LiveData<List<Game>> getGames() {
    return repository.getAll();
  }

  public LiveData<Maze> getMaze() {
    return maze;
  }

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void save(Game game) {
    pending.add(
        repository
            .save(game)
            .subscribe(
                (savedGame) -> {
                },
                this::postThrowable
            )
    );
  }

  public void buildMaze() {
    throwable.setValue(null);
    pending.add(
        repository
            .generateMaze(preferences.getInt(mazeSizePrefKey, mazeSizePrefDefault))
            .subscribe(
                maze::postValue,
                this::postThrowable
            )
    );
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }


}

