package RockPaperSmash;

public class Falco extends Champion {
    private double damageModifier = 1;

    public Falco (String playerName) {
        super("stats/FalcoStats.txt");
        charName = playerName;
    }

    // Falco is special in that every consecutive hit he gets doubles the power of his next hit.
    @Override
    public double attack(Champion rhs) {
        isSpecial = false;
        damageModifier *= 2;
        rhs.takeDamage(stats[actionFlag][0] * (damageModifier / 2));
        if (damageModifier > 2) {
            isSpecial = true;
        }
        return stats[actionFlag][1] * (damageModifier / 2);
    }

    @Override
    public void takeDamage(double amtDmg) {
        super.takeDamage(amtDmg);
        damageModifier = 1;
    }

    // Falco has no special attack.
    @Override
    public double specialAttack(Champion rhs) {
        return 0;
    }

    @Override
    public String getChampionName() {
        return "FALCO";
    }
}
