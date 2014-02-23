package fr.nemolovich.apps.securefolder.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.batch.BatchCommand;
import fr.nemolovich.apps.securefolder.batch.BatchUtils;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;
import fr.nemolovich.apps.securefolder.logger.ILogger;

/**
 * Some file utilities
 * 
 * @author nemo
 * 
 */
public class FileUtils {
	private static ILogger logger = new ClassLogger(FileUtils.class,
			SecureFolder.logger);

	/**
	 * Returns the files list that folder contains, and recursively for all
	 * folders inside
	 * 
	 * @param folder
	 *            {@link File} - The folder to look on
	 * @return {@link List}<{@link File}> - The files list
	 * @throws FileNotFoundException
	 */
	public static List<String> getFolderContent(File folder)
			throws FileNotFoundException {
		return getFolderContent(folder, null);
	}

	/**
	 * Returns the files list that folder contains, and recursively for all
	 * folders inside
	 * 
	 * @param folder
	 *            {@link File} - The folder to look on
	 * @param parentPath
	 *            {@link String} - The parent path to consider
	 * 
	 * @return {@link List}<{@link File}> - The files list
	 * @throws FileNotFoundException
	 */
	public static List<String> getFolderContent(File folder, String parentPath)
			throws FileNotFoundException {
		List<String> files = new ArrayList<String>();
		if (!folder.exists()) {
			logger.error("File not found: ['" + folder.getAbsolutePath() + "']");
			throw new FileNotFoundException();
		}
		if (!folder.isDirectory()) {
			logger.write("Path ['" + folder.getAbsolutePath()
					+ "'] is not a directory", ILogger.SEVERITY_WARNING);
			files.add(folder.getName());
			return files;
		}
		logger.write("Getting path content: ['" + folder.getAbsolutePath()
				+ "']", ILogger.SEVERITY_INFO);
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				List<String> content = getFolderContent(file,
						(parentPath == null ? "" : parentPath + File.separator)
								+ folder.getName());
				if (content != null) {
					files.addAll(content);
				}
			}
			String filePath = file.getAbsolutePath();
			int index = filePath.indexOf(folder.getName());
			if (parentPath != null) {
				index = filePath.indexOf(parentPath);
			}
			files.add(filePath.substring(index, filePath.length()));
		}

		return files;
	}

	/**
	 * Check if the given path and file exist and if the path is a directory
	 * 
	 * @param path
	 *            {@link String} - The path to check
	 * @param fileName
	 *            {@link String} - The name of the file to check in the
	 *            directory
	 * @return {@link Boolean boolean} - <code>true</code> if the verification
	 *         is correct, <code>false</code> otherwise
	 */
	private static boolean verify(String path, String fileName) {
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			logger.error("The path '" + path + "' does not exists");
			return false;
		} else if (!pathFile.isDirectory()) {
			logger.error("The path '" + path + "' is not a directory");
			return false;
		}
		File file = new File(path + File.separator + fileName);
		if (!file.exists()) {
			logger.error("The file '" + file.getAbsolutePath()
					+ "' does not exists");
			return false;
		}
		return true;
	}

	/**
	 * Execute a batch command from {@link BatchUtils} looking for file commands
	 * (<code>file.[FILE_COMMAND]</code>)
	 * 
	 * @param fileCommand
	 *            {@link String} - The file command
	 * @param parameters
	 *            {@link String}[] The list of arguments
	 */
	private static void executeBatchCommand(String fileCommand,
			String... parameters) {
		BatchUtils batch = BatchUtils.getInstance();
		String commandKey = "file." + fileCommand;
		logger.write("Creating batch file command: " + fileCommand
				+ " with parameters " + Arrays.asList(parameters),
				ILogger.SEVERITY_INFO);
		BatchCommand command = batch.createCommand(commandKey, parameters);
		command.execute();
	}

	/**
	 * Hide a file using batch command
	 * 
	 * @param path
	 *            {@link String} - Path of the file
	 * @param fileName
	 *            {@link String} - File name
	 * @return {@link Boolean boolean} - <code>true</code> if the file has been
	 *         hide, <code>false</code> otherwise
	 */
	public static boolean hideFile(String path, String fileName) {
		if (!verify(path, fileName)) {
			return false;
		}
		executeBatchCommand("hideFile", path + File.separator, fileName);
		File newFile = new File(path + File.separator, "." + fileName);
		return newFile.exists() && newFile.isHidden();
	}

	/**
	 * Unhide a file using batch command
	 * 
	 * @param path
	 *            {@link String} - Path of the file
	 * @param fileName
	 *            {@link String} - File name
	 * @return {@link Boolean boolean} - <code>true</code> if the file has been
	 *         unhide, <code>false</code> otherwise
	 */
	public static boolean unHideFile(String path, String fileName) {
		if (!verify(path, "." + fileName)) {
			return false;
		}
		executeBatchCommand("unHideFile", path + File.separator, fileName);
		File newFile = new File(path + File.separator, fileName);
		return newFile.exists() && !newFile.isHidden();
	}
}
