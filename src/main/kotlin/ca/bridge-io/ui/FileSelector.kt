package ca.`bridge-io`.ui

import com.github.michaelbull.logging.InlineLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.progress.ProgressMonitor
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * A class responsible for selecting and extracting zip files.
 *
 * @author Tyluur <itstyluur@icloud.com>
 * @since July 21, 2022
 */
class FileSelector {

	/**
	 * Displays the file chooser and handles file extraction.
	 */
	fun display() {
		val fileChooser = JFileChooser()
		val filter = FileNameExtensionFilter("Archive Files", "rar", "zip", "tar.gz")
		fileChooser.fileFilter = filter
		val option = fileChooser.showOpenDialog(null)
		val file = fileChooser.selectedFile?.absoluteFile

		if (file != null) {
			logger.info { "Selected file [$file] to unzip" }
			logger.debug { "option={$option}, file={$file}" }
		} else {
			logger.error { "No file selected" }
			throw FileSelectionException("No file selected")
		}

		if (option == JFileChooser.APPROVE_OPTION) {
			runBlocking { extractFile(file) }
		}
	}

	/**
	 * Extracts the selected file to a specified directory.
	 *
	 * @param file The file to extract.
	 */
	private suspend fun extractFile(file: File) = withContext(Dispatchers.IO) {
		val extractDirectory = File(file.absolutePath)
		logger.info { "Approve Option selected... Extracting to directory [$extractDirectory]" }
		val zipFile = ZipFile(file)
		val outputFolder = "${zipFile.file.parentFile}/${zipFile.file.name.substringBefore(".")}"
		val outputFolderFile = File(outputFolder)

		if (!outputFolderFile.exists() && !outputFolderFile.mkdirs()) {
			logger.error { "Unable to create export directory" }
			throw FileExtractionException("Unable to create export directory")
		}

		logger.debug { "Constructed export directory" }

		try {
			zipFile.extractAll(outputFolder)
			monitorExtractionProgress(zipFile)
			logger.info { "Successfully Extracted Files from ${zipFile.file.absoluteFile}" }
		} catch (e: Exception) {
			logger.error { "Failed to extract files: ${e.message}" }
			throw FileExtractionException("Failed to extract files", e)
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

	companion object {
		/**
		 * The instance of the logger for this class.
		 */
		private val logger = InlineLogger()
	}

	/**
	 * Custom exception thrown when file selection fails.
	 */
	class FileSelectionException(message: String) : Exception(message)

	/**
	 * Custom exception thrown when file extraction fails.
	 */
	class FileExtractionException(message: String, cause: Throwable? = null) : Exception(message, cause)
}
