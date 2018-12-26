package Formation;

import Being.Creature;
import java.util.List;

public class Formation {

    public static final String empty = "\uD83C\uDE33";
    protected String[][] content;
    protected int width;
    protected int height;
    protected Location location;

    Formation(int x, int y){
        this.width = x;
        this.height = y;
        content = new String[x][y];
        for(int i = 0;i < width;i++)
            for(int j = 0; j < height;j++) {
                content[i][j] = empty;
            }
    }
    Formation(int x, int y, Location loc){
        this.width = x;
        this.height = y;
        content = new String[x][y];
        for(int i = 0;i < width;i++) {
            for (int j = 0; j < height; j++) {
                content[i][j] = empty;
            }
        }
        this.location = loc;
    }


    public static <T extends Creature>void setYanxing(List<T> list, Location l) {
        for(int i = 0; i < list.size(); i++) {
            list.get(i).setLocation(new Location(l.x + i, l.y + i));
        }
    }
    public static <T extends Creature>void setFengShi(List<T> list, Location l) {
        if(list.size() < 5 )
            return;
        int i;
        for(i = 0; i < 3;i++) {
            list.get(i).setLocation(new Location(l.x + i, l.y - i));
        }
        for(; i < 5; i++) {
            list.get(i).setLocation(new Location( l.x + i - 2, l.y + i - 2));
        }
        for(; i < list.size(); i++) {
            list.get(i).setLocation(new Location(l.x + i - 3, l.y));
        }
    }
    public static <T extends Creature>void setFangxing(List<T> list, Location l) {
        if(list.size() != 8)
            return;
        list.get(0).setLocation(new Location( l.x, l.y));
        list.get(1).setLocation(new Location(l.x + 1, l.y - 1));
        list.get(2).setLocation(new Location(l.x + 2, l.y - 2));
        list.get(3).setLocation(new Location(l.x + 3, l.y - 1));
        list.get(4).setLocation(new Location(l.x + 4, l.y));
        list.get(5).setLocation(new Location(l.x + 3, l.y + 1));
        list.get(6).setLocation(new Location(l.x + 2, l.y + 2));
        list.get(7).setLocation(new Location(l.x + 1, l.y + 1));
    }

}