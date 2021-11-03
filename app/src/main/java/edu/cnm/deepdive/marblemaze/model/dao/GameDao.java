
package edu.cnm.deepdive.marblemaze.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface GameDao {

  @Insert
  Single<Long> insert(GameDao game);

  @Insert
  Single<List<Long>> insert(GameDao... games);

  @Insert
  Single<List<Long>> insert(Collection<GameDao> games);

  @Update
  Single<Integer> update(GameDao game);

  @Update
  Single<Integer> update(GameDao... games);

  @Update
  Single<Integer> update(Collection<GameDao> games);

  @Delete
  Single<Integer> delete(GameDao game);

  @Delete
  Single<Integer> delete(GameDao... games);

  @Delete
  Single<Integer> delete(Collection<GameDao> games);

  @Query("SELECT * FROM game ORDER BY created DESC")
  LiveData<List<GameDao>> selectAll();

  @Query("SELECT * FROM game WHERE game_id = :gameId")
  LiveData<GameDao> select(long gameId);

  @Query("SELECT * FROM game WHERE pool_size = :poolSize AND length = :length ORDER BY guess_count ASC")
  LiveData<List<GameDao>> selectSummariesByGuessCount(int poolSize, int length);

  @Query("SELECT * FROM game WHERE pool_size = :poolSize AND length = :length ORDER BY total_time ASC")
  LiveData<List<GameDao>> selectSummariesByTotalTime(int poolSize, int length);

