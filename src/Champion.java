import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Darrien on 12/17/15.
 */

// TODO: MAKE THIS ABSTRACT
// SERIOUSLY IT IS ONLY NOT ABSTRACT FOR TESTING
public class Champion {

    public Champion(){
        dataCrawler("lol.txt");
    }

    /******************************
     * START DATA MEMBERS
     ******************************/

    // Name of the player if given
    private String charName;

    // Possible array of strings containing all file names needed to access?
    private String[] fileNames;

    // Where we store the power statistics for each character
    private double atkDmg;
    private double atkKB;
    private double atkDir;

    private double grabDmg;
    private double grabKB;
    private double grabDir;

    private double shieldDmg;
    private double shieldKB;
    private double shieldDir;

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
        return atkDmg;
    }

    public double getAtkKB() {
        return atkKB;
    }

    public double getAtkDir() {
        return atkDir;
    }

    public double getGrabDmg() {
        return grabDmg;
    }

    public double getGrabKB() {
        return grabKB;
    }

    public double getGrabDir() {
        return grabDir;
    }

    public double getShieldDmg() {
        return shieldDmg;
    }

    public double getShieldKB() {
        return shieldKB;
    }

    public double getShieldDir() {
        return shieldDir;
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
        tokens = br.readLine().split(DELIMITER);

        if (tokens.length < numArgs){
            // Make real exceptions when class is finished
            // throw invalidData;
            System.out.println("Invalid arguments. Process failed.");
            System.exit(1);
        }

        atkDmg = Double.parseDouble(tokens[0]);
        atkKB = Double.parseDouble(tokens[1]);
        atkDir = Integer.parseInt(tokens[2]);

        tokens = br.readLine().split(DELIMITER);

        if (tokens.length < numArgs){
            // Make real exceptions when class is finished
            // throw invalidData;
            System.out.println("Invalid arguments. Process failed.");
            System.exit(1);
        }

        grabDmg = Double.parseDouble(tokens[0]);
        grabKB = Double.parseDouble(tokens[1]);
        grabDir = Integer.parseInt(tokens[2]);

        tokens = br.readLine().split(DELIMITER);

        if (tokens.length < numArgs){
            // Make real exceptions when class is finished
            // throw invalidData;
            System.out.println("Invalid arguments. Process failed.");
            System.exit(1);
        }

        shieldDmg = Double.parseDouble(tokens[0]);
        shieldKB = Double.parseDouble(tokens[1]);
        shieldDir = Integer.parseInt(tokens[2]);

        weight = Double.parseDouble(br.readLine());
        }catch (java.io.IOException e){
            System.out.println("Invalid or corrupted file data. Failing");
            System.exit(0);
        }
    }
}