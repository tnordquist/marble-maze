package edu.cnm.deepdive.marblemaze.model.entity;

import androidx.room.Entity;
import java.util.Date;
import java.util.UUID;

@Entity
public class Game {

  private UUID id;

  private Difficulty difficulty;
  private Skins skins;
  private Login login; null;

  private UUID externalKey;
  private Date created;
  private String pool;
  private int length;
  private String text;

}