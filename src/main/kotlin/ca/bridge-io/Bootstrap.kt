package ca.`bridge-io`

import com.github.michaelbull.logging.InlineLogger
import ca.`bridge-io`.ui.FileSelector

/**
 * The [Bootstrap] class is responsible for initializing and executing the application's core functionality.
 * It serves as the entry point for the application and coordinates the execution of key components, such as
 * the [FileSelector].
 *
 * @property fileSelector An instance of the [FileSelector] class responsible for handling file selection and operations.
 *
 * @author Tyluur
 */
class Bootstrap(
	/**
	 * An instance of the [FileSelector] class that can be injected for flexibility.
	 * If not provided, a default instance will be created.
	 */
	private val fileSelector: FileSelector = FileSelector()
) {

	/**
	 * Executes the bootstrap process.
	 *
	 * This method initializes and runs the core components of the application.
	 * It starts by logging the initiation of the bootstrap process, then calls
	 * the [FileSelector.display] method to handle file selection and operations,
	 * and finally logs the successful completion of the bootstrap.
	 *
	 * If any exceptions are encountered during the execution, they are caught
	 * and logged, and the application continues without crashing.
	 *
	 * @throws Exception If any unexpected error occurs during execution.
	 */
	fun execute() {
		try {
			logger.info { "Starting Bootstrap execution..." }
			fileSelector.display()
			logger.info { "Bootstrap executed successfully" }
		} catch (e: Exception) {
			logger.error { "An error occurred during Bootstrap execution: ${e.message}" }
		}
	}

	companion object {

		/**
		 * The logger for the bootstrap class.
		 *
		 * This logger is used to log informational, debug, and error messages related
		 * to the bootstrap process.
		 */
		private val logger = InlineLogger()
	}
}

/**
 * The main function of the application.
 *
 * This function serves as the entry point for the application. It creates an instance of the [Bootstrap]
 * class and calls its [Bootstrap.execute] method to initiate and run the application.
 *
 * @author Your Name
 */
fun main() = Bootstrap().execute()
