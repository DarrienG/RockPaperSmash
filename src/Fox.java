import java.util.Random;

/**
 * Created by Darrien on 12/27/15.
 */
public class Fox extends Champion {
    public Fox (String playerName){
        super("stats/FoxStats.txt");
        charName = playerName;
    }

    // Same as standard attack, but 15% chance of triggering special on all moves
    @Override
    public double attack(Champion rhs){
        isSpecial = false;
        Random rand = new Random();
        if (rand.nextDouble() <= .15){
            return specialAttack(rhs);
        }
        rhs.takeDamage(stats[getActionFlag()][0]);
        return stats[getActionFlag()][1];
    }

    // 15% chance of activating on all moves, increases damage by 1.5
    @Override
    public double specialAttack(Champion rhs){
        isSpecial = true;
        rhs.takeDamage(getStats()[getActionFlag()][0] * 1.5);
        return getStats()[getActionFlag()][1];
    }

    public String getChampionName(){
        return "FOX";
    }

}
