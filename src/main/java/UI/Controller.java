package UI;

import Being.*;
import Field.BattleField;

import Replay.*;
import Formation.Location;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Iterator;


public class Controller {

    @FXML
    public Canvas canvas;
    @FXML
    public AnchorPane Pane;

    BattleField battleField = new BattleField();
    HuLuWas huLuWas ;
    Enemys enemys ;
    Stage stage;
    boolean isBattle;
    boolean guiSword;
    int formationNum = 0;
    boolean startBattle;

    public void putCreatureToField() {
        synchronized (battleField) {
            for (int i = 0; i < battleField.getWidth(); i++) {
                for (int j = 0; j < battleField.getHeight(); j++)
                    battleField.getBattlefield()[i][j] = null;
            }
            battleField.putIntoBattleField(huLuWas.brothers);
            battleField.putIntoBattleField(enemys.followers);
            battleField.putIntoBattleField(enemys.scorpion);
            battleField.putIntoBattleField(enemys.serpent);
        }

    }

    private void formationChoosingPrepare() {
        reBorn();
        enemys.setFormation(formationNum);
        putCreatureToField();
    }
    public void setStage(Stage stage) { this.stage = stage; }

    private void reBorn() {
        huLuWas.reBorn();
        enemys.reBorn(formationNum);
        guiSword = false;
    }

    public void  initialize() {
        huLuWas = new HuLuWas(battleField);
        enemys = new Enemys(battleField, formationNum);
        isBattle = false;
        startBattle = false;
        guiSword = false;
        putCreatureToField();
        new Thread(new Renovate(canvas, battleField)).start();
    }

    public class KeyEventHandle implements  EventHandler<KeyEvent> {

        public KeyEventHandle() {
            super();
        }

        @Override
        public void handle(KeyEvent event) {
            synchronized (battleField) {
                startBattle = battleField.getStartBattle();
            }
            //System.out.println(startBattle);
            if(!startBattle && event.getCode() == KeyCode.SPACE) {
                reBorn();
                putCreatureToField();
                battleField.setStartBattle(true);
                startBattle = true;
                for( int i = 0; i < huLuWas.brothers.size(); i++)
                    new Thread(huLuWas.brothers.get(i)).start();
                for(int i = 0; i < enemys.followers.size(); i++)
                    new Thread(enemys.followers.get(i)).start();
                new Thread(enemys.scorpion).start();
                new Thread(enemys.serpent).start();

            } else if(!startBattle && event.getCode() == KeyCode.Q) {
                formationNum = 0;
                formationChoosingPrepare();

            } else if(!startBattle && event.getCode() == KeyCode.W) {
                formationNum = 1;
                formationChoosingPrepare();

            } else if(!startBattle && event.getCode() == KeyCode.E) {
                formationNum = 2;
                formationChoosingPrepare();

            } else if(startBattle && event.getCode() == KeyCode.R) {
                if(!guiSword) {
                    guiSword = true;
                    Iterator<Brother> it = huLuWas.brothers.iterator();
                    int num = 1;
                    while(it.hasNext()) {
                        Brother brother0 = it.next();
                        brother0.openGuiSword();
                        if(brother0.isLive()) {
                            brother0.setImage("hulu" + num + "G.jpg");
                        }
                        num++;
                    }
                }
            } else if(!startBattle && event.getCode() == KeyCode.L) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);
                if(file != null) {
                    new replayReader(file);
                    Renovate.replay = true;
                }
            }
        }
    }

}
