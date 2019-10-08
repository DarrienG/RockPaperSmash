package RockPaperSmash

import java.util.Random

internal class CaptainFalcon(playerName: String) : Champion("stats/CaptainFalconStats.txt") {
    private val modifier = 2.0
    private var setUp = false

    override val championName: String
        get() = "CAPTAIN FALCON"

    init {
        charName = playerName
    }

    override fun attack(rhs: Champion): Double {
        isSpecial = false
        if (setUp && actionFlag == 0 && Random().nextDouble() > .2) {
            return specialAttack(rhs)
        } else {
            setUp = false
        }

        if (actionFlag == 1) {
            setUp = true
        }
        rhs.takeDamage(stats[actionFlag][0])
        return stats[actionFlag][1] * (modifier / 2)
    }

    override fun takeDamage(amtDmg: Double) {
        super.takeDamage(amtDmg)
    }

    override fun specialAttack(rhs: Champion): Double {
        isSpecial = true
        rhs.takeDamage(atkDmg * modifier)
        return atkKB * modifier
    }
}
