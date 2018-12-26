package Replay;

import Field.BattleField;
import Formation.Location;

public class battleRecord {
    int rank;
    boolean isLive;
    int blood;
    int totalBlood;
    Location location;

    public battleRecord() {}

    public battleRecord(int rank, boolean isLive, int blood, int totalBlood, Location l) {
        this.rank =rank;
        this.isLive = isLive;
        this.blood = blood;
        this.totalBlood = totalBlood;
        this.location = new Location(l);
    }

    public void setRank(int rank) { this.rank = rank; }
    public void setLive(boolean isLive) { this.isLive = isLive; }
    public void setBlood(int blood) { this.blood = blood; }
    public void setTotalBlood(int totalBlood) { this.totalBlood = totalBlood; }
    public void setLocation(Location location) { this.location = location; }

    public int getRank() { return rank; }
    public int getBlood() { return blood;}
    public int getTotalBlood() { return totalBlood; }
    public boolean getIsLive() { return isLive; }
    public Location getLocation() { return location;}
    public int getX() { return location.x; }
    public int getY() { return location.y; }

}
