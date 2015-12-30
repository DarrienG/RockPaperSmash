import java.util.Random;

/**
 * Created by darrien on 12/30/15.
 */
public class Falco extends Champion{

    private boolean isHG;

    public Falco (String playerName){
        super("stats/FalcoStats.txt");
        charName = playerName;
    }

    @Override
    public double attack(Champion rhs){
        isSpecial = false;
        Random rand = new Random();
        double randCalc = rand.nextDouble();

        // Determines if Falco will get his pillar combo
        if (randCalc <= .45 && (getActionFlag() == 0 || getActionFlag() == 2)){
            if (rhs.getGravity() < .9 && rhs.getPercentDmg() <= 50){
                isHG = false;
                return specialAttack(rhs);
            }
            else if (rhs.getGravity() >= .9 && rhs.getPercentDmg() <= 65){
                isHG = true;
                return specialAttack(rhs);
            }
        }
        rhs.takeDamage(stats[getActionFlag()][0]);
        return stats[getActionFlag()][1];
    }


    @Override
    public double specialAttack(Champion rhs){
        if (isHG){
            rhs.takeDamage(55);
            return stats[getActionFlag()][1];
        }
        else{
            rhs.takeDamage(65);
            return stats[getActionFlag()][1];
        }
    }

    public String getChampionName(){
        return "FALCO";
    }
}
