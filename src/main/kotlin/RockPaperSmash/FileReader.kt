package RockPaperSmash

import java.lang.RuntimeException
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object FileReader {

    val tmpDir: File
        get() {
            try {
                return Files.createTempDirectory("TmpBattleFiles").toFile()
            } catch (e: java.io.IOException) {
                println("Unable to create temp dir")
                throw RuntimeException(e)
            }

        }

    @Throws(java.io.IOException::class)
    fun loadFile(rFile: FileResource): BufferedReader {
        val file: InputStream? = if (rFile.resource) {
            ClassLoader.getSystemResourceAsStream(rFile.fileName)
        } else {
            FileInputStream(rFile.fileName)
        }
        return BufferedReader(InputStreamReader(file!!, StandardCharsets.UTF_8))
    }
}
