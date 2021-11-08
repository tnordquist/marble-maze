package edu.cnm.deepdive.marblemaze.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.marblemaze.adapter.GameAdapter;
import edu.cnm.deepdive.marblemaze.databinding.FragmentGameBinding;
import edu.cnm.deepdive.marblemaze.viewmodel.GameViewModel;

public class GameFragment extends Fragment {

  private FragmentGameBinding binding;
  private GameViewModel viewModel;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {

    binding = FragmentGameBinding.inflate(inflater, container, false);
    binding.addGame.setOnClickListener(
        this::addGame
    );

    return binding.getRoot();

  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(
        GameViewModel.class
    );
    viewModel
        .getGames()
        .observe(getViewLifecycleOwner(), (games) -> {
          GameAdapter adapter = new GameAdapter(getContext(), games);
          binding.games.setAdapter(adapter);
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void addGame(View v) {
    Navigation.findNavController(binding.getRoot())
        .navigate(GameFragmentDirections.openGame()); //generated for us from fragment
  }

}