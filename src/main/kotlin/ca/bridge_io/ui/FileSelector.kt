package ca.bridge_io.ui

import ca.bridge_io.utils.Utility.extractFile
import com.github.michaelbull.logging.InlineLogger
import kotlinx.coroutines.runBlocking
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
