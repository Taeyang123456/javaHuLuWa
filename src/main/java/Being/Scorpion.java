package Being;

import Field.BattleField;

import static java.lang.Thread.sleep;

public class Scorpion extends Creature implements Runnable{


    public Scorpion(BattleField b){
        super(b);
        type = "BAD";
        attack = 250;
        blood = 2000;
        total_blood = 2000;
        alive = true;
    }

    public void run() {
        while(true) {
            synchronized (battleField) {
                if (!alive) {
                    return;
                }
                randMove();
                tryAttack();
            }
            try {
                sleep(800);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
