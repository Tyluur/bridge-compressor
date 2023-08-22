package ca.bridge_io

import ca.bridge_io.ui.FileSelector
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class BootstrapTest {

	private lateinit var fileSelector: FileSelector
	private lateinit var bootstrap: Bootstrap

	@BeforeEach
	fun setup() {
		// Create a mock instance of FileSelector
		fileSelector = mock()

		// Inject the mock into the Bootstrap class
		bootstrap = Bootstrap(fileSelector)
	}

	@Test
	fun `execute should call FileSelector display`() {
		// Call the method under test
		bootstrap.execute()

		// Verify that the display method of FileSelector was called
		verify(fileSelector).display()
	}
}
