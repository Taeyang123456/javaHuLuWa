package Being;

import Field.BattleField;
import Formation.Location;

import java.util.*;

public class HuLuWas {
    public List<Brother> brothers = new ArrayList<>();
    //public Brother[] brothers = new Brother[7];

    public HuLuWas(BattleField b) {
        for(int i = 0; i < 7;i++) {
            Brother temp = new Brother(b);
            temp.setAlive(true);
            brothers.add(temp);
        }
        initialBrothers();
    }
    private void initialBrothers() {

        Iterator<Brother> it = brothers.iterator();
        int i = 1;
        while(it.hasNext()) {
            Brother brother = it.next();
            brother.setRank(i);
            brother.setImage("hulu" + i + ".jpg");
            brother.setAttack(200);
            brother.setTotal_blood(900);
            brother.setBlood(900);
            brother.setLocation(new Location(3, 3 + i));
            i++;
        }
    }

    public void reBorn() {
        Iterator<Brother> it = brothers.iterator();
        while(it.hasNext()) {
            Brother creature = it.next();
            creature.setAlive(true);
            creature.setSleepTime(800);
        }
        initialBrothers();
    }
}
