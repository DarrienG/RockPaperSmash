package RockPaperSmash

import kotlin.system.exitProcess

/**
 * Champion class. Characters, and their stats are stored here.
 */
abstract class Champion(file: String) {

    /** Name of the player if given.  */
    var charName: String = ""
        get() {
            return if (field == "") championName else field
        }
        protected set

    /** Possible array of strings containing all file names needed to access.  */
    lateinit var fileNames: Array<FileResource>
        private set

    /** Where we store the power statistics for each character, for atkDir, 0 means horizontal, 1 means vertical.
     *
     * +-----------+----------+-----------+
     * | atkDmg    | atkKB    | atkDir    |
     * +-----------+----------+-----------+
     * | grabDmg   | grabKB   | grabDir   |
     * +-----------+----------+-----------+
     * | shieldDmg | shieldKB | shieldDir |
     * +-----------+----------+-----------+
     */
    val stats = Array(3) { DoubleArray(3) }

    /**
     * How heavy the player is, affects vertical survivability, and horizontal recovery.
     */
    var gravity: Double = 0.0
        private set

    /**
     * How long character's recovery is when knocked off horizontally.
     */
    var recovery: Double = 0.0
        private set

    /**
     * Amount of damage character has taken thus far.
     */
    var percentDmg: Double = 0.0
        private set

    /**
     * Whether or not the character has been KO'd.
     */
    var isKO: Boolean = false
        private set

    /**
     * Sets status of attack using pseudo-C enumeration.
     * Attack, Grab, or Shield respectively as 0, 1, or 2.
     *
     * Assuming safeguards are not implemented in main method, function will pick a random num between 0 and 2 if
     * the action is out of bounds.
     */
    var actionFlag: Int = 0
        set(action) {
            field = if (action <= 2 || action >= 0) action else (Math.random() % 2).toInt()
        }

    /**
     * ASCII formatting spacer.
     */
    var spacer: Int = 0
        private set

    /**
     * Determines if an attack was a special attack or not
     */
    var isSpecial: Boolean = false
        protected set

    /**
     * Tag that inherently defines character based on archetypes.
     */
    var attribute: String? = null
        private set

    val atkDmg: Double
        get() = stats[0][0]

    val atkKB: Double
        get() = stats[0][1]

    val atkDir: Double
        get() = stats[0][2]

    val grabDmg: Double
        get() = stats[1][0]

    val grabKB: Double
        get() = stats[1][1]

    val grabDir: Double
        get() = stats[1][2]

    val shieldDmg: Double
        get() = stats[2][0]

    val shieldKB: Double
        get() = stats[2][1]

    val shieldDir: Double
        get() = stats[2][2]

    abstract val championName: String

    init {
        dataCrawler(file)
        percentDmg = 0.0
        isKO = false
        isSpecial = false
    }

    // Deals damage to opponent based on the move used, and returns knockback
    open fun attack(rhs: Champion): Double {
        rhs.takeDamage(stats[actionFlag][0])
        return stats[actionFlag][1]
    }

    /**
     * Special attack for given [Champion]Champion. Implementation is [Champion]Champion specific.
     * @param rhs Champion on the receiving end of the attack.
     * @return Amount of knockback from move.
     */
    abstract fun specialAttack(rhs: Champion): Double

    /**
     * Helper method, amount of damage taken from an action.
     * @param amtDmg Amount of damage.
     */
    open fun takeDamage(amtDmg: Double) {
        percentDmg += amtDmg
    }

    /** Switches KO status from true to false, and false to true.  */
    fun toggleKO() {
        isKO = !isKO
    }


    /**
     * Resets name of a given Champion.
     *
     * @param name New player name of Champion.
     */
    fun resetName(name: String) {
        charName = name
    }

    /**
     * Reads in data for a character based off of given file name, and puts in [Champion&#39;s][Champion]stats.
     *
     * @param file Name of file containing [Champion&#39;s][Champion] stats.
     */
    fun dataCrawler(file: String) {
        val DELIMITER = ", "

        try {
            val br = FileReader.loadFile(FileResource(file, true))
            var tokens: Array<String>
            val readLine = { ->
                br.readLine().split(DELIMITER.toRegex()).dropLastWhile {
                    it.isEmpty()
                }.toTypedArray()
            }

            // Set file information immediately to raw data in file
            fileNames = resourceToFileResource(readLine())

            if (fileNames.size != 6) {
                // Make real exceptions when class is finished
                // throw invalidData;
                println("Invalid arguments. Process failed.")
                exitProcess(1)
            }

            // Get attack, grab, and shield stats, and set them
            stats.forEach { row ->
                tokens = readLine()
                if (tokens.size < row.size) {
                    // Make real exceptions when class is finished
                    // throw invalidData;
                    println("Invalid arguments. Process failed.")
                    exitProcess(1)
                }
                row[0] = java.lang.Double.parseDouble(tokens[0])
                row[1] = java.lang.Double.parseDouble(tokens[1])
                row[2] = Integer.parseInt(tokens[2]).toDouble()
            }

            gravity = java.lang.Double.parseDouble(br.readLine())
            recovery = java.lang.Double.parseDouble(br.readLine())
            spacer = Integer.parseInt(br.readLine())
            attribute = br.readLine()
        } catch (e: java.io.IOException) {
            throw RuntimeException("Invalid or corrupted file data. Failing$e")
        }

    }

    /**
     * Maps array of Strings to array of FileResource.
     * Why can't this be Kotlin :(
     * Gimme my map :(
     *
     * @param rawResources Array of String to be converted.
     * @return resources converted to FileResources.
     */
    private fun resourceToFileResource(rawResources: Array<String>): Array<FileResource> {
        return Array(rawResources.size) { i -> FileResource(rawResources[i]) }
    }

}
