package ca.bridge_io.utils

import net.lingala.zip4j.io.inputstream.ZipInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Utility object containing helper functions related to file operations.
 *
 * @author Tyluur <itstyluur@icloud.com>
 * @since July 21, 2022
 */
object Utility {

    /**
     * Unzips a given ZIP file using an optional password.
     *
     * @param input The ZIP file to be unzipped.
     * @param password The password used to unlock the ZIP file (if encrypted).
     * @throws Exception if the ZIP file is corrupted or an incorrect password is provided.
     */
    fun unzip(input: File, password: CharArray) {
        FileInputStream(input).use { fileInputStream ->
            val readBuffer = ByteArray(4096)

            ZipInputStream(fileInputStream, password).use { zipInputStream ->
                while (true) {
                    val localFileHeader = zipInputStream.nextEntry ?: break
                    val extractedFile = File(localFileHeader.fileName)

                    FileOutputStream(extractedFile).use { outputStream ->
                        while (true) {
                            val readLen = zipInputStream.read(readBuffer)
                            if (readLen == -1) break
                            outputStream.write(readBuffer, 0, readLen)
                        }
                    }
                }
            }
        }
    }
}
