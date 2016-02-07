import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Champion class. Characters, and their stats are stored here.
 */
public abstract class Champion {

    /**
     * Basic constructor. Takes file where Champion stats are located to construct Champion.
     *
     * @param file Where Champion stats are located.
     */
    public Champion(String file) {
        dataCrawler(file);
        percentDmg = 0;
        isKO = false;
        isSpecial = false;
    }

    /** Name of the player if given. */
    protected String charName;

    /** Possible array of strings containing all file names needed to access. */
    private String[] fileNames;

    /** Where we store the power statistics for each character, for atkDir, 0 means horizontal, 1 means vertical.
     *
     *    +-----------+----------+-----------+
     *    | atkDmg    | atkKB    | atkDir    |
     *    +-----------+----------+-----------+
     *    | grabDmg   | grabKB   | grabDir   |
     *    +-----------+----------+-----------+
     *    | shieldDmg | shieldKB | shieldDir |
     *    +-----------+----------+-----------+
     */
    protected double[][] stats;

    /**
     * How heavy the player is, affects vertical survivability, and horizontal recovery.
     */
    private double gravity;

    /**
     * How long character's recovery is when knocked off horizontally.
     */
    private double recovery;

    /**
     * Amount of damage character has taken thus far.
     */
    private double percentDmg;

    /**
     * Whether or not the character has been KO'd.
     */
    private boolean isKO;

    /**
     * Sets status of attack using pseudo-C enumeration.
     * Attack, Grab, or Shield respectively as 0, 1, or 2.
     */
    private int actionFlag;

    /**
     * ASCII formatting spacer.
     */
    private int spacer;

    /**
     * Determines if an attack was a special attack or not
     */
    protected boolean isSpecial;

    /**
     * Tag that inherently defines character based on archetypes.
     */
    private String attribute;

    public double[][] getStats() {
        return stats;
    }

    // Deals damage to opponent based on the move used, and returns knockback
    public double attack(Champion rhs) {
        rhs.takeDamage(stats[actionFlag][0]);
        return stats[actionFlag][1];
    }

    /**
     * Special attack for given {@link Champion}Champion. Implementation is {@link Champion}Champion specific.
     * @param rhs Champion on the receiving end of the attack.
     * @return Amount of knockback from move.
     */
    public abstract double specialAttack(Champion rhs);

    /**
     * Helper method, amount of damage taken from an action.
     * @param amtDmg Amount of damage.
     */
    public void takeDamage(double amtDmg) {
        percentDmg += amtDmg;
    }

    /** Switches KO status from true to false, and false to true. */
    public void toggleKO() {
        isKO = !isKO;
    }

    // Sets the action chosen by user
    // Assuming safeguards are not implemented in main, will pick a random num between 0 and 2 if action is out of bounds

    /**
     * Sets action chosen by user.
     * Assuming safeguards are not implemented in main method, function will pick a random num between 0 and 2 if
     * the action is out of bounds.
     *
     * @param action Specified action by user. [0] = attack, [1] = grab, [2] = shield.
     */
    public void setActionFlag(int action) {
        actionFlag = !(action > 2) || !(action < 0) ? action : (int)(Math.random() % 2);
    }

    /**
     * Resets name of a given Champion.
     *
     * @param name New player name of Champion.
     */
    public void resetName(String name) {
        charName = name;
    }

    //

    /**
     * Reads in data for a character based off of given file name, and puts in {@link Champion Champion's}stats.
     *
     * @param file Name of file containing {@link Champion Champion's} stats.
     */
    public void dataCrawler(String file) {
        stats = new double[3][3];

        String DELIMITER = ", ";
        int numArgs = 3;

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] tokens;

            // Set file information immediately to raw data in file
            fileNames = br.readLine().split(DELIMITER);

            if (fileNames.length != 6) {
                // Make real exceptions when class is finished
                // throw invalidData;
                System.out.println("Invalid arguments. Process failed.");
                System.exit(1);
            }

            // Get attack, grab, and shield stats, and set them
            for (int i = 0; i < numArgs; ++i) {
                tokens = br.readLine().split(DELIMITER);

                if (tokens.length < numArgs) {
                    // Make real exceptions when class is finished
                    // throw invalidData;
                    System.out.println("Invalid arguments. Process failed.");
                    System.exit(1);
                }
                stats[i][0] = Double.parseDouble(tokens[0]);
                stats[i][1] = Double.parseDouble(tokens[1]);
                stats[i][2] = Integer.parseInt(tokens[2]);
            }

            gravity = Double.parseDouble(br.readLine());
            recovery = Double.parseDouble(br.readLine());
            spacer = Integer.parseInt(br.readLine());
            attribute = br.readLine();

        } catch (java.io.IOException e) {
            throw new RuntimeException("Invalid or corrupted file data. Failing");
        }
    }

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

    public double getGravity() {
        return gravity;
    }

    public double getRecovery() {
        return recovery;
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

    public int getSpacer() {
        return spacer;
    }

    public abstract String getChampionName();

    public boolean isSpecial() {
        return isSpecial;
    }

    public String getAttribute() {
        return attribute;
    }
}