package Replay;

import Formation.Location;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class replayReader {
    public static Vector<Vector<battleRecord>> list = new Vector<>();
    public static String Winner;

    public replayReader(File file) {
        list.clear();
        Winner = null;
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String str;
            str = bufferedReader.readLine();
            while(str != null && !str.contains("END")) {
                Vector<battleRecord> battleRecordVector = new Vector<>();
                for(int i = 0; i < 17; i++) {
                    Pattern pattern = Pattern.compile("[^0-9]");
                    Matcher matcher = pattern.matcher(str);
                    String[] result = matcher.replaceAll(" ").split("\\s+");
                    battleRecord battlerecord = new battleRecord();
                    battlerecord.setRank(Integer.parseInt(result[0]));
                    if (result[1].equals("1"))
                        battlerecord.setLive(true);
                    else
                        battlerecord.setLive(false);
                    battlerecord.setLocation(new Location(Integer.parseInt(result[2]), Integer.parseInt(result[3])));
                    battlerecord.setBlood(Integer.parseInt(result[4]));
                    battlerecord.setTotalBlood(Integer.parseInt(result[5]));
                    battleRecordVector.add(battlerecord);
                    str = bufferedReader.readLine();
                }
                list.add(battleRecordVector);
            }
            if(str.contains("GOOD")) {
                Winner = new String("GOOD");
            } else {
                Winner = new String("BAD");
            }

        }catch (Exception e) {
            System.out.println("read file error");
        }
    }


}
