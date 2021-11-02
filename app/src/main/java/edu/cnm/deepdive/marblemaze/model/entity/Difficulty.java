package edu.cnm.deepdive.marblemaze.model.entity;

import androidx.room.Entity;
import java.util.UUID;

@Entity
public class Difficulty {

  private UUID id;

  private int difficulty;

}
