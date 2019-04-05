package RockPaperSmash;

import javafx.util.Pair;

import java.util.Random;

public class CPU {
    private CPU_DIFFICULTY difficulty;
    public Champion champion;

    /**
     * Value of the opponent's previous move and whether or not they won.
     */
    int opponentPreviousMove = 0;
    boolean opponentWonLastRound = true;

    CPU(CPU_DIFFICULTY difficulty, Champion champion) {
        this.difficulty = difficulty;
        this.champion = champion;
    }

    void setMemory(int memory, boolean opponentWon) {
        opponentPreviousMove = memory;
        opponentWonLastRound = opponentWon;
    }

    int chooseMove() {
        if (difficulty == CPU_DIFFICULTY.difficult) {
            return calculatedDecision();
        }
        return simpleDecision();
    }

    /**
     * Pick random choice.
     * @return Decision. See {@link Champion} for reference to what flags mean.
     */
    private int simpleDecision() {
        int uncleanRand = new Random().nextInt() % 3;
        int rand = -1;
        if (uncleanRand < 0) {
            rand = uncleanRand * -1;
        } else {
            rand = uncleanRand;
        }
        return rand;
    }

    /**
     * Pick proper attack based on the opponent's last answer.
     * @return Decision. See {@link Champion} for reference to what flags mean.
     */
    private int calculatedDecision() {
        int uncleanRand = new Random().nextInt() % 100;
        int rand = -1;
        if (uncleanRand < 0) {
            rand = uncleanRand * -1;
        } else {
            rand = uncleanRand;
        }
        // Occasionally pick a random answer for those that know RPS theory.
        // Humans are kind of random too, so we want a little randomness.
        if (rand > 90) {
            return simpleDecision();
        }
        // Opponent did not win the last round.
        // They will likely pick whatever beat my last choice.
        // Which just so happens to be their last choice.
        // :smirk:
        if (opponentWonLastRound) {
            System.out.println("Opponent won the last round.");
            // Opponent won the last round, so they will likely pick the same thing again.
            // Pick whatever beats what they just picked.
            return ((opponentPreviousMove + 2) % 3);
        } else {
            System.out.println("Opponent lost the last round.");
            return opponentPreviousMove;
        }
    }
}

