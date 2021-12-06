package edu.cnm.deepdive.marblemaze.model.pojo;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class Maze {

  private final int rows;
  private final int columns;
  private final Random rng;
  private final Cell[][] cells;

  public Maze(int rows, int columns, Random rng) {
    this.rows = rows;
    this.columns = columns;
    this.rng = rng;
    cells = new Cell[rows][columns];
    for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
      for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
        cells[rowIndex][columnIndex] = new Cell(rowIndex, columnIndex);
      }
    }
    cells[0][0].build();
  }

  @NonNull
  @Override
  public String toString() {
    return Arrays
        .stream(cells)
        .map(Arrays::toString)
        .collect(Collectors.joining("\n"));
  }

  public class Cell {

    private final EnumSet<Direction> walls;
    private final int row;
    private final int column;

    private boolean visited;

    public Cell(int row, int column) {
      this.row = row;
      this.column = column;
      walls = EnumSet.allOf(Direction.class);

    }

    private void build() {
      visited = true;
      EnumSet<Direction> forbiddenWalls = EnumSet.noneOf(Direction.class);
      if (row == 0) {
        forbiddenWalls.add(Direction.NORTH);
      }
      if (row == rows - 1) {
        forbiddenWalls.add(Direction.SOUTH);
      }
      if (column == 0) {
        forbiddenWalls.add(Direction.WEST);
      }
      if (column == columns - 1) {
        forbiddenWalls.add(Direction.EAST);
      }
      EnumSet<Direction> activeWalls = EnumSet.copyOf(walls);
      activeWalls.removeAll(forbiddenWalls);
      while (!activeWalls.isEmpty()) {
        List<BuildDirection> neighbors = neighbors(activeWalls);
        if (!neighbors.isEmpty()) {
          BuildDirection neighbor = neighbors.get(rng.nextInt(neighbors.size()));
          walls.remove(neighbor.direction);
          activeWalls.remove(neighbor.direction);
          neighbor.cell.walls.remove(neighbor.direction.opposite());
          neighbor.cell.build();
        } else {
          break;
        }
      }
    }

    private List<BuildDirection> neighbors(EnumSet<Direction> activeWalls) {
      List<BuildDirection> neighbors = new ArrayList<>();
      for (Direction direction : activeWalls) {
        Cell neighbor = cells[row + direction.rowOffset][column + direction.columnOffset];
        if (!neighbor.visited) {
          neighbors.add(new BuildDirection(direction, neighbor));
        }
      }
      return neighbors;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
      return walls.toString();
    }
  }

  private static class BuildDirection {

    private final Direction direction;
    private final Cell cell;

    private BuildDirection(Direction direction,
        Cell cell) {
      this.direction = direction;
      this.cell = cell;
    }
  }

  public enum Direction {
    NORTH(-1, 0) {
      @Override
      public Direction opposite() {
        return SOUTH;
      }
    },
    EAST(0, 1) {
      @Override
      public Direction opposite() {
        return WEST;
      }
    },
    SOUTH(1, 0) {
      @Override
      public Direction opposite() {
        return NORTH;
      }
    },
    WEST(0, -1) {
      @Override
      public Direction opposite() {
        return EAST;
      }
    };

    private final int rowOffset;
    private final int columnOffset;

    Direction(int rowOffset, int columnOffset) {
      this.rowOffset = rowOffset;
      this.columnOffset = columnOffset;
    }

    public abstract Direction opposite();

  }

}
