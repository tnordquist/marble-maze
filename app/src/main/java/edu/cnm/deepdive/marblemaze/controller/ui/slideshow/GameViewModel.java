package edu.cnm.deepdive.marblemaze.controller.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public GameViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is my games slideshow fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}