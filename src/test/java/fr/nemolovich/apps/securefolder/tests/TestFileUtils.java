package fr.nemolovich.apps.securefolder.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.nemolovich.apps.securefolder.file.FileUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFileUtils {

	@Test
	public void aNonExistingPath() {
		assertFalse(FileUtils.hideFile(MainTestSuite.FILE_PATH + "NonExisting",
				MainTestSuite.FILE_NAME));
	}

	@Test
	public void aNonExistingFile() {
		assertFalse(FileUtils.hideFile(MainTestSuite.FILE_PATH,
				MainTestSuite.FILE_NAME + "NonExisting"));
	}

	@Test
	public void hideExistingFile() {
		assertTrue(FileUtils.hideFile(MainTestSuite.FILE_PATH,
				MainTestSuite.FILE_NAME));
	}

	@Test
	public void unHideExistingFile() {
		assertTrue(FileUtils.unHideFile(MainTestSuite.FILE_PATH,
				MainTestSuite.FILE_NAME));
	}

	@Test
	public void getExistingContent() throws FileNotFoundException {
		List<String> list=FileUtils.getFolderContent(new File(MainTestSuite.FILE_PATH
				+ File.separator + MainTestSuite.FOLDER_NAME));
		System.out.println(Arrays.toString(list.toArray()));
		assertNotNull(list);
	}

	@AfterClass
	public static void close() {
		if (!MainTestSuite.isExecutingAll()) {
			MainTestSuite.close();
		}
	}
}
