package fr.nemolovich.apps.securefolder.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.lingala.zip4j.exception.ZipException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.nemolovich.apps.securefolder.zip.ZipUtils;

public class TestZipUtils {

	@Test(expected = FileNotFoundException.class)
	public void unExistingFile() throws ZipException,
			FileNotFoundException {
		boolean created = ZipUtils.zipFile(new File(MainTestSuite.FILE_PATH
				+ File.separator + MainTestSuite.FILE_NAME + "fake"), new File(
				MainTestSuite.FILE_PATH + File.separator
						+ MainTestSuite.FILE_NAME + ".zip"), "test");
		assertTrue(created);
	}

	@Test
	public void secureExistingFolder() throws ZipException,
			FileNotFoundException {
		ZipUtils.secureFolder(new File(MainTestSuite.FILE_PATH + File.separator
				+ MainTestSuite.FOLDER_NAME), "azerty");
	}

	// @Test
	// public void existingFile() throws ZipException, FileNotFoundException {
	// File encrypted = new File(MainTestSuite.FILE_PATH + File.separator
	// + MainTestSuite.FILE_NAME + ".encrypted");
	// boolean created = ZipUtils.zipFile(new File(MainTestSuite.FILE_PATH
	// + File.separator + MainTestSuite.FILE_NAME), encrypted);
	// assertTrue(created);
	// assertTrue(encrypted.exists());
	// FileUtils.hideFile(encrypted.getParent(), encrypted.getName());
	// assertFalse(encrypted.exists());
	// encrypted = new File(MainTestSuite.FILE_PATH + File.separator + "."
	// + MainTestSuite.FILE_NAME + ".encrypted");
	// assertTrue(encrypted.exists());
	// File sflock = new File(MainTestSuite.FILE_PATH + File.separator
	// + MainTestSuite.FILE_NAME + ".sflock");
	// created = ZipUtils.zipFile(encrypted, sflock, "test");
	// assertTrue(sflock.exists());
	// assertTrue(encrypted.exists());
	// assertTrue(encrypted.delete());
	// assertFalse(encrypted.exists());
	// assertTrue(created);
	// }

	@BeforeClass
	public static void init() throws IOException {
		System.out
				.println("[INFO] ------------------------------------------------------------------------");
		System.out.println("[INFO] Tests for: " + TestZipUtils.class.getName());
		System.out
				.println("[INFO] ------------------------------------------------------------------------");
	}

	@AfterClass
	public static void close() {
		if (!MainTestSuite.isExecutingAll()) {
			MainTestSuite.close();
		}
	}
}
