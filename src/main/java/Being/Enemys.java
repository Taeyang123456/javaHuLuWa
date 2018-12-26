package Being;

import Field.BattleField;
import Formation.Formation;
import Formation.Location;

import java.util.ArrayList;
import java.util.List;

public class Enemys {
    //public Followers [] followers = new Followers[8];
    public List<Followers> followers = new ArrayList<>();
    public Scorpion scorpion ;
    public Serpent serpent ;
    public Enemys(BattleField b, int formationNum) {
        /*for(int i = 0; i < followers.length; i++) {
            followers[i] = new Followers(b);
            followers[i].setImage("monster.jpg");
        }*/
        for(int i = 0; i < 8;i++) {
            Followers fol = new Followers(b);
            fol.setImage("monster.jpg");
            fol.setRank(0);
            followers.add(fol);
        }
        switch (formationNum) {
            case 0: Formation.setYanxing(followers, new Location(8,3)); break;
            case 1: Formation.setFengShi(followers, new Location(9,7)); break;
            case 2: Formation.setFangxing(followers, new Location(9,7)); break;
        }
        scorpion = new Scorpion(b);
        scorpion.setLocation(new Location(8,7));
        scorpion.setImage("scorpion.jpg");
        scorpion.setRank(8);
        serpent = new Serpent(b);
        serpent.setLocation(new Location(15,7));
        serpent.setImage("serpent.jpg");
        serpent.setRank(9);
    }

    public void setFormation(int formationNum) {
        switch (formationNum) {
            case 0: Formation.setYanxing(followers, new Location(8,3)); break;
            case 1: Formation.setFengShi(followers, new Location(9,7)); break;
            case 2: Formation.setFangxing(followers, new Location(9,7)); break;
        }
    }

    public void reBorn(int formationNum) {
        for(int i = 0; i < followers.size(); i++) {
            followers.get(i).setAlive(true);
            followers.get(i).setBlood(followers.get(i).getTotal_blood());
            followers.get(i).setImage("monster.jpg");
        }
        serpent.setAlive(true);
        serpent.setBlood(serpent.getTotal_blood());
        serpent.setLocation(new Location(15,7));
        serpent.setImage("serpent.jpg");
        scorpion.setAlive(true);
        scorpion.setBlood(scorpion.getTotal_blood());
        scorpion.setLocation(new Location(8, 7));
        scorpion.setImage("scorpion.jpg");

        switch (formationNum) {
            case 0: Formation.setYanxing(followers, new Location(8,3)); break;
            case 1: Formation.setFengShi(followers, new Location(9,7)); break;
            case 2: Formation.setFangxing(followers, new Location(9,7)); break;
        }
    }
}
