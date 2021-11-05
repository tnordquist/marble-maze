package edu.cnm.deepdive.marblemaze.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.marblemaze.model.entity.Game;
import edu.cnm.deepdive.marblemaze.service.GameRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class GameViewModel
    extends AndroidViewModel
    implements LifecycleObserver {

  private final GameRepository repository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Long> gameId;
  private final LiveData<Game> game;
  private final CompositeDisposable pending;

  public GameViewModel(@NonNull Application application) {
    super(application);
    repository = new GameRepository(application);
    throwable = new MutableLiveData<>();
    gameId = new MutableLiveData<>();
    game = Transformations.switchMap(gameId, repository::get);
    pending = new CompositeDisposable();
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

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void save(Game note) {
    pending.add(
        repository
            .save(note)
            .subscribe(
                (savedGame) -> {
                },
                this::postThrowable
            )
    );
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }


}
