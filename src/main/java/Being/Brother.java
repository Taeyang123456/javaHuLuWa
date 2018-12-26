package Being;

import Field.BattleField;
import Formation.Location;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Brother extends Creature implements Runnable{

    int sleepTime = 800;

    public Brother() {
        super();
        type = "GOOD";
    }

    public Brother(BattleField battleField) {
        super(battleField);
        type = "GOOD";
        alive = true;
    }

    public void set_position(int i, int j) {
        location.x = i;
        location.y = j;
    }

    public boolean moveTowardEnemyYLow( int x, int y, String str) {
        int lowCount = 0;
        int highCount = 0;
        for(int i = y;i < battleField.getHeight();i++) {
            for(int j = 0; j < battleField.getWidth();j++) {
                Creature c = battleField.getCreature(new Location(j, i));
                if(c != null && c.isLive()) {
                    if(c.getType() != str) {
                        highCount++;
                    }
                }
            }
        }
        for(int i = y; i >= 0;i--) {
            for(int j = 0; j < battleField.getWidth();j++) {
                Creature c = battleField.getCreature(new Location(j, i));
                if(c != null && c.isLive()) {
                    if(c.getType() != str){
                        lowCount++;
                    }
                }
            }
        }
        return lowCount > highCount;
    }
    public boolean moveTowardEnemyXLeft(int x, int y, String str) {
        int leftCount = 0;
        int rightCount = 0;
        for(int i = x; i < battleField.getWidth();i++) {
            for(int j = 0; j < battleField.getHeight(); j++) {
                Creature c = battleField.getCreature(new Location(i, j));
                if(c != null && c.isLive()) {
                    if (c.getType() != str) {
                        rightCount++;
                    }
                }
            }
        }
        for(int i = x; i >= 0;i--) {
            for(int j = 0; j < battleField.getHeight();j++) {
                Creature c = battleField.getCreature(new Location(i, j));
                if(c != null && c.isLive()) {
                    if(c.getType() != str) {
                        leftCount++;
                    }

                }
            }
        }
        return leftCount > rightCount;
    }

    public void noRandMove() {
        int x = location.x;
        int y = location.y;
        boolean isLeft = moveTowardEnemyXLeft(x, y, getType());
        boolean isLow = moveTowardEnemyYLow(x, y, getType());
        if(isLeft && isLow) {
            if (battleField.fieldIsEmpty(new Location(x - 1, y))) {
                setX(x - 1);
            } else if (battleField.fieldIsEmpty(new Location(x, y - 1))) {
                setY(y - 1);
            } else if (battleField.fieldIsEmpty(new Location(x + 1, y))) {
                setX(x + 1);
            } else if (battleField.fieldIsEmpty(new Location(x, y + 1))) {
                setY(y + 1);
            }
            battleField.setPosition(new Location(x, y), new Location(getX(), getY()), this);
        } else if(isLeft) {
            if(battleField.fieldIsEmpty(new Location(x - 1, y))) {
                setX(x - 1);
            } else if(battleField.fieldIsEmpty(new Location(x, y + 1))) {
                setY(y + 1);
            } else if(battleField.fieldIsEmpty(new Location(x, y - 1))) {
                setY(y - 1);
            } else if(battleField.fieldIsEmpty(new Location(x + 1, y))) {
                setX(x + 1);
            }
            battleField.setPosition(new Location(x, y), new Location(getX(), getY()), this);
        } else if(isLow) {
            if(battleField.fieldIsEmpty(new Location(x,y - 1))) {
                setY(y - 1);
            } else if(battleField.fieldIsEmpty(new Location(x + 1, y))) {
                setX(x + 1);
            } else if(battleField.fieldIsEmpty(new Location(x - 1, y))) {
                setX(x - 1);
            } else if(battleField.fieldIsEmpty(new Location(x, y + 1))) {
                setY(y + 1);
            }
        } else {
            if(battleField.fieldIsEmpty(new Location(x, y + 1))) {
                setY(y + 1);
            } else if(battleField.fieldIsEmpty(new Location(x + 1, y))) {
                setX(x + 1);
            } else if(battleField.fieldIsEmpty(new Location(x, y - 1))) {
                setY(y - 1);
            } else if(battleField.fieldIsEmpty(new Location(x - 1, y))) {
                setX(x - 1);
            }
        }
        battleField.setPosition(new Location(x, y), new Location(getX(), getY()), this);

    }
    public void openGuiSword() {
        sleepTime /= 2;
        attack += 100;
    }

    public void fieldMove() {
        Random random = new Random();
        int i = random.nextInt(2);
        if(i == 0)
            noRandMove();
        else
            randMove();
    }

    @Override
    public void run() {
        while(true) {
            synchronized (battleField) {
                if (!alive) {
                    return;
                }
                fieldMove();
                tryAttack();
            }
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setSleepTime(int num) { sleepTime = num;}
}
