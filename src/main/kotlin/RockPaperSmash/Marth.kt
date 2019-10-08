package RockPaperSmash

import java.util.Random

class Marth(playerName: String) : Champion("stats/MarthStats.txt") {

    override val championName: String
        get() = "MARTH"

    init {
        charName = playerName
    }

    // Same as standard attack, but with 30% chance of triggering special
    override fun attack(rhs: Champion): Double {
        isSpecial = false
        if (actionFlag == 0) {
            val rand = Random()
            if (rand.nextDouble() <= .3) {
                return specialAttack(rhs)
            }
        }

        // Chaingrabs against spacies deal extra damage
        if (actionFlag == 1 && rhs.attribute == "spacie") {
            if (rhs.percentDmg == 0.0) {
                rhs.takeDamage(46.0)
                return grabKB
            } else {
                rhs.takeDamage(grabDmg * 1.25)
                return grabKB
            }
        }

        rhs.takeDamage(stats[actionFlag][0])
        return stats[actionFlag][1]
    }

    // 30% chance of occurring, doubles Marth's attack power, and increases KB by 1.25
    override fun specialAttack(rhs: Champion): Double {
        isSpecial = true
        rhs.takeDamage(atkDmg * 2)
        return atkKB * 1.25
    }
}
