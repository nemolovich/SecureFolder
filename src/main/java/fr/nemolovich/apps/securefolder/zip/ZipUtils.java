package fr.nemolovich.apps.securefolder.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jConstants;
import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.file.FileUtils;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;
import fr.nemolovich.apps.securefolder.logger.ILogger;

/**
 * Static class to zip or unzipp files
 * 
 * @author nemo
 * 
 */
public class ZipUtils {
	private static ClassLogger logger = new ClassLogger(ZipUtils.class,
			SecureFolder.logger);

	/**
	 * Secure a folder with a password
	 * 
	 * @param source
	 *            {@link File} - The folder to secure
	 * @param password
	 *            {@link String} - The password
	 * @return {@link Boolean boolean} - <code>true</code> if the folder has
	 *         been secured zipped, <code>false</code> otherwise
	 * @throws FileNotFoundException
	 * @throws ZipException
	 */
	public static boolean secureFolder(File source, String password)
			throws FileNotFoundException, ZipException {
		logger.setMethodName("secureFodler");
		if (source.exists() && !source.isDirectory()) {
			logger.error("The source path is not a directory");
			return false;
		}
		File encrypted = new File(source.getAbsoluteFile() + ".encrypted");
		boolean created = zipFile(source, encrypted);
		if (!created || !encrypted.exists()) {
			logger.error("Can not secure the folder ['"
					+ source.getAbsolutePath() + "']");
			return false;
		}
		FileUtils.hideFile(encrypted.getParent(), encrypted.getName());
		if (encrypted.exists()) {
			logger.error("Error while removing temporary file ['"
					+ encrypted.getAbsolutePath() + "']");
			return false;
		}
		encrypted = new File(source.getParent() + File.separator + "."
				+ source.getName() + ".encrypted");
		if (!encrypted.exists()) {
			logger.error("Temporary file ['" + encrypted.getAbsolutePath()
					+ "'] not found");
			return false;
		}
		File sflock = new File(source.getAbsoluteFile() + ".sflock");
		created = ZipUtils.zipFile(encrypted, sflock, password);
		boolean removed = false;
		logger.write("Removing temporary files", ILogger.SEVERITY_INFO);
		if (encrypted.exists()) {
			if (encrypted.delete()) {
				if (!encrypted.exists()) {
					removed = true;
				}
			}
		}
		if (!removed) {
			logger.warn("Can not remove temporary file ['"
					+ encrypted.getAbsolutePath() + "']");
		}

		return created && sflock.exists();
	}

	/**
	 * Zip a file without password
	 * 
	 * @param source
	 *            {@link File} - The file to add
	 * @param destination
	 *            {@link File} - The destination file
	 * @return {@link Boolean boolean} - <code>true</code> if the file has been
	 *         zipped, <code>false</code> otherwise
	 * @throws FileNotFoundException
	 * @throws ZipException
	 */
	public static boolean zipFile(File source, File destination)
			throws FileNotFoundException, ZipException {
		logger.setMethodName("zipFile");
		return zipFile(source, destination, null);
	}

	/**
	 * Zip a file with a password security
	 * 
	 * @param source
	 *            {@link File} - The file to add
	 * @param destination
	 *            {@link File} - The destination file
	 * @param password
	 *            {@link String} - The password
	 * @return {@link Boolean boolean} - <code>true</code> if the file has been
	 *         zipped, <code>false</code> otherwise
	 * @throws ZipException
	 * @throws FileNotFoundException
	 */
	public static boolean zipFile(File source, File destination, String password)
			throws ZipException, FileNotFoundException {
		logger.setMethodName("zipFile");
		if (!source.exists()) {
			logger.error("The source file to zip does not exist");
			throw new FileNotFoundException();
		}
		if (destination.exists()) {
			logger.error("The destination file already exists");
			return false;
		}

		logger.write("Start to zip files", ILogger.SEVERITY_INFO);

		ZipFile zipFile = new ZipFile(destination);
		zipFile.setFileNameCharset(InternalZipConstants.CHARSET_UTF8);

		ArrayList<String> filesToAdd = new ArrayList<String>(
				FileUtils.getFolderContent(source));

		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);

		if (password != null) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setReadHiddenFiles(false);
			parameters.setPassword(password);
		}
		logger.write(
				"Adding files in zip " + Arrays.toString(filesToAdd.toArray()),
				ILogger.SEVERITY_INFO);
		for (String path : filesToAdd) {
			File file = new File(source.getParent() + File.separator + path);
			logger.write("Adding file: ['" + file.getName() + "']",
					ILogger.SEVERITY_INFO);
			if (file.getParentFile().equals(source) || source.equals(file)) {
				if (file.isDirectory()) {
					zipFile.addFolder(file, parameters);
				} else {
					zipFile.addFile(file, parameters);
				}
			}
		}
		return zipFile.isValidZipFile()
				&& (zipFile.isEncrypted() || password == null);
	}

}
