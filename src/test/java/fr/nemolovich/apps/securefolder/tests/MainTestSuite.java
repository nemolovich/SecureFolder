package fr.nemolovich.apps.securefolder.tests;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.nemolovich.apps.securefolder.SecureFolder;

@RunWith(Suite.class)
@SuiteClasses({ TestBatchCommand.class, TestFileUtils.class,
		TestZipUtils.class, })
public class MainTestSuite {
	/**
	 * Path to file for {@link TestFileUtils} and {@link TestZipUtils}
	 */
	public static final String FILE_PATH_LINUX = "/home/nemo/Documents/Tests";
	public static final String CUSTOMIZED_COMMAND_LINUX = "ls -la";
	public static String FILE_PATH_WINDOWS = "C:\\Users\\Nemolovich\\Desktop\\SecureFolder";
	public static String CUSTOMIZED_COMMAND_WINDOWS = "dir";
	public static String FILE_PATH = FILE_PATH_WINDOWS;
	public static String CUSTOMIZED_COMMAND = CUSTOMIZED_COMMAND_WINDOWS;
	/**
	 * File for {@link TestFileUtils} and {@link TestZipUtils}
	 */
	public static final String FILE_NAME = "uselessFile";
	public static final String FOLDER_NAME = "Test";
	private static boolean EXECUTE_ALL = false;

	@BeforeClass
	public static void init() throws IOException {
		System.out
				.println("[INFO] ------------------------------------------------------------------------");
		System.out.println("[INFO] INITIALIZING TESTS");
		System.out
				.println("[INFO] ------------------------------------------------------------------------");
		EXECUTE_ALL = true;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("nux")) {
			FILE_PATH = FILE_PATH_LINUX;
			CUSTOMIZED_COMMAND = CUSTOMIZED_COMMAND_LINUX;
		}
	}

	@AfterClass
	public static void close() {
		try {
			System.out
					.println("[INFO] ------------------------------------------------------------------------");
			System.out.println("[INFO] TERMINATING TESTS");
			System.out
					.println("[INFO] ------------------------------------------------------------------------");
			SecureFolder.logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isExecutingAll() {
		return EXECUTE_ALL;
	}
}
