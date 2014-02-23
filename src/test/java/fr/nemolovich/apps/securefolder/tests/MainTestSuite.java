package fr.nemolovich.apps.securefolder.tests;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.nemolovich.apps.securefolder.SecureFolder;


@RunWith(Suite.class)
@SuiteClasses({
	TestBatchCommand.class,
	TestFileUtils.class,
	TestZipUtils.class,
})
public class MainTestSuite {
	/**
	 * Path to file for {@link TestFileUtils} and {@link TestZipUtils}
	 */
	public static final String FILE_PATH = "/home/nemo/Documents/Tests";
//	public static final String FILE_PATH = "/home/nemo/Documents/Tests";
	/**
	 * File for {@link TestFileUtils} and {@link TestZipUtils}
	 */
	public static final String FILE_NAME = "fileTest";
	public static final String FOLDER_NAME = "folderTest";
	private static boolean EXECUTE_ALL = false;

	@BeforeClass
	public static void init() throws IOException {
		System.out.println("[INFO] ------------------------------------------------------------------------");
		System.out.println("[INFO] INITIALIZING TESTS");
		System.out.println("[INFO] ------------------------------------------------------------------------");
		EXECUTE_ALL=true;
	}
	
	@AfterClass
	public static void close() {
		try {
			System.out.println("[INFO] ------------------------------------------------------------------------");
			System.out.println("[INFO] TERMINATING TESTS");
			System.out.println("[INFO] ------------------------------------------------------------------------");
			SecureFolder.logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isExecutingAll() {
		return EXECUTE_ALL;
	}
}
