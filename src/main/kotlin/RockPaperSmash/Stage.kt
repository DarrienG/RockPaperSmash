package RockPaperSmash

import kotlin.system.exitProcess

abstract class Stage(statsFile: String) {
    var fileLoc: String = ""

    /**
     * Length and width of the stage.
     * +---------------+-------------+
     * | HoriztonalLen | VerticalLen |
     * +---------------+-------------+
     */
    private val stageStats = DoubleArray(2)

    val horizontalLen: Double
        get() = stageStats[0]

    val verticalLen: Double
        get() = stageStats[1]

    abstract val stageName: String

    init {
        try {
            val br = FileReader.loadFile(FileResource(statsFile, true))
            fileLoc = br.readLine()
            stageStats[0] = java.lang.Double.parseDouble(br.readLine())
            stageStats[1] = java.lang.Double.parseDouble(br.readLine())

        } catch (e: java.io.IOException) {
            // Restart program when failure - add later
            println("Invalid parameters. Process failed.")
            exitProcess(1)
        }

    }
}
