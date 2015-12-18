/**
 * Created by Darrien on 12/17/15.
 */
public abstract class Character {

    /******************************
     * START DATA MEMBERS
     ******************************/

    // Name of the player if given
    private String charName;

    // Possible array of strings containing all file names needed to access?
    // String[] fileNames

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
     * GETTERS LOCATED BELOW
     ****************************/

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
}

