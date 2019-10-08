package RockPaperSmash

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

abstract class Stage
/**
 * Basic constructor. Takes string file containing stage's stats.
 *
 * @param statsFile String containing stage's tats,
 */
(statsFile: String) {

    var fileLoc: String = ""

    /**
     * Length and width of the stage.
     * +---------------+-------------+
     * | HoriztonalLen | VerticalLen |
     * +---------------+-------------+
     */
    private val stageStats: DoubleArray

    val horizontalLen: Double
        get() = stageStats[0]

    val verticalLen: Double
        get() = stageStats[1]

    abstract val stageName: String

    init {
        stageStats = DoubleArray(2)
        try {
            val br = FileReader.loadFile(FileResource(statsFile, true))
            fileLoc = br.readLine()
            stageStats[0] = java.lang.Double.parseDouble(br.readLine())
            stageStats[1] = java.lang.Double.parseDouble(br.readLine())

        } catch (e: java.io.IOException) {
            // Restart program when failure - add later
            println("Invalid parameters. Process failed.")
            System.exit(1)
        }

    }
}
