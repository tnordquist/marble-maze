package edu.cnm.deepdive.marblemaze;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.marblemaze.service.GoogleSignInRepository;
import edu.cnm.deepdive.marblemaze.service.MarbleMazeDatabase;
import io.reactivex.schedulers.Schedulers;

/**
 * Initializes (in the {@link #onCreate()} method) application-level resources. This class
 * <strong>must</strong> be referenced in {@code AndroidManifest.xml}, or it will not be loaded and
 * used by the Android system.
 */
public class MarbleMazeApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    MarbleMazeDatabase.setContext(this);
    GoogleSignInRepository.setContext(this);
    MarbleMazeDatabase
        .getInstance()
        .getGameDao()
        .delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

}
