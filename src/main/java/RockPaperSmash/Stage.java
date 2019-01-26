package RockPaperSmash;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Stage {

    private String fileLoc;
    /**
     * Length and width of the stage.
     *  +---------------+-------------+
     *  | HoriztonalLen | VerticalLen |
     *  +---------------+-------------+
     */
    private double[] stageStats;

    /**
     * Basic constructor. Takes string file containing stage's stats.
     *
     * @param statsFile String containing stage's tats,
     */
    public Stage(String statsFile){
        stageStats = new double[2];
        try{
            BufferedReader br = FileReader.loadFile(new FileResource(statsFile, true));
            fileLoc = br.readLine();
            stageStats[0] = Double.parseDouble(br.readLine());
            stageStats[1] = Double.parseDouble(br.readLine());

        }catch(java.io.IOException e){
            // Restart program when failure - add later
            System.out.println("Invalid parameters. Process failed.");
            System.exit(1);
        }
    }

    public double getHorizontalLen() {
        return stageStats[0];
    }

    public double getVerticalLen() {
        return stageStats[1];
    }

    public abstract String getStageName();

    public String getFileLoc() {
        return fileLoc;
    }
}
