package RockPaperSmash

import java.util.Random
import java.util.Scanner
import java.util.Stack

import RockPaperSmash.CPU_DIFFICULTY.difficult
import RockPaperSmash.CPU_DIFFICULTY.easy
import kotlin.math.absoluteValue

fun main() {
    val d = Driver()
    try {
        d.menu()
    } catch (e: Exception) {
        println("Program prematurely killed. Cleaning up before exiting.")
        e.printStackTrace()
        d.cleanDir()
    }

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun start() {
            println("Exiting.")
            println("Thanks for playing RockPaperSmash!")
            d.cleanDir()
        }
    })
}

/**
 * Main class. Everything is run here.
 */
class Driver {

    /**
     * Positive responses that can be used to answer dialogs.
     */
    private val POS_RESPONSES = arrayOf("y", "yes", "1", "yup", "good", "check", "yeah", "y e s", "yee", "praise duarte", "hell yeah", "hell yes")

    // /** Negative responses used to answer dialogs. Not implemented anywhere yet. */
    // public static final String[] negResponses = {"n", "no", "0", "nada", "bad", "x", "never", "n o", "praise ive",
    // "hell no"};

    /**
     * Options for choosing attack option.
     */
    private val ATTACK_OPS = arrayOf("attack", "a", "1")

    /**
     * Options for choosing grab option.
     */
    private val GRAB_OPS = arrayOf("grab", "g", "2")

    /**
     * Options for choosing shield option.
     */
    private val SHIELD_OPS = arrayOf("shield", "s", "3")

    /**
     * Directories for all of the number files.
     */
    private val NUM_LOC = arrayOf(FileResource("Assets/Numbers/Zero.txt"), FileResource("Assets/Numbers/One.txt"), FileResource("Assets/Numbers/Two.txt"), FileResource("Assets/Numbers/Three.txt"), FileResource("Assets/Numbers/Four.txt"), FileResource("Assets/Numbers/Five.txt"), FileResource("Assets/Numbers/Six.txt"), FileResource("Assets/Numbers/Seven.txt"), FileResource("Assets/Numbers/Eight.txt"), FileResource("Assets/Numbers/Nine.txt"), FileResource("Assets/Numbers/Mod.txt"))

    /**
     * Directories for all action text.
     */
    private val ACTION_FILES = arrayOf(FileResource("Assets/Actions/Attack.txt"), FileResource("Assets/Actions/Grab.txt"), FileResource("Assets/Actions/Shield.txt"), FileResource("Assets/Actions/Mid.txt"))

    /**
     * Directories for all titles.
     */
    private val TITLE_LOC = arrayOf("Assets/DisplayScreens/Title/Title1.txt", "Assets/DisplayScreens/Title/Title2.txt", "Assets/DisplayScreens/Title/Title3.txt")

    /**
     * Copies of every character for easy selection. Likely to be replaced by strings later.
     */
    private val CHAR_LIST = arrayOf(Marth("NULL"), Fox("NULL"), Falco("NULL"), CaptainFalcon("NULL"))

    /**
     * Copies of every stage, likely to be replaced later.
     */
    private val STAGE_LIST = arrayOf<Stage>(Battlefield())

    private val CPU_IDENTIFIER = "CPU"

    /**
     * Names of player 1 and player 2.
     */
    private lateinit var mP1Name: String
    private lateinit var mP2Name: String

    private val ATTACK_STRING = ", what move do you want to make? (a)ttack, (g)rab, (s)hield\n> "

    private val TMP_DIR = FileReader.tmpDir

    /**
     * Allows choice of a [Stage].
     *
     * @return Chosen stage.
     */
    // Test validity of player choice for a character
    // Only go here if the player chose a valid Stage
    // Should never reach here
    // Stage becomes Battlefield if some impossible flaw in logic causes this to happen
    private val field: Stage
        get() {
            var input: String
            var response: String
            var goodPick = false
            val sc = Scanner(System.`in`)
            println()
            while (!goodPick) {
                print("Stage list: ")
                for (st in STAGE_LIST) {
                    print("\"" + st.stageName + "\" ")
                }
                println()
                print("What stage would you like to pick?\n> ")
                input = sc.nextLine().toLowerCase().trim { it <= ' ' }

                var count = 0
                for (st in STAGE_LIST) {
                    if (st.stageName.toLowerCase() == input) {
                        goodPick = true
                        break
                    }
                    ++count
                }
                if (!goodPick) {
                    println("Sorry, I don't recognize that stage.")
                    println()
                }
                if (goodPick) {
                    animate(FileResource(STAGE_LIST[count].fileLoc))
                    print("Is \"$input\" good?\n> ")
                    response = sc.nextLine()
                    response = response.toLowerCase().trim { it <= ' ' }
                    for (comparator in POS_RESPONSES) {
                        if (response == comparator) {
                            return STAGE_LIST[count]
                        }
                    }
                    goodPick = false
                }
            }
            println("Fatal choosing error. Defaulting to Battlefield.")
            return Battlefield()
        }

    /**
     * Has one player choose the [Champion] they would like to play.
     *
     * @param player Name of the player.
     * @return Champion.
     */
    fun getCharacter(player: String): Champion {
        var input: String
        var response: String
        var goodPick = false
        val sc = Scanner(System.`in`)
        animate(FileResource("Assets/DisplayScreens/CharSelect/Select.txt", true))

        while (!goodPick) {
            print("Character list: ")
            for (ch in CHAR_LIST) {
                print("\"${ch.championName}\" ")
            }
            println()
            print("$player please choose your character: ")
            input = sc.nextLine().toLowerCase().trim { it <= ' ' }

            var count = 0
            // Test validity of player choice for a character
            for (ch in CHAR_LIST) {
                if (ch.championName.toLowerCase().trim { it <= ' ' } == input) {
                    goodPick = true
                    break
                }
                ++count
            }
            if (!goodPick) {
                println("Sorry, I don't recognize that character.")
                println()
            }

            // Only go here if the player chose a valid Champion
            if (goodPick) {
                animate(CHAR_LIST[count].fileNames[0])
                print("Is \"$input\" good?\n> ")
                response = sc.nextLine()
                response = response.toLowerCase().trim { it <= ' ' }
                for (comparator in POS_RESPONSES) {
                    if (response == comparator) {
                        val ch = CHAR_LIST[count]
                        ch.resetName(player)
                        return ch
                    }
                }
                goodPick = false
            }
        }

        // Should never reach here
        // Player becomes a Marth if some impossible flaw in logic causes this to happen
        println("Fatal choosing error. Defaulting to Marth.")
        return Marth(player)
    }

    fun pickRandomCharacter(): Champion {
        println("Picking CPU character...")
        snooze(1000)
        val cpuChampion = CHAR_LIST[(Random().nextInt() % CHAR_LIST.size).absoluteValue]
        cpuChampion.resetName(CPU_IDENTIFIER)
        animateNoJump(cpuChampion.fileNames[0])
        val prettyChampion = cpuChampion.championName.toLowerCase().trim { it <= ' ' }
        println("CPU picks: $prettyChampion")

        snooze(2000)
        return cpuChampion
    }

    /**
     * Decides which player gets to go first based off of a coin flip, then begins the battle.
     *
     * @param player1 Player 1, the first [Champion] in the battle.
     * @param player2 Player 2, the second [Champion] in the battle.
     * @param arena   The chosen [Stage] where the players will be battling.
     */
    fun battle(player1: Champion, player2: Champion, arena: Stage) {
        val rand = Random()
        val coinFlip = rand.nextDouble()
        val sc = Scanner(System.`in`)

        clear()

        println("Today on ${arena.stageName} we have ")
        println("${player1.charName}: ${player1.championName}")
        println("\tvs.")
        println("${player2.charName}: ${player2.championName}")


        // Player 1 goes first
        if (coinFlip > .5) {
            println("${player1.charName} (Player 1) goes first!")
            print("<ENTER> to begin")
            sc.nextLine()
            battleBegin(player1, player2, arena)
        } else {
            println("${player2.charName} (Player 2) goes first!")
            print("\n\n<ENTER> to begin ")
            sc.nextLine()
            battleBegin(player2, player1, arena)
        }
        // Player 2 goes first
    }

    /**
     * Decides which player gets to go first based off of a coin flip, then begins the battle.
     *
     * @param player1 Player 1, the first [Champion] in the battle.
     * @param cpuPlayer Player 2, the second [Champion] in the battle.
     * @param arena   The chosen [Stage] where the players will be battling.
     */
    fun cpuBattle(player1: Champion, cpuPlayer: CPU, arena: Stage) {
        val sc = Scanner(System.`in`)

        println("Today on ${arena.stageName} we have ")
        println("${player1.charName}: ${player1.championName}")
        println("\tvs.")
        println("${cpuPlayer.champion.charName}: ${cpuPlayer.champion.championName}")

        println("Single player battle. ${player1.charName} vs. $CPU_IDENTIFIER")
        println("${player1.charName} (Player 1) goes first!")
        print("<ENTER> to begin")
        sc.nextLine()
        cpuBattleBegin(player1, cpuPlayer, arena)
    }

    /**
     * Potential helper method. Can be called after after battle() for randomized start, or called on its own so
     * players get to pick who goes first.
     *
     * TODO: Refactor the bulk of this method into a shared method.
     *
     * @param first  The first [Champion] in the battle.
     * @param second The second [Champion] in the battle.
     * @param arena  The chosen [Stage] where the players will be battling.
     */
    fun battleBegin(first: Champion, second: Champion, arena: Stage) {
        val sc = Scanner(System.`in`)
        var inputFirst: String
        var inputSecond: String
        val fc = FileCat(first.fileNames[1], second.fileNames[2])
        val partBattle = fc.lateralOp(TMP_DIR.absolutePath + "/PartBattleFile.txt")

        var knockBack: Double
        while (!first.isKO && !second.isKO) {
            animate(FileResource(partBattle, false))
            formatNames(first, second)
            animateNoJump(FileResource(percentageMaker(first, second), false))

            print(first.charName + ATTACK_STRING)
            inputFirst = String(System.console().readPassword()).toLowerCase().trim { it <= ' ' }

            clear()

            print(second.charName + ATTACK_STRING)
            inputSecond = String(System.console().readPassword()).toLowerCase().trim { it <= ' ' }
            // Do the array thing with choices tomorrow

            first.actionFlag = -1
            second.actionFlag = -1
            for (a in ATTACK_OPS) {
                if (inputFirst == a) {
                    first.actionFlag = 0
                }
                if (inputSecond == a) {
                    second.actionFlag = 0
                }
            }
            for (g in GRAB_OPS) {
                if (inputFirst == g) {
                    first.actionFlag = 1
                }
                if (inputSecond == g) {
                    second.actionFlag = 1
                }
            }
            for (s in SHIELD_OPS) {
                if (inputFirst == s) {
                    first.actionFlag = 2
                }
                if (inputSecond == s) {
                    second.actionFlag = 3
                }
            }

            if (first.actionFlag != -1 && second.actionFlag != -1) {
                // Print result of each attack
                // As a reminder, 0 = attack (rock), 1 = grab (scissors), 2 = shield (paper)

                if (first.actionFlag == 0 && second.actionFlag == 0 ||
                        first.actionFlag == 1 && second.actionFlag == 1 ||
                        first.actionFlag == 2 && second.actionFlag == 2) {
                    println("Same option chosen by " + first.charName + " and " + second.charName + "!\nNo damage taken.")
                } else if (second.actionFlag == first.actionFlag + 1 || second.actionFlag == first.actionFlag - 2) {
                    val initDmg = second.percentDmg
                    knockBack = first.attack(second)

                    if (first.isSpecial) {
                        animate(first.fileNames[3])
                        try {
                            Thread.sleep(1500)
                        } catch (e: InterruptedException) {
                            println("Caught $e\nPlease be more careful next time.")
                        }

                    }

                    actionDisplay(first, second)

                    println(second.charName + " takes: ")
                    singlePercDisplay(second.percentDmg - initDmg)

                    // Is knockback horizontal?
                    if (first.stats[first.actionFlag][2] == 0.0) {
                        knockBack *= second.percentDmg * second.gravity

                        if (knockBack > arena.horizontalLen) {
                            second.toggleKO()
                            println(second.charName + " has been KO'd!")
                        }
                    } else {
                        knockBack = second.percentDmg * knockBack / second.gravity

                        if (knockBack > arena.verticalLen || knockBack > second.recovery) {
                            second.toggleKO()
                            println(second.charName + " has been KO'd!")
                        }
                    }// Or is is vertical?

                } else {
                    val initDmg = first.percentDmg
                    knockBack = second.attack(first)

                    if (second.isSpecial) {
                        animate(second.fileNames[4])
                        try {
                            Thread.sleep(1500)
                        } catch (e: InterruptedException) {
                            println("Caught $e\nPlease be more careful next time.")
                        }

                    }

                    actionDisplay(first, second)

                    println(first.charName + " takes :")
                    singlePercDisplay(first.percentDmg - initDmg)

                    // Is knockback horizontal?
                    if (second.stats[second.actionFlag][2] == 0.0) {
                        knockBack *= first.percentDmg * first.gravity

                        if (knockBack > arena.horizontalLen || knockBack > first.recovery) {
                            first.toggleKO()
                            println(first.charName + " has been KO'd!")
                        }
                    } else {
                        knockBack = first.percentDmg * knockBack / first.gravity

                        if (knockBack > arena.verticalLen) {
                            first.toggleKO()
                            println(first.charName + " has been KO'd!")
                        }
                    }// Or is is vertical?
                }
                println("<ENTER> to continue")
                sc.nextLine()
            } else {
                val misMatch = if (first.actionFlag == -1) first.charName else second.charName
                println("Invalid action entered by $misMatch please re-enter commands.")
            }// Invalid action entered by one of the two players
            // TODO: End battle, possible increment counter, champs may require extra data member to determine player
            // number

        }
    }

    /**
     * Potential helper method. Can be called after after battle() for randomized start, or called on its own so
     * players get to pick who goes first.
     *
     * TODO: Refactor bulk of this method into a different shared method.
     *
     * @param first  The first [Champion] in the battle.
     * @param cpu CPU to battle againt.
     * @param arena  The chosen [Stage] where the players will be battling.
     */
    fun cpuBattleBegin(first: Champion, cpu: CPU, arena: Stage) {
        val sc = Scanner(System.`in`)
        var inputFirst: String
        val fc = FileCat(first.fileNames[1], cpu.champion.fileNames[2])
        val partBattle = fc.lateralOp(TMP_DIR.absolutePath + "/PartBattleFile.txt")

        var knockBack: Double
        while (!first.isKO && !cpu.champion.isKO) {
            animate(FileResource(partBattle, false))
            formatNames(first, cpu.champion)
            animateNoJump(FileResource(percentageMaker(first, cpu.champion), false))

            print(first.charName + ATTACK_STRING)
            inputFirst = String(System.console().readPassword()).toLowerCase().trim { it <= ' ' }

            clear()

            println(cpu.champion.charName + " thinking...")
            snooze(750)
            clear()
            cpu.champion.actionFlag = cpu.chooseMove()

            first.actionFlag = -1
            for (a in ATTACK_OPS) {
                if (inputFirst == a) {
                    first.actionFlag = 0
                }
            }
            for (g in GRAB_OPS) {
                if (inputFirst == g) {
                    first.actionFlag = 1
                }
            }
            for (s in SHIELD_OPS) {
                if (inputFirst == s) {
                    first.actionFlag = 2
                }
            }

            val processResult = { winner: Champion, loser: Champion ->
                val initDmg = loser.percentDmg
                knockBack = winner.attack(loser)

                if (winner.isSpecial) {
                    animate(winner.fileNames[3])
                    snooze(1500)
                }

                actionDisplay(winner, loser)
                println("${loser.charName} takes: ")
                singlePercDisplay(loser.percentDmg - initDmg)

                // Is knockback horizontal?
                if (winner.stats[winner.actionFlag][2] == 0.0) {
                    knockBack *= loser.percentDmg * loser.gravity
                    if (knockBack > arena.horizontalLen) {
                        loser.toggleKO()
                        println("${loser.charName} has been KO'd!")
                    }
                } else {
                    knockBack *= loser.percentDmg / loser.gravity
                    if (knockBack > arena.verticalLen || knockBack > loser.recovery) {
                        loser.toggleKO()
                        println("${loser.charName} has been KO'd!")
                    }
                }
                // Or is is vertical?
            }

            if (first.actionFlag != -1 && cpu.champion.actionFlag != -1) {
                // Print result of each attack
                // As a reminder, 0 = attack (rock), 1 = grab (scissors), 2 = shield (paper)\

                if (first.actionFlag == 0 && cpu.champion.actionFlag == 0 ||
                        first.actionFlag == 1 && cpu.champion.actionFlag == 1 ||
                        first.actionFlag == 2 && cpu.champion.actionFlag == 2) {
                    println("Same option chosen by ${first.charName} and ${cpu.champion.charName}!")
                    println("No damage taken.")
                } else if (cpu.champion.actionFlag == first.actionFlag + 1 ||
                        cpu.champion.actionFlag == first.actionFlag - 2) {
                    // Player 1 wins the round
                    cpu.setMemory(first.actionFlag, true)
                    processResult(first, cpu.champion)
                } else {
                    // CPU wins the round
                    cpu.setMemory(first.actionFlag, false)
                    processResult(cpu.champion, first)
                }
                println("<ENTER> to continue")
                sc.nextLine()
            } else {
                val misMatch = if (first.actionFlag == -1) first.charName else cpu.champion.charName
                println("Invalid action entered by $misMatch please re-enter commands.")
            }
            // Invalid action entered by one of the two players
            // TODO: End battle, possible increment counter, champs may require extra data member to determine player
            // number

        }
    }

    /**
     * Takes actions by two players, and outputs them to a unicode art version of the move.
     *
     * @param first  The first player.
     * @param second The second player.
     */
    fun actionDisplay(first: Champion, second: Champion) {
        var fc = FileCat(ACTION_FILES[first.actionFlag], ACTION_FILES[3])
        var outFile = fc.lateralOp(TMP_DIR.absolutePath + "/tmpPerc.txt")

        val resourceOutFile = FileResource(outFile, false)
        fc = FileCat(resourceOutFile, ACTION_FILES[second.actionFlag])
        outFile = fc.lateralOp(TMP_DIR.absolutePath + "/PercFin.txt")
        animateNoJump(FileResource(outFile, false))
    }

    /**
     * Creates text art of damage of player, then outputs it to the screen in percentage form.
     *
     * @param inputDmg The total amount of damage received.
     */
    fun singlePercDisplay(inputDmg: Double) {
        val numStack = Stack<Int>()
        val tmpLoc = TMP_DIR.absolutePath + "/"
        var fileOut: String
        var dmg = inputDmg.toInt()

        var fc: FileCat

        if (dmg > 0) {
            while (dmg > 0) {
                numStack.push(dmg % 10)
                dmg /= 10
            }

            // Leading zero on percentages less than 10
            if (numStack.size == 1) {
                fc = FileCat(NUM_LOC[0], NUM_LOC[numStack.pop()])
                fileOut = fc.lateralOp(tmpLoc + "TmpDmg.txt")
                fc = FileCat(FileResource(fileOut, false), NUM_LOC[10])
                fileOut = fc.lateralOp(tmpLoc + "UnspacedDmg.txt")
            } else {
                fc = FileCat(NUM_LOC[numStack.pop()], NUM_LOC[numStack.pop()])
                fileOut = fc.lateralOp(tmpLoc + "TmpDmg.txt")
                // Percentage greater than 100
                if (numStack.size > 0) {
                    fc = FileCat(FileResource(fileOut, false), NUM_LOC[numStack.pop()])
                    fileOut = fc.lateralOp(tmpLoc + "TmpDmg2.txt")
                }
                fc = FileCat(FileResource(fileOut, false), NUM_LOC[10])
                fileOut = fc.lateralOp(tmpLoc + "FinDmg.txt")
            }// Percentage greater than/equal to 9
        } else {
            fc = FileCat(NUM_LOC[0], NUM_LOC[0])
            fileOut = fc.lateralOp(tmpLoc + "TmpDmg.txt")
            fc = FileCat(FileResource(fileOut, false), NUM_LOC[10])
            fileOut = fc.lateralOp(tmpLoc + "FinDmg.txt")
        }

        /*fc = new FileCat(fileOut, NUM_LOC[9]);
        fileOut = fc.LateralOp(tmpLoc + "modPlus.txt");*/
        fc = FileCat(FileResource(fileOut, false), 8)
        fileOut = fc.lateralOp(tmpLoc + "dmgPlus.txt")
        fc = FileCat(FileResource(fileOut, false), FileResource("Assets/Extras/Damage.txt"))
        animateNoJump(FileResource(fc.lateralOp(tmpLoc + "outDmg.txt"), false))
    }

    /**
     * Helper method, copies value from one Champion into a temporary Champion to be later returned.
     *
     * @param ch Champion chosen.
     * @return Clean version of Champion chosen.
     */
    fun clone(ch: Champion): Champion {
        return when (ch.championName.toLowerCase()) {
            "marth" -> Marth(ch.charName)
            "fox" -> Fox(ch.charName)
            "falco" -> Falco(ch.charName)
            "captain falcon" -> CaptainFalcon(ch.charName)
            else -> {
                // Default to Marth if there is some impossible logic flaw that allows this to slip through the cracks
                println("Impossible logic flaw. Defaulting to Marth.")
                Marth(ch.charName)
            }
        }
    }

    /**
     * Takes a file name, and outputs the result to the screen, after clearing the screen.
     *
     * @param file Resource of file to be opened and output.
     */
    fun animate(file: FileResource) {
        // The length of my terminal in lines, may or may not correspond to your terminal size
        clear()
        try {
            FileReader.loadFile(file).forEachLine { line -> println(line) }
        } catch (e: java.io.IOException) {
            println("Info page not found.")
            e.printStackTrace()
        }

    }

    /**
     * Takes a file name, and outputs the results to the screen.
     *
     * @param file Resource of file to be opened and output.
     */
    fun animateNoJump(file: FileResource) {
        try {
            FileReader.loadFile(file).forEachLine { line -> println(line) }
        } catch (e: java.io.IOException) {
            println("Info page not found.")
        }

    }

    // Creates ASCII output of percentages, damage capped at 999%

    /**
     * Creates ASCII art of player percentages, and places them next to each other. Returns newly created file name.
     * Damage display is capped at 999%.
     *
     * @param first  Player 1.
     * @param second Player 2.
     * @return File name of created file.
     */
    fun percentageMaker(first: Champion, second: Champion): String {
        val numStack = Stack<Int>()
        var firstPercent = first.percentDmg.toInt()
        var secondPercent = second.percentDmg.toInt()
        var spacer = first.spacer
        val tmpLoc = TMP_DIR.absolutePath + "/"
        var firstLoc: String
        var secondLoc: String
        var hundredPlusFlag = false

        var fc: FileCat

        if (firstPercent > 0) {
            while (firstPercent > 0) {
                numStack.push(firstPercent % 10)
                firstPercent /= 10
            }

            // Leading zero on percentages less than 10
            if (numStack.size == 1) {
                fc = FileCat(NUM_LOC[0], NUM_LOC[numStack.pop()])
                firstLoc = fc.lateralOp(tmpLoc + "tmpFirstPercent.txt")
                fc = FileCat(FileResource(firstLoc, false), NUM_LOC[10])
                firstLoc = fc.lateralOp(tmpLoc + "unspacedFirstPercent.txt")
            } else {
                fc = FileCat(NUM_LOC[numStack.pop()], NUM_LOC[numStack.pop()])
                firstLoc = fc.lateralOp(tmpLoc + "tmpFirstPercent.txt")
                // Percentage greater than 100
                if (numStack.size > 0) {
                    fc = FileCat(FileResource(firstLoc, false), NUM_LOC[numStack.pop()])
                    firstLoc = fc.lateralOp(tmpLoc + "tmpFirstPercent2.txt")
                    hundredPlusFlag = true
                }
                fc = FileCat(FileResource(firstLoc, false), NUM_LOC[10])
                firstLoc = fc.lateralOp(tmpLoc + "unspacedFirstPercent.txt")
            }// Percentage greater than/equal to 9
        } else {
            fc = FileCat(NUM_LOC[0], NUM_LOC[0])
            firstLoc = fc.lateralOp(tmpLoc + "tmpFirstPercent.txt")
            fc = FileCat(FileResource(firstLoc, false), NUM_LOC[10])
            firstLoc = fc.lateralOp(tmpLoc + "unspacedFirstPercent.txt")
        }

        spacer = if (hundredPlusFlag) spacer - 8 else spacer
        fc = FileCat(FileResource(firstLoc, false), spacer)
        firstLoc = fc.lateralOp(tmpLoc + "firstPercent.txt")

        // Second player's percentage
        if (secondPercent > 0) {
            while (secondPercent > 0) {
                numStack.push(secondPercent % 10)
                secondPercent /= 10
            }

            // Leading zero on percentages less than 10
            if (numStack.size == 1) {
                fc = FileCat(NUM_LOC[0], NUM_LOC[numStack.pop()])
                secondLoc = fc.lateralOp(tmpLoc + "tmpSecondPercent.txt")
                fc = FileCat(FileResource(secondLoc, false), NUM_LOC[10])
                secondLoc = fc.lateralOp(tmpLoc + "secondPercent.txt")
            } else {
                fc = FileCat(NUM_LOC[numStack.pop()], NUM_LOC[numStack.pop()])
                secondLoc = fc.lateralOp(tmpLoc + "tmpSecondPercent.txt")
                // Percentage greater than 100
                if (numStack.size > 0) {
                    fc = FileCat(FileResource(secondLoc, false), NUM_LOC[numStack.pop()])
                    secondLoc = fc.lateralOp(tmpLoc + "tmpSecondPercent2.txt")
                }
                fc = FileCat(FileResource(secondLoc, false), NUM_LOC[10])
                secondLoc = fc.lateralOp(tmpLoc + "secondPercent.txt")
            }// Percentage greater than/equal to 9
        } else {
            fc = FileCat(NUM_LOC[0], NUM_LOC[0])
            secondLoc = fc.lateralOp(tmpLoc + "tmpSecondPercent.txt")
            fc = FileCat(FileResource(secondLoc, false), NUM_LOC[10])
            secondLoc = fc.lateralOp(tmpLoc + "secondPercent.txt")
        }
        fc = FileCat(FileResource(firstLoc, false), FileResource(secondLoc, false))
        return fc.lateralOp(tmpLoc + "finalPercents.txt")
    }

    /**
     * Formats spacing of player names, so they are aligned correctly regardless of character choice.
     *
     * @param first  Player 1.
     * @param second Player 2.
     */
    private fun formatNames(first: Champion, second: Champion) {
        var trueSpacer = first.spacer + 25 - first.charName.length
        print(first.charName)
        if (trueSpacer < 1) {
            trueSpacer = 1
        }
        for (i in 0 until trueSpacer) {
            print(" ")
        }
        println(second.charName)
    }

    // Removes temporary battle files from tmpBattleFiles folder

    /**
     * Removes temporary battle files from tmpBattleFiles folder.
     */
    fun cleanDir() {
        TMP_DIR.listFiles()?.mapNotNull { file ->
            if (!file.delete()) {
                println("Error cleaning up file: " + file.absolutePath)
            }
        }
    }

    /**
     * Reads intro files, and prints them to the screen with a slight delay in between prints.
     */
    fun displayTitle() {
        val sc = Scanner(System.`in`)
        clear()
        for (titleScreen in TITLE_LOC) {
            animateNoJump(FileResource(titleScreen))
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                println("Program killed prematurely.$e")
            }

        }

        println("Press Start (<ENTER>) to begin!")
        sc.nextLine()
    }

    /**
     * Displays the main menu.
     */
    fun menu() {
        val sc = Scanner(System.`in`)
        var input: String
        displayTitle()
        while (true) {
            clear()
            println("~~~~~~~~~~~~~~~~~~")
            println("Main menu")
            println("1. Options")
            println("2. Player vs. Player")
            println("3. Player vs. CPU")
            println("4. Show Characters")
            println("5. Quit")
            print("> ")

            input = sc.nextLine().trim { it <= ' ' }.toLowerCase()

            when (input) {
                "options", "1", "1." -> options()
                "2", "2.", "pvp", "player vs. player", "player vs player" -> pvp()
                "3.", "3" -> pvcpu()
                "4", "4." -> showChars()
                "5", "5.", "quit", "q" -> return

                else -> println("I didn't recognize that input. Please enter input again.\n")
            }
        }
    }

    fun showChars() {
        val sc = Scanner(System.`in`)
        var c: Champion
        for (i in CHAR_LIST.indices) {
            c = CHAR_LIST[i]
            animate(c.fileNames[0])
            print(i + 1)
            print('/')
            print(CHAR_LIST.size)
            print(" Enter for next, q to quit\n> ")
            val input = sc.nextLine()
            if (input == "q") {
                return
            }
        }
    }

    /**
     * Displays the options menu.
     */
    fun options() {
        val sc = Scanner(System.`in`)
        animate(FileResource("Assets/DisplayScreens/Options/Options.txt", true))
        println("Empty settings menu for now. Come back later.")
        println("I'll fill it with good stuff later.")
        println("<ENTER> to return to the main menu")
        sc.nextLine()
    }

    /**
     * Begins a player vs. player battle.
     */
    fun pvp() {
        makeNames()
        val p1 = clone(getCharacter(mP1Name))
        clear()
        val p2 = clone(getCharacter(mP2Name))
        clear()
        val st = field
        battle(p1, p2, st)
        cleanDir()
    }

    /**
     * Begins a player vs. CPU battle.
     */
    fun pvcpu() {
        setUpPvCpu()
        val p1 = clone(getCharacter(mP1Name))
        clear()
        val p2 = clone(pickRandomCharacter())
        clear()
        val st = field
        cpuBattle(p1, CPU(setCpuDifficulty(), p2), st)
        cleanDir()
    }

    private fun setCpuDifficulty(): CPU_DIFFICULTY {
        val sc = Scanner(System.`in`)
        while (true) {
            println("Set CPU difficulty: (e)asy, (d)ifficult")
            print("> ")
            when (sc.nextLine().toLowerCase().trim { it <= ' ' }) {
                "e" -> return easy
                "d" -> return difficult
                else -> {
                    println("Invalid difficulty.\n")
                }
            }
        }
    }

    /**
     * Helper method, gets the tags of two players by calling getName twice
     */
    private fun makeNames() {
        animate(FileResource("Assets/DisplayScreens/TagSelect/TagS.txt", true))
        mP1Name = getName("Player 1")
        mP2Name = getName("Player 2")
    }

    private fun setUpPvCpu() {
        animate(FileResource("Assets/DisplayScreens/TagSelect/TagS.txt", true))
        mP1Name = getName("Player 1")
        mP2Name = CPU_IDENTIFIER
    }

    /**
     * Helper method, gets player tag, and returns it.
     *
     * @param player Player number choosing their tag.
     * @return The returned tag.
     */
    private fun getName(player: String): String {
        var input: String
        var response: String
        val sc = Scanner(System.`in`)
        println()
        while (true) {
            print("$player please enter your tag: ")
            input = sc.nextLine()
            print("Is \"$input\" good?\n> ")
            response = sc.nextLine()
            response = response.toLowerCase().trim { it <= ' ' }
            for (comparator in POS_RESPONSES) {
                if (response == comparator) {
                    return input
                }
            }
        }
    }

    /**
     * Helper method, clears what is currently on the screen by printing new lines.
     * TODO: Make this the size of the user's output device.
     */
    private fun clear() {
        for (i in 0..124) {
            println()
        }
    }

    private fun snooze(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            println("Something happened. idk")
        }

    }
}
