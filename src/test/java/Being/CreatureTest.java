package Being;

import Field.BattleField;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreatureTest {

    @Test
    public void randMove() {
        BattleField battleField = new BattleField();
        Creature creature = new Creature(battleField.getWidth() / 2,battleField.getHeight() / 2, battleField);
        for(int i = 0; i < 99999;i++) {
            try {
                creature.randMove();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}