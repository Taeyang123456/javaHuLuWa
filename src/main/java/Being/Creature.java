package Being;


import Field.BattleField;
import Formation.Location;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.Random;




public class Creature {
    protected int blood;
    protected int total_blood;
    protected int attack;
    protected Location location;
    protected Image image;
    protected String url;
    protected String type;
    protected BattleField battleField;
    protected boolean alive;
    protected int rank;

    public Creature() {
        location  = new Location();
        location.x = 0;
        location.y = 0;
        blood = 0;
        total_blood = 0;
        attack = 0;
        type = "";
    }

    public Creature(BattleField b) {
        location  = new Location();
        location.x = 0;
        location.y = 0;
        blood = 0;
        total_blood = 0;
        attack = 0;
        type = "";
        battleField = b;
    }

    public Creature(int x, int y, BattleField b) {
        location  = new Location();
        location.x = x;
        location.y = x;
        blood = 0;
        total_blood = 0;
        attack = 0;
        type = "";
        battleField = b;
    }
    public Location getLocation() { return location;}
    public int getRank() { return rank;}
    public void setRank(int num) { this.rank = num; }
    public void setImage(String url) {
        this.url = url;
        InputStream is = getClass().getClassLoader().getResourceAsStream(url);
        image = new Image(is);
    }

    public void randMove() throws ArrayIndexOutOfBoundsException{
        int x = location.x;
        int y = location.y;
        int valuableDirection = 0;

        boolean[] direction = new boolean[4];
        for(int i = 0; i < direction.length; i++)
            direction[i] = false;
        if(battleField.fieldIsEmpty(new Location(x - 1, y))) {
            direction[0] = true;
            valuableDirection++;
        }
        if(battleField.fieldIsEmpty(new Location(x + 1, y))) {
            direction[1] = true;
            valuableDirection++;
        }
        if(battleField.fieldIsEmpty(new Location( x, y - 1))) {
            direction[2] = true;
            valuableDirection++;
        }
        if(battleField.fieldIsEmpty(new Location( x, y + 1))) {
            direction[3] = true;
            valuableDirection++;
        }
        if(valuableDirection == 0)
            return;
        if(valuableDirection == 1) {
            int i = 0;
            for(i = 0; i < direction.length; i++) {
                if(direction[i])
                    break;
            }
            switch(i) {
                case 0:setX(x - 1); break;
                case 1:setX(x + 1); break;
                case 2:setY(y - 1); break;
                case 3:setY(y + 1); break;
            }
            if(battleField.isOutOfBound(new Location(getX(), getY())))
                throw new ArrayIndexOutOfBoundsException("battlefield out of bound");
            battleField.setPosition(new Location(x, y), new Location(getX(), getY()), this);
            return;
        }
        Random rand = new Random();
        int i = rand.nextInt(valuableDirection);

        int count = 0;
        for(count = 0; count < direction.length; count++) {
            if(direction[count])
                i--;
            if(i < 0)
                break;
        }

        switch(count) {
            case 0:setX(x - 1); break;
            case 1:setX(x + 1); break;
            case 2:setY(y - 1); break;
            case 3:setY(y + 1); break;
        }
        if(battleField.isOutOfBound(new Location(getX(), getY())))
            throw new ArrayIndexOutOfBoundsException("battlefield out of bound");
        battleField.setPosition(new Location(x, y), new Location(getX(), getY()), this);
    }

    public void setAlive(boolean flag) { alive = flag; }
    public void setX(int x) { location.x = x; }
    public void setY(int y) { location.y = y; }
    public void setLocation(Location l) { location.x = l.x; location.y = l.y; }
    public void setBlood(int b) { blood = b;}
    public void setTotal_blood(int b) { total_blood = b; }
    public void setAttack(int a) { attack = a;};



    public int getX() { return location.x; }
    public int getY() { return location.y; }
    public boolean isLive() { return alive; }
    public Image getImage() {
        return image;
    }
    public String getUrl() {return url;}
    public String getType() { return type; }
    public int getBlood() { return blood; }
    public int getTotal_blood() { return total_blood; }

    public void tryAttack() {
        int x = location.x;
        int y = location.y;
        Creature leftC = battleField.getCreature(new Location(x - 1, y));
        if(leftC != null) {
            if(leftC.isLive() && !leftC.getType().equals(this.type)) {
                leftC.sufferAttack(this.attack);
            }
        }
        Creature rightC = battleField.getCreature(new Location(x + 1, y));
        if(rightC != null) {
            if(rightC.isLive() && !rightC.getType().equals(this.type)) {
                rightC.sufferAttack(this.attack);
            }
        }
        Creature lowC = battleField.getCreature(new Location(x, y - 1));
        if(lowC != null) {
            if(lowC.isLive() && !lowC.getType().equals(this.type)) {
                lowC.sufferAttack(this.attack);
            }
        }
        Creature highC = battleField.getCreature(new Location(x, y + 1));
        if(highC != null) {
            if(highC.isLive() && !highC.getType().equals(this.type)) {
                highC.sufferAttack(this.attack);
            }
        }
    }

    /*public void run() {
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

    }*/

    public boolean sufferAttack(int hurt) {
        blood -= hurt;
        if(blood <= 0) {
            blood = 0;
            this.url = "tombstone.jpg";
            InputStream is = getClass().getClassLoader().getResourceAsStream(this.url);
            image = new Image(is);
            alive = false;
            return true;
        }
        return false;
    }
}
