package RockPaperSmash

import java.util.Random
import kotlin.math.absoluteValue

class CPU internal constructor(private val difficulty: CPU_DIFFICULTY, var champion: Champion) {

    /**
     * Value of the opponent's previous move and whether or not they won.
     */
    internal var opponentPreviousMove = 0
    internal var opponentWonLastRound = true

    internal fun setMemory(memory: Int, opponentWon: Boolean) {
        opponentPreviousMove = memory
        opponentWonLastRound = opponentWon
    }

    internal fun chooseMove(): Int {
        return if (difficulty == CPU_DIFFICULTY.difficult) {
            calculatedDecision()
        } else simpleDecision()
    }

    /**
     * Pick random choice.
     * @return Decision. See [Champion] for reference to what flags mean.
     */
    private fun simpleDecision(): Int {
        return (Random().nextInt() % 3).absoluteValue
    }

    /**
     * Pick proper attack based on the opponent's last answer.
     * @return Decision. See [Champion] for reference to what flags mean.
     */
    private fun calculatedDecision(): Int {
        // Occasionally pick a random answer for those that know RPS theory.
        // Humans are kind of random too, so we want a little randomness.
        if ((Random().nextInt() % 100).absoluteValue > 90) {
            return simpleDecision()
        }
        return if (opponentWonLastRound) {
            // Opponent won the last round, so they will likely pick the same thing again.
            // Pick whatever beats what they just picked.
            (opponentPreviousMove + 2) % 3
        } else {
            // Opponent did not win the last round.
            // They will likely pick whatever beat my last choice.
            // Which just so happens to be their last choice.
            // :smirk:
            opponentPreviousMove
        }
    }
}

