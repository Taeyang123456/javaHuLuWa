package Field;

import Being.Creature;
import Formation.Location;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class BattleField {
    static final int width = 16;
    static final int height = 14;
    boolean isBattle;
    boolean startBattle;
    boolean replay;
    private static Creature[][] battlefield = new Creature[width][height];
    public BattleField() {
        isBattle = false;
        startBattle = false;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                battlefield[i][j] = null;
            }
    }
    public boolean isBattleEnd() {
        String str = null;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height;j++) {
                if(battlefield[i][j] != null && battlefield[i][j].isLive()) {
                    if(str == null) {
                        str = battlefield[i][j].getType();
                    }
                    else {
                        if(!(battlefield[i][j].getType().equals(str))) {
                            isBattle = true;
                            return false;
                        }
                    }

                }
            }
        }
        isBattle = false;
        startBattle = false;
        return true;
    }
    public String getVictory() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(battlefield[i][j] != null &&  battlefield[i][j].isLive())
                    return battlefield[i][j].getType();
            }
        }
        return null;
    }
    public void clearCreature() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(battlefield[i][j] != null)
                    battlefield[i][j].setAlive(false);
            }
        }
        //System.out.println("clear!");
    }

    public Creature[][] getBattlefield() {
        return battlefield;
    }
    public void setPosition(Location prev, Location cur, Creature c) {
        if(prev.x == cur.x && prev.y == cur.y)
            return;
        if(battlefield[cur.x][cur.y] == null) {
            battlefield[cur.x][cur.y] = c;
            battlefield[prev.x][prev.y] = null;

        }
    }
    public boolean isOutOfBound(Location l) {
        if(l.x >= width || l.x < 0 || l.y >= height || l.y < 0)
            return true;
        return false;
    }
    public Creature getCreature(Location l) {
        if(isOutOfBound(l)) {
            return null;
        }
        if(battlefield[l.x][l.y] == null)
            return null;
        return battlefield[l.x][l.y];
    }

    public boolean fieldIsEmpty(Location l) {
        if(isOutOfBound(l)) {
            return false;
        }
        if(battlefield[l.x][l.y] == null)
            return true;
        else
            return false;
    }

    public  <T extends Creature> void putIntoBattleField(List<T> list) {
        isBattle = true;
        startBattle = false;
        for(int i = 0; i < list.size(); i++){
            Creature temp = list.get(i);
            battlefield[temp.getX()][temp.getY()] = temp;
        }

    }

    public <T extends Creature> void putIntoBattleField(T c) {
        isBattle = true;
        startBattle = false;
        battlefield[c.getX()][c.getY()] = c;
    }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setStartBattle(boolean start) { startBattle = start;}
    public boolean getStartBattle() { return startBattle; }
    public boolean getBattleFlag() { return isBattle; }

}
