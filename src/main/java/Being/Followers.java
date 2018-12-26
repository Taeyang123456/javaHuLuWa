package Being;

import Field.BattleField;

import static java.lang.Thread.sleep;

public class Followers extends Creature implements Runnable{

    public Followers(BattleField b){
        super(b);
        type = "BAD";
        attack = 200;
        blood = 800;
        total_blood = 800;
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
