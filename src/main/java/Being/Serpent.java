package Being;

import Field.BattleField;

import static java.lang.Thread.sleep;

public class Serpent extends  Creature implements Runnable{



    public Serpent(BattleField b) {

        super(b);
        type = "BAD";
        attack = 500;
        blood = 1000;
        total_blood = 1000;
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
