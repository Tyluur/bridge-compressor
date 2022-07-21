package com.tyluur.ui

import com.github.michaelbull.logging.InlineLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.progress.ProgressMonitor
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since July 21, 2022
 */
class FileSelector {

    fun display() {
        val fileChooser = JFileChooser()

        with(fileChooser) {
            // Files to filter through
            val filter = FileNameExtensionFilter("Archive Files", "rar", "zip", "tar.gz")
            fileChooser.fileFilter = filter

            // the return value from the open dialog
            val option = showOpenDialog(null)

            // the file selected from the chooser
            val file = selectedFile.absoluteFile

            logger.info { "Selected file [$file] to unzip" }

            logger.debug { "option={$option}, file={$file}" }

            if (option == JFileChooser.APPROVE_OPTION) {
                val extractDirectory = File(file.absolutePath)

                logger.info { "Approve Option selected... Extracting to directory [$extractDirectory]" }

                val zipFile = ZipFile(file)
                val outputFolder =
                    "${zipFile.file.parentFile}/${
                        zipFile.file.name.substring(
                            0,
                            zipFile.file.name.indexOf(("."))
                        )
                    }"
                val outputFolderFile = File(outputFolder)

                if (!outputFolderFile.exists()) {
                    val successful = outputFolderFile.mkdirs()
                    if (!successful) {
                        logger.error { "Unable to create export directory... Shutting down" }
                        return
                    }
                    logger.debug { "Constructed export directory" }
                }

                zipFile.extractAll(outputFolder)

                val monitor = zipFile.progressMonitor

                runBlocking {
                    while (!monitor.state.equals(ProgressMonitor.State.READY)) {
                        logger.debug { "Percentage Complete = [${monitor.percentDone}], File = [${monitor.fileName}], Task = [${monitor.currentTask}]" }
                        delay(100)
                    }
                }

                logger.info { "Successfully Extracted Files from ${zipFile.file.absoluteFile}" }
            }
        }


    }

    companion object {

        /**
         * The instance of the logger for this class
         */
        private val logger = InlineLogger()

    }
}