package UI;

import Being.Brother;
import Being.Creature;
import Field.BattleField;
import Formation.Location;
import Replay.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class Renovate implements Runnable {

    Canvas canvas;
    BattleField battleField;
    final int canvas_width = 50;
    final int canvas_heigh = 50;
    final int line_width = 8;
    boolean recorderOpen;
    String winner = null;
    FileWriter out;
    List<Image> imageList = new ArrayList<>();
    Vector<Vector<battleRecord>> list;
    Iterator<Vector<battleRecord>> vecIt;
    static boolean replay;

    private String outputLine(Creature c_temp) {
        int creatureNum = 0;
        if (c_temp.getClass().getSimpleName().equals("Brother")) {
            creatureNum = c_temp.getRank();
        } else if (c_temp.getClass().getSimpleName().equals("Followers")) {
            creatureNum = 0;
        } else if (c_temp.getClass().getSimpleName().equals("Scorpion")) {
            creatureNum = 8;
        } else if (c_temp.getClass().getSimpleName().equals("Serpent")) {
            creatureNum = 9;
        }
        String str = new String();
        str += creatureNum;
        str += " ";
        if(c_temp.isLive())
            str += 1;
        else
            str += 0;
        str += " ";
        str += c_temp.getLocation().x;
        str += " ";
        str += c_temp.getLocation().y;
        str += " ";
        str += c_temp.getBlood();
        str += " ";
        str += c_temp.getTotal_blood();
        str += "\r\n";
        return str;
    }


    public Renovate(Canvas canvas, BattleField battleField) {
        this.canvas = canvas;
        this.battleField = battleField;
        replay = false;
        recorderOpen = false;
        vecIt = null;
        Image img;
        InputStream is = getClass().getClassLoader().getResourceAsStream("monster.jpg");
        img = new Image(is);
        imageList.add(img);
        for(int i = 0; i < 7; i++) {
            int num = i + 1;
            is = getClass().getClassLoader().getResourceAsStream("hulu" + num + ".jpg");
            img = new Image(is);
            imageList.add(img);
        }
        is = getClass().getClassLoader().getResourceAsStream("scorpion.jpg");
        img = new Image(is);
        imageList.add(img);
        is = getClass().getClassLoader().getResourceAsStream("serpent.jpg");
        img = new Image(is);
        imageList.add(img);
        is = getClass().getClassLoader().getResourceAsStream("tombstone.jpg");
        img = new Image(is);
        imageList.add(img);
    }

    //public void setReplay(boolean replay) { this.replay =  replay; }

    private void printResult() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        String str = battleField.getVictory();
        if (winner == null)
            winner = new String(str);
        battleField.clearCreature();
        try{
            out.write("END " + winner);
            out.flush();
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        recorderOpen = false;
        Image image;
        if (str.equals("GOOD")) {
            InputStream is = getClass().getClassLoader().getResourceAsStream("victory.jpg");
            image = new Image(is);

        } else {
            InputStream is = getClass().getClassLoader().getResourceAsStream("defeat.jpg");
            image = new Image(is);
        }
        context.drawImage(image, 0, 0);
    }

    private void printBattleF() {
        if(!recorderOpen) {
            try {
                String filePath = System.getProperty("user.dir");
                //System.out.println(filePath);
                out = new FileWriter(filePath + File.separator + "tempRecord.txt");
            }catch (Exception e) {
                e.printStackTrace();
            }
            recorderOpen = true;
        }
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < battleField.getWidth(); i++) {
            for (int j = 0; j < battleField.getHeight(); j++) {
                if (battleField.getCreature(new Location(i, j)) != null) {
                    Creature c_temp = battleField.getBattlefield()[i][j];
                    Image img = c_temp.getImage();
                    context.drawImage(img, i * canvas_width, j * canvas_heigh);
                    double percent = (double) c_temp.getBlood() / (double) c_temp.getTotal_blood();
                    double greenPart = canvas_width * percent;
                    context.setFill(Color.rgb(0, 255, 0));
                    context.fillRect(i * canvas_width, j * canvas_heigh, greenPart, line_width);
                    if(battleField.getStartBattle()) {
                        String str = new String(outputLine(c_temp));
                        try {
                            out.write(str);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void replayDrawing() {
        if(!vecIt.hasNext()) {
            Image image;
            if(replayReader.Winner == null) {
                InputStream is = getClass().getClassLoader().getResourceAsStream("crash.jpg");
                image = new Image(is);
                winner = new String("BAD");
            } else {
                if (replayReader.Winner.equals("GOOD")) {
                    InputStream is = getClass().getClassLoader().getResourceAsStream("victory.jpg");
                    image = new Image(is);
                } else {
                    InputStream is = getClass().getClassLoader().getResourceAsStream("defeat.jpg");
                    image = new Image(is);
                }
                winner = new String(replayReader.Winner);
            }
            battleField.clearCreature();

            GraphicsContext context = canvas.getGraphicsContext2D();
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            context.drawImage(image, 0, 0);
            replay = false;
            // setVictory!!!
        }
        else {

            Vector<battleRecord> tempRecord = vecIt.next();
            GraphicsContext context = canvas.getGraphicsContext2D();
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            Iterator<battleRecord> iterator = tempRecord.iterator();
            while(iterator.hasNext()) {
                //System.out.println("here");
                battleRecord temp = iterator.next();
                int tempWidth = temp.getX() * canvas_width;
                int tempHeigh = temp.getY() * canvas_heigh;
                if(temp.getIsLive())
                    context.drawImage(imageList.get(temp.getRank()), tempWidth, tempHeigh);
                else
                    context.drawImage(imageList.get(10), tempWidth, tempHeigh);
                double percent = (double) temp.getBlood() / (double) temp.getTotalBlood();
                double greenPart = canvas_width * percent;
                context.setFill(Color.rgb(0,255, 0));
                context.fillRect(tempWidth, tempHeigh, greenPart, line_width);
            }
        }
    }



    public void run() {
        while(true) {
            synchronized (canvas) {
                if(replay) {
                    //System.out.println("here");
                    list = replayReader.list;

                    if(vecIt == null) {
                        vecIt = list.iterator();
                    }
                    replayDrawing();
                }
                else {
                    synchronized (battleField) {
                        battleField.isBattleEnd();
                        if (!battleField.getBattleFlag()) {
                            if (winner == null) {
                                printResult();
                            }
                        } else {
                            winner = null;
                            printBattleF();
                        }
                    }
                }
            }
            try {
                sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
