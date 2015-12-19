import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Darrien on 12/17/15.
 */

public abstract class Champion {

    public Champion(String file){
        dataCrawler(file);
        percentDmg = 0;
        isKO = false;
    }

    /******************************
     * START DATA MEMBERS
     ******************************/

    // Name of the player if given
    protected String charName;

    // Possible array of strings containing all file names needed to access?
    private String[] fileNames;

    // Where we store the power statistics for each character, for atkDir, 0 means horizontal, 1 means vertical
    /*
        +-----------+----------+-----------+
        | atkDmg    | atkKB    | atkDir    |
        +-----------+----------+-----------+
        | grabDmg   | grabKB   | grabDir   |
        +-----------+----------+-----------+
        | shieldDmg | shieldKB | shieldDir |
        +-----------+----------+-----------+
     */

    protected double[][] stats;

    // How heavy the player is, affects vertical survivability, and horizontal recovery
    private double weight;

    // Amount of damage character has taken thus far
    private double percentDmg;

    // Whether or not the character has been KO'd
    private boolean isKO;

    // Sets status of attack using pseudo-enumeration
    // Attack, Grab, or Shield respectively as 0, 1, or 2
    private int actionFlag;

    /******************************
     * END DATA MEMBERS
     *****************************/

    // Deals damage to opponent based on the move used, and returns knockback
    public double attack(Champion rhs){
        rhs.takeDamage(stats[actionFlag][0]);
        return stats[actionFlag][1];
    }

    public abstract double specialAttack(Champion rhs);

    public void takeDamage(double amtDmg){
        percentDmg += amtDmg;
    }
    // Switches KO status from true to false, and false to true
    public void toggleKO(){
        isKO = !isKO;
    }

    // Sets the action chosen by user
    // Assuming safeguards are not implemented in main, will pick a random num between 0 and 2 if action is out of bounds
    public void setActionFlag(int action){
        actionFlag = !(action > 2) || !(action < 0) ? action : (int)(Math.random() % 2);
    }

    /*****************************
     * START GETTERS
     ****************************/

    public String[] getFileNames() {
        return fileNames;
    }

    public double getAtkDmg() {
        return stats[0][0];
    }

    public double getAtkKB() {
        return stats[0][1];
    }

    public double getAtkDir() {
        return stats[0][2];
    }

    public double getGrabDmg() {
        return stats[1][0];
    }

    public double getGrabKB() {
        return stats[1][1];
    }

    public double getGrabDir() {
        return stats[1][2];
    }

    public double getShieldDmg() {
        return stats[2][0];
    }

    public double getShieldKB() {
        return stats[2][1];
    }

    public double getShieldDir() {
        return stats[2][2];
    }

    public double getWeight() {
        return weight;
    }

    public String getCharName() {
        return charName;
    }

    public double getPercentDmg() {
        return percentDmg;
    }

    public boolean isKO() {
        return isKO;
    }

    public int getActionFlag() {
        return actionFlag;
    }


    /*****************************
     * END GETTERS
     ****************************/

    // Reads in data for a character based off of given file name
    public void dataCrawler(String file) {
        stats = new double[3][3];

        String DELIMITER = ", ";
        int numArgs = 3;

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] tokens;

            // Set file information immediately to raw data in file
            fileNames = br.readLine().split(DELIMITER);

            if (fileNames.length != 6){
                // Make real exceptions when class is finished
                // throw invalidData;
                System.out.println("Invalid arguments. Process failed.");
                System.exit(1);
            }

            // Get attack, grab, and shield stats, and set them
            for (int i = 0; i < numArgs; ++i){
                tokens = br.readLine().split(DELIMITER);

                if (tokens.length < numArgs){
                    // Make real exceptions when class is finished
                    // throw invalidData;
                    System.out.println("Invalid arguments. Process failed.");
                    System.exit(1);
                }
                System.out.println("i: " + i);
                System.out.println("tokens[0]: " + tokens[0]);
                System.out.println("tokens[0]: " + tokens[1]);
                System.out.println("tokens[0]: " + tokens[2]);
                stats[i][0] = Double.parseDouble(tokens[0]);
                stats[i][1] = Double.parseDouble(tokens[1]);
                stats[i][2] = Integer.parseInt(tokens[2]);
            }

            weight = Double.parseDouble(br.readLine());
        }catch (java.io.IOException e){
            System.out.println("Invalid or corrupted file data. Failing");
            System.exit(0);
        }
    }
}