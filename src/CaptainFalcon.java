import java.util.Random;

class CaptainFalcon extends Champion {
    private double modifier = 2;
    private boolean setUp = false;

    public CaptainFalcon(String playerName) {
        super("stats/CaptainFalconStats.txt");
        charName = playerName;
    }

    @Override
    public double attack(Champion rhs) {
        isSpecial = false;
        if (setUp && getActionFlag() == 0 && new Random().nextDouble() > .2) {
            return specialAttack(rhs);
        } else {
            setUp = false;
        }

        if (getActionFlag() == 1) {
            setUp = true;
        }
        rhs.takeDamage(stats[actionFlag][0]);
        return stats[actionFlag][1] * (modifier / 2);
    }

    @Override
    public void takeDamage(double amtDmg) {
        super.takeDamage(amtDmg);
    }

    @Override
    public double specialAttack(Champion rhs) {
        isSpecial = true;
        rhs.takeDamage(getAtkDmg() * modifier);
        return getAtkKB() * modifier;
    }

    @Override
    public String getChampionName() {
        return "CAPTAIN FALCON";
    }
}
