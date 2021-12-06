package edu.cnm.deepdive.marblemaze.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.marblemaze.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class SettingsRepository {

  private final SharedPreferences preferences;
  private final String mazeSpeedPrefKey;
  private final String mazeSizePrefKey;
  private final int mazeSpeedPrefDefault;
  private final int mazeSizePrefDefault;
  private final OnSharedPreferenceChangeListener listener;

  private ObservableEmitter<Integer> mazeSpeedPrefEmitter;
  private ObservableEmitter<Integer> mazeSizePrefEmitter;

  public SettingsRepository(Context context) {
    Resources resources = context.getResources();
    mazeSpeedPrefKey = resources.getString(R.string.maze_speed_pref_key);
    mazeSizePrefKey = resources.getString(R.string.maze_size_pref_key);
    mazeSpeedPrefDefault = resources.getInteger(R.integer.maze_speed_pref_default);
    mazeSizePrefDefault = resources.getInteger(R.integer.maze_size_pref_default);
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    listener = this::emitChangedPreferences;
    preferences.registerOnSharedPreferenceChangeListener(listener);
  }

  public Observable<Integer> getMazeSpeedPreference() {
    return Observable.create((emitter) -> {
      mazeSpeedPrefEmitter = emitter;
      emitChangedPreferences(preferences, mazeSpeedPrefKey);
    });
  }

  public Observable<Integer> getMazeSizePreference() {
    return Observable.create((emitter) -> {
      mazeSizePrefEmitter = emitter;
      emitChangedPreferences(preferences, mazeSizePrefKey);
    });
  }

  private void emitChangedPreferences(SharedPreferences prefs, String key) {
    if (key.equals(mazeSpeedPrefKey)) {
      if (mazeSpeedPrefEmitter != null && !mazeSpeedPrefEmitter.isDisposed()) {
        mazeSpeedPrefEmitter.onNext(prefs.getInt(key, mazeSpeedPrefDefault));
      }
    } else if (key.equals(mazeSizePrefKey)) {
      if (mazeSizePrefEmitter != null && !mazeSizePrefEmitter.isDisposed()) {
        mazeSizePrefEmitter.onNext(prefs.getInt(key, mazeSizePrefDefault));
      }
    }
  }

}
