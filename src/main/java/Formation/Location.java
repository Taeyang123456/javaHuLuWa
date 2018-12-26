package  Formation;

import java.util.HashSet;
import java.util.Random;

public class Location {
    public int x;
    public int y;
    public Location() {
        this.x = x;
        this.y = y;
    }
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Location(Location l) {
        this.x = l.x;
        this.y = l.y;
    }
    public void randomHashSet() {
        HashSet<Integer> hashSet = new HashSet<>();
        Random random = new Random();
        int num;
        for(int i = 0; i < 1000;i++) {
            num = random.nextInt(7);
            hashSet.add(num);
            if(hashSet.size() == 7)
                break;
        }

    }
}
