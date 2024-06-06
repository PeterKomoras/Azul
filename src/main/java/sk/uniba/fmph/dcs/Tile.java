package sk.uniba.fmph.dcs;

enum Tile {
  STARTING_PLAYER,
  RED,
  GREEN,
  YELLOW,
  BLUE,
  BLACK;

  @Override
  public String toString() {
    switch (this) {
      case STARTING_PLAYER:
        return "S";
      case RED:
        return "R";
      case GREEN:
        return "G";
      case YELLOW:
        return "I";
      case BLUE:
        return "B";
      case BLACK:
        return "L";
      default:
        return null;
    }
  }
}
