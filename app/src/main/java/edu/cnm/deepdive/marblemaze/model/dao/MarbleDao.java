
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
public interface MarbleDao {

  @Insert
  Single<Long> insert(MarbleDao game);

  @Insert
  Single<List<Long>> insert(MarbleDao... games);

  @Insert
  Single<List<Long>> insert(Collection<MarbleDao> games);

  @Update
  Single<Integer> update(MarbleDao game);

  @Update
  Single<Integer> update(MarbleDao... games);

  @Update
  Single<Integer> update(Collection<MarbleDao> games);

  @Delete
  Single<Integer> delete(MarbleDao game);

  @Delete
  Single<Integer> delete(MarbleDao... games);

  @Delete
  Single<Integer> delete(Collection<MarbleDao> games);

  @Query("SELECT * FROM game ORDER BY created DESC")
  LiveData<List<MarbleDao>> selectAll();

  @Query("SELECT * FROM game WHERE marble_id = :gameId")
  LiveData<MarbleDao> select(long gameId);

  @Query("SELECT * FROM marble_summary WHERE pool_size = :poolSize AND length = :length ORDER BY guess_count ASC")
  LiveData<List<MarbleDao>> selectSummariesByGuessCount(int poolSize, int length);

  @Query("SELECT * FROM marble_summary WHERE pool_size = :poolSize AND length = :length ORDER BY total_time ASC")
  LiveData<List<MarbleDao>> selectSummariesByTotalTime(int poolSize, int length);

