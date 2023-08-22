package ca.bridge_io

import ca.bridge_io.ui.FileSelector
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * This class tests the [Bootstrap] class, ensuring that the core application bootstrapping process
 * functions correctly.
 *
 * Specifically, it tests the interaction between the [Bootstrap] class and the [FileSelector] class,
 * using Mockito to verify the expected behavior.
 *
 * @author Tyluur
 */
class BootstrapTest {

	/**
	 * The [FileSelector] instance that will be used in the tests.
	 * This instance is a mock, allowing for controlled behavior and verification of interactions.
	 */
	private lateinit var fileSelector: FileSelector

	/**
	 * The [Bootstrap] instance that is being tested.
	 * It will be created with a mock [FileSelector] to allow for controlled testing.
	 */
	private lateinit var bootstrap: Bootstrap

	/**
	 * Sets up the test environment before each test execution.
	 *
	 * This method creates a mock [FileSelector] instance and injects it into the [Bootstrap] instance.
	 */
	@BeforeEach
	fun setup() {
		// Create a mock instance of FileSelector
		fileSelector = mock()

		// Inject the mock into the Bootstrap class
		bootstrap = Bootstrap(fileSelector)
	}

	/**
	 * Tests that the [Bootstrap.execute] method calls the [FileSelector.display] method.
	 *
	 * This test verifies that the bootstrapping process correctly triggers the file selection display.
	 * It does this by calling [Bootstrap.execute] and then verifying that [FileSelector.display] was called.
	 */
	@Test
	fun `execute should call FileSelector display`() {
		// Call the method under test
		bootstrap.execute()

		// Verify that the display method of FileSelector was called
		verify(fileSelector).display()
	}
}
