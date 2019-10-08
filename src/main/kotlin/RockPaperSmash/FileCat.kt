package RockPaperSmash

import java.lang.RuntimeException
import java.io.*

class FileCat {
    private var file1: FileResource? = null
    private var file2: FileResource? = null
    private var extension: String? = null
    private var spacer: String? = null
    private var spaceCount: Int = 0

    /**
     * Gets the current default file name.
     *
     * @return Current default file name.
     */
    val defaultFileName: String
        get() = "output$mProcCount$extension"

    /**
     * Basic constructor. Takes names of two files to be concatenated.
     *
     * @param file1 First file name.
     * @param file2 Second file name.
     */
    constructor(file1: FileResource, file2: FileResource) {
        this.file1 = file1
        this.file2 = file2
        extension = ".txt"
    }

    /**
     * Basic constructor. Takes names of two files to be concatenated, and extension of output files.
     *
     * @param file1 First file name.
     * @param file2 Second file name.
     * @param extension Extension of output file.
     */
    constructor(file1: FileResource, file2: FileResource, extension: String) {
        this.file1 = file1
        this.file2 = file2
        this.extension = ".$extension"
    }

    /**
     * Basic constructor. Takes name of one file, the string it will be spaced with, and the number of spaces desired.
     *
     * @param file1 Name of first file.
     * @param spacer String String containing characters to be spaced with.
     * @param spaceCount Number of spaces desired.
     */
    constructor(file1: FileResource, spacer: String, spaceCount: Int) {
        this.file1 = file1
        this.file2 = null
        this.spacer = spacer
        this.spaceCount = spaceCount
        extension = ".txt"
    }

    /**
     * Basic constructor. Takes name of one file, the string it will be spaced with, number of spaces desired, and the
     * file extension.
     *
     * @param file1 Name of first file.
     * @param spacer String String containing characters to be spaced with.
     * @param spaceCount Number of spaces desired.
     * @param extension Extension of output file.
     */
    constructor(file1: FileResource, spacer: String, spaceCount: Int, extension: String) {
        this.file1 = file1
        this.file2 = null
        this.spacer = spacer
        this.spaceCount = spaceCount
        this.extension = extension
    }

    /**
     * Concatenates two files horizontally using default naming convention for output file.
     * @return String containing name of the file.
     */
    fun LateralOp(): String {
        LateralOp(defaultFileName)
        ++mProcCount
        return defaultFileName
    }

    /**
     * Concatenates two files horizontally using custom name for output file. Name must include extension if it is
     * desired.
     *
     * @param name Custom name of output file.
     * @return Name of output file.
     */
    fun LateralOp(name: String): String {
        var right: String?
        try {
            val br = FileReader.loadFile(file1!!)
            val bw = BufferedWriter(FileWriter(name))

            // Working with two files
            if (file2 != null) {
                var left: String?
                val br2 = FileReader.loadFile(file2!!)
                do {
                    left = br.readLine()
                    right = br2.readLine()

                    bw.write((left ?: "") + (right ?: ""))
                    bw.newLine()
                } while (left != null || right != null)
                bw.flush()
            } else {
                right = spacer?.repeat(spaceCount)
                br.forEachLine {
                    left ->
                    bw.write(left + right)
                    bw.newLine()
                }
                bw.flush()
            } // Working with spacers
        } catch (e: FileNotFoundException) {
            System.err.println("ERROR: File does not exist. Please input a valid file.$e")
            e.printStackTrace()
            throw RuntimeException(":(")
        } catch (e: IOException) {
            System.err.println("ERROR: Failure while reading from file.$e")
        }

        return name
    }

    /**
     * Concatenates two files vertically using default naming convention for output file. Name must include extension
     * if it is desired.
     *
     * @return String containing name of file.
     */
    fun VerticalOp(): String {
        VerticalOp(defaultFileName)
        return defaultFileName
    }

    //

    /**
     * Concatenates two files vertically using custom name for output file.
     *
     * @param name Custom name of output file.
     * @return String containing name of file.
     */
    fun VerticalOp(name: String): String {
        try {
            val br = FileReader.loadFile(file1!!)
            val br2 = FileReader.loadFile(file2!!)
            val bw = BufferedWriter(FileWriter(name))

            br.forEachLine { line -> bw.write(line) }
            br2.forEachLine { line -> bw.write(line) }
            bw.flush()
        } catch (e: FileNotFoundException) {
            println("ERROR: File does not exist. Please input a valid input file.$e")
        } catch (e: IOException) {
            println("ERROR: Failure while reading from file.$e")
        }

        ++mProcCount
        return name
    }

    companion object {
        private var mProcCount = 0
    }
}
