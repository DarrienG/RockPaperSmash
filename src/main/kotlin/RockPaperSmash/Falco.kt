package RockPaperSmash

class Falco(playerName: String) : Champion("stats/FalcoStats.txt") {
    private var damageModifier = 1.0

    override val championName: String = "FALCO"

    init {
        charName = playerName
    }

    // Falco is special in that every consecutive hit he gets doubles the power of his next hit.
    override fun attack(rhs: Champion): Double {
        isSpecial = false
        damageModifier *= 2.0
        rhs.takeDamage(stats[actionFlag][0] * (damageModifier / 2))
        if (damageModifier > 2) {
            isSpecial = true
        }
        return stats[actionFlag][1] * (damageModifier / 2)
    }

    override fun takeDamage(amtDmg: Double) {
        super.takeDamage(amtDmg)
        damageModifier = 1.0
    }

    // Falco has no special attack.
    override fun specialAttack(rhs: Champion): Double {
        return 0.0
    }
}
