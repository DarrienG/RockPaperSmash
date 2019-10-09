package RockPaperSmash

import java.lang.RuntimeException
import java.io.*

class FileCat {
    private var file1: FileResource? = null
    private var file2: FileResource? = null
    private var spaceCount: Int = 0

    /**
     * Basic constructor. Takes names of two files to be concatenated.
     *
     * @param file1 First file name.
     * @param file2 Second file name.
     */
    constructor(file1: FileResource, file2: FileResource) {
        this.file1 = file1
        this.file2 = file2
    }

    /**
     * Basic constructor. Takes name of one file, the string it will be spaced with, and the number of spaces desired.
     *
     * @param file1 Name of first file.
     * @param spaceCount Number of spaces desired.
     */
    constructor(file1: FileResource, spaceCount: Int) {
        this.file1 = file1
        this.spaceCount = spaceCount
    }

    /**
     * Concatenates two files horizontally using custom name for output file. Name must include extension if it is
     * desired.
     *
     * @param name Custom name of output file.
     * @return Name of output file.
     */
    fun lateralOp(name: String): String {
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
                right = " ".repeat(spaceCount)
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
}
