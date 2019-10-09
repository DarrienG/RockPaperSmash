package RockPaperSmash

import java.util.Random

class Fox(playerName: String) : Champion("stats/FoxStats.txt") {
    override val championName: String = "FOX"

    init {
        charName = playerName
    }

    // Same as standard attack, but 15% chance of triggering special on all moves.
    override fun attack(rhs: Champion): Double {
        isSpecial = false
        val rand = Random()
        if (rand.nextDouble() <= .15) {
            return specialAttack(rhs)
        }
        rhs.takeDamage(stats[actionFlag][0])
        return stats[actionFlag][1]
    }

    // 15% chance of activating on all moves, increases damage by 1.5.
    override fun specialAttack(rhs: Champion): Double {
        isSpecial = true
        rhs.takeDamage(stats[actionFlag][0] * 1.5)
        return stats[actionFlag][1]
    }

}
