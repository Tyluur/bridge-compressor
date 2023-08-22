package ca.bridge_io.utils

import ca.bridge_io.ui.FileSelector
import com.github.michaelbull.logging.InlineLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.io.inputstream.ZipInputStream
import net.lingala.zip4j.progress.ProgressMonitor
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

	/**
	 * Extracts the selected file to a specified directory.
	 *
	 * @param file The file to extract.
	 */
	suspend fun extractFile(file: File) = withContext(Dispatchers.IO) {
		val extractDirectory = File(file.absolutePath)
		logger.info { "Approve Option selected... Extracting to directory [$extractDirectory]" }
		val zipFile = ZipFile(file)
		val outputFolder = "${zipFile.file.parentFile}/${zipFile.file.name.substringBefore(".")}"
		val outputFolderFile = File(outputFolder)

		if (!outputFolderFile.exists() && !outputFolderFile.mkdirs()) {
			logger.error { "Unable to create export directory" }
			throw FileSelector.FileExtractionException("Unable to create export directory")
		}

		logger.debug { "Constructed export directory" }

		try {
			zipFile.extractAll(outputFolder)
			monitorExtractionProgress(zipFile)
			logger.info { "Successfully Extracted Files from ${zipFile.file.absoluteFile}" }
		} catch (e: Exception) {
			logger.error { "Failed to extract files: ${e.message}" }
			throw FileSelector.FileExtractionException("Failed to extract files", e)
		}
	}

	/**
	 * Monitors the extraction progress.
	 *
	 * @param zipFile The ZipFile to monitor.
	 */
	private suspend fun monitorExtractionProgress(zipFile: ZipFile) = withContext(Dispatchers.Default) {
		val monitor = zipFile.progressMonitor
		while (!monitor.state.equals(ProgressMonitor.State.READY)) {
			logger.debug { "Percentage Complete = [${monitor.percentDone}], File = [${monitor.fileName}], Task = [${monitor.currentTask}]" }
			delay(100)
		}
	}

	/**
	 * The instance of the logger for this class
	 */
	private val logger = InlineLogger()

}
