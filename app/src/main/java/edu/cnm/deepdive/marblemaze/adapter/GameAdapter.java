package edu.cnm.deepdive.marblemaze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.marblemaze.databinding.ItemGameBinding;
import edu.cnm.deepdive.marblemaze.model.entity.Game;
import java.text.DateFormat;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.Holder> {

  private final LayoutInflater inflator;
  private final DateFormat dateFormat;
  private final List<Game> games;

  public GameAdapter(Context context, List<Game> games) {
    inflator = LayoutInflater.from(context);
    dateFormat = android.text.format.DateFormat.getDateFormat(context);
    this.games = games;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(ItemGameBinding.inflate(inflator, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return games.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private final ItemGameBinding binding;

    private Holder(@NonNull ItemGameBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      //Use contents of model object to set contents of binding fields.
      Game game = games.get(position);
      binding.size.setText(String.valueOf(game.getSize()));
      binding.created.setText(dateFormat.format(game.getCreated()));
      binding.speed.setText(String.valueOf(game.getSpeed()));
      binding.time.setText(String.valueOf(game.getTime()));
      binding.completed.setText(String.valueOf(game.isCompleted()));
    }
  }

}
