package fr.nemolovich.apps.securefolder.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.lingala.zip4j.exception.ZipException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.file.FileUtils;
import fr.nemolovich.apps.securefolder.zip.ZipUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestZipUtils {

	@Test(expected = FileNotFoundException.class)
	public void firstZipUnExistingFile() throws ZipException,
			FileNotFoundException {
		boolean created = ZipUtils.zipFile(new File(MainTestSuite.FILE_PATH
				+ File.separator + MainTestSuite.FILE_NAME + "fake"), new File(
				MainTestSuite.FILE_PATH + File.separator
						+ MainTestSuite.FILE_NAME + ".zip"), "azerty");
		assertTrue(created);
	}

	@Test
	public void firstZipExistingFile() throws ZipException,
			FileNotFoundException {
		File sourceFile = new File(MainTestSuite.FILE_PATH + File.separator
				+ MainTestSuite.FILE_NAME);
		boolean created = ZipUtils.zipFile(sourceFile, new File(
				MainTestSuite.FILE_PATH + File.separator
						+ MainTestSuite.FILE_NAME + ".zip"), "azerty");
		assertTrue(created);
		File okFile = new File(sourceFile.getAbsolutePath() + "_ok");
		sourceFile.renameTo(okFile);
		assertTrue(okFile.exists());
	}

	@Test
	public void thenUnzipExistingFile() throws ZipException {
		boolean created = ZipUtils.unzipFile(new File(MainTestSuite.FILE_PATH
				+ File.separator + MainTestSuite.FILE_NAME + ".zip"), new File(
				MainTestSuite.FILE_PATH), "azerty");
		assertTrue(created);
	}

	@Test
	public void secureExistingFolder() throws ZipException,
			FileNotFoundException {
		boolean created = ZipUtils.secureFolder(new File(
				MainTestSuite.FILE_PATH + File.separator
						+ MainTestSuite.FOLDER_NAME), "azerty");
		assertTrue(created);
	}

	@Test
	public void unsecureExistingFolder() throws ZipException,
			FileNotFoundException {
		boolean created = ZipUtils.unsecureFolder(
				new File(MainTestSuite.FILE_PATH
						+ File.separator
						+ MainTestSuite.FOLDER_NAME.concat(".").concat(
								SecureFolder.EXTENSION_LOCK)), "azerty");
		assertTrue(created);
	}

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
		File sourceFolder = new File(MainTestSuite.FILE_PATH.concat(
				File.separator).concat(MainTestSuite.FOLDER_NAME));
		assertTrue(FileUtils.removeFolder(sourceFolder));
		sourceFolder = new File(MainTestSuite.FILE_PATH.concat(File.separator)
				.concat(MainTestSuite.FOLDER_NAME).concat("_ok"));
		assertTrue(FileUtils.renameFolder(
				sourceFolder,
				new File(MainTestSuite.FILE_PATH.concat(File.separator).concat(
						MainTestSuite.FOLDER_NAME))));
		File sourceFile = new File(MainTestSuite.FILE_PATH + File.separator
				+ MainTestSuite.FILE_NAME);
		assertTrue(sourceFile.delete());
		File okFile = new File(MainTestSuite.FILE_PATH + File.separator
				+ MainTestSuite.FILE_NAME + "_ok");
		assertTrue(okFile.renameTo(new File(MainTestSuite.FILE_PATH
				+ File.separator + MainTestSuite.FILE_NAME)));
		File sourceFileZipped = new File(MainTestSuite.FILE_PATH
				+ File.separator + MainTestSuite.FILE_NAME + ".zip");
		assertTrue(sourceFileZipped.delete());
		File sflockFile = new File(MainTestSuite.FILE_PATH
				+ File.separator
				+ MainTestSuite.FOLDER_NAME.concat(".").concat(
						SecureFolder.EXTENSION_LOCK));
		assertTrue(sflockFile.delete());
		File encrypted = new File(MainTestSuite.FILE_PATH
				.concat(File.separator)
				.concat(".")
				.concat(MainTestSuite.FOLDER_NAME.concat(".").concat(
						ZipUtils.ENCRYPTED_EXTENSION)));
		assertTrue(encrypted.delete());
		if (!MainTestSuite.isExecutingAll()) {
			MainTestSuite.close();
		}
	}
}
