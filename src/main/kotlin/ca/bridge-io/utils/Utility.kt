package ca.`bridge-io`.utils

import net.lingala.zip4j.io.inputstream.ZipInputStream
import net.lingala.zip4j.model.LocalFileHeader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since July 21, 2022
 */
object Utility {

    fun unzip(input: File, password: CharArray) {
        val stream = FileInputStream(input)
        var localFileHeader: LocalFileHeader
        var readLen: Int
        val readBuffer = ByteArray(4096)

        ZipInputStream(stream, password).use { zipInputStream ->
            while (zipInputStream.nextEntry.also {
                    localFileHeader = it
                } != null) {
                val extractedFile = File(localFileHeader.fileName)
                FileOutputStream(extractedFile).use { outputStream ->
                    while (zipInputStream.read(readBuffer).also {
                            readLen = it
                        } != -1) {
                        outputStream.write(readBuffer, 0, readLen)
                    }
                }
            }
        }

    }
}