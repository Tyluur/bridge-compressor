package ca.`bridge-io`

import com.github.michaelbull.logging.InlineLogger
import ca.`bridge-io`.ui.FileSelector

class Bootstrap {

    /**
     * The instance of the file selector module
     */
    private val fileSelector = FileSelector()

    /**
     * Executes the bootstrap
     */
    fun execute() {
        logger.info { "Bootstrap executed" }
        fileSelector.display()
    }

    companion object {

        /**
         * The logger for the bootstrap class
         */

        private val logger = InlineLogger()
    }
}

/**
 * Executes the bootstrap function
 */
fun main() = Bootstrap().execute()