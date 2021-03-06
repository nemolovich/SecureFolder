package fr.nemolovich.apps.securefolder.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.exception.ZipExceptionConstants;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jConstants;
import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.batch.exception.BatchException;
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

	public static final String ENCRYPTED_EXTENSION = "encrypted";
	public static Map<Integer, String> descriptions;

	public static void initConstants() {
		Map<Integer, String> descriptions = new ConcurrentHashMap<Integer, String>();
		descriptions.put(IZipConstants.SUCCESS_STATUS,
				IZipConstants.SUCCESS_STATUS_DESC);
		descriptions.put(IZipConstants.WRONG_FILE_STATUS,
				IZipConstants.WRONG_FILE_STATUS_DESC);
		descriptions.put(IZipConstants.WRONG_PATH_STATUS,
				IZipConstants.WRONG_PATH_STATUS_DESC);
		descriptions.put(IZipConstants.WRONG_PASSWORD_STATUS,
				IZipConstants.WRONG_PASSWORD_STATUS_DESC);
		descriptions.put(IZipConstants.ERROR_STATUS,
				IZipConstants.ERROR_STATUS_DESC);
	}

	/**
	 * Secure a folder with a password
	 * 
	 * @param source
	 *            {@link File} - The folder to secure
	 * @param password
	 *            {@link String} - The password
	 * @throws FileNotFoundException
	 * @throws ZipException
	 * @throws BatchException
	 */
	public static int secureFolder(File source, String password)
			throws FileNotFoundException, ZipException, BatchException {
		logger.setMethodName("secureFodler");
		if (source.exists() && !source.isDirectory()) {
			logger.error("The source path is not a directory");
			return IZipConstants.WRONG_PATH_STATUS;
		}
		File encrypted = new File(source.getAbsolutePath().concat(".")
				.concat(ENCRYPTED_EXTENSION));
		boolean created = zipFile(source, encrypted);
		if (!created || !encrypted.exists()) {
			logger.error("Can not secure the folder ['"
					+ source.getAbsolutePath() + "']");
			return IZipConstants.WRONG_PATH_STATUS;
		}
		FileUtils.hideFile(encrypted.getParent(), encrypted.getName());
		if (encrypted.exists()) {
			logger.error("Error while removing temporary file ['"
					+ encrypted.getAbsolutePath() + "']");
			return IZipConstants.ERROR_STATUS;
		}
		encrypted = new File(source.getParent().concat(
				File.separator.concat(".").concat(
						source.getName().concat(".")
								.concat(ENCRYPTED_EXTENSION))));
		if (!encrypted.exists()) {
			logger.error("Temporary file ['" + encrypted.getAbsolutePath()
					+ "'] not found");
			return IZipConstants.ERROR_STATUS;
		}
		File sflockFile = new File(source.getAbsolutePath().concat(".")
				.concat(SecureFolder.EXTENSION_LOCK));
		String cryptedPass = password;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(cryptedPass.getBytes(/* "UTF-8" */));
			byte[] md5 = md.digest();
			cryptedPass = new String(md5/* ,"UTF-8" */);
		} catch (NoSuchAlgorithmException ex) {
			cryptedPass = "nopass";
		}
		created = ZipUtils.zipFile(encrypted, sflockFile, cryptedPass);
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

		removed = FileUtils.removeFolder(source);

		if (removed && created && sflockFile.exists()) {
			return IZipConstants.SUCCESS_STATUS;
		}
		return IZipConstants.ERROR_STATUS;
	}

	public static int unsecureFolder(File sflockFile, String password)
			throws ZipException, BatchException {
		logger.setMethodName("unsecureFodler");
		if (!sflockFile.exists() || !sflockFile.isFile()) {
			logger.error("File ['" + sflockFile.getAbsolutePath()
					+ "'] is not a valid file");
			return IZipConstants.WRONG_FILE_STATUS;
		}
		File destination = new File(sflockFile.getParent());
		String cryptedPass = password;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(cryptedPass.getBytes(/* "UTF-8" */));
			byte[] md5 = md.digest();
			cryptedPass = new String(md5/* ,"UTF-8" */);
		} catch (NoSuchAlgorithmException ex) {
			cryptedPass = "nopass";
		}
		boolean unzipped = unzipFile(sflockFile, destination, cryptedPass);
		if (!unzipped) {
			return IZipConstants.WRONG_PASSWORD_STATUS;
		}
		File tempFile = new File(
				sflockFile
						.getParent()
						.concat(File.separator)
						.concat(".")
						.concat(sflockFile
								.getName()
								.substring(
										0,
										sflockFile.getName().lastIndexOf(".") + 1)
								.concat(ZipUtils.ENCRYPTED_EXTENSION)));
		FileUtils.hideFile(tempFile.getParent(), tempFile.getName());
		File destFolder = new File(sflockFile
				.getParent()
				.concat(File.separator)
				.concat(sflockFile.getName().substring(0,
						sflockFile.getName().lastIndexOf("."))));
		unzipped = unzipFile(tempFile, destFolder);
		if (unzipped) {
			unzipped = tempFile.delete();
		}
		if (unzipped) {
			return IZipConstants.SUCCESS_STATUS;
		}
		return IZipConstants.ERROR_STATUS;
	}

	public static boolean secureBackFolder(File sflockFile) {
		logger.setMethodName("secureBackFolder");
		if (!sflockFile.exists() || !sflockFile.isFile()) {
			logger.error("File ['" + sflockFile.getAbsolutePath()
					+ "'] is not a valid file");
			return false;
		}
		String folderName = sflockFile.getAbsolutePath();
		folderName = folderName.substring(0, folderName.lastIndexOf("."));
		File sourceFolder = new File(folderName);
		logger.write("Removing folder ['" + sourceFolder.getAbsolutePath()
				+ "']", ILogger.SEVERITY_INFO);
		return FileUtils.removeFolder(sourceFolder);
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

	public static boolean unzipFile(File source, File destination)
			throws ZipException {
		return unzipFile(source, destination, null);
	}

	public static boolean unzipFile(File source, File destination,
			String password) throws ZipException {
		logger.setMethodName("unzipFile");
		if (!source.exists()) {
			logger.error("The file to unzip does not exist");
			return false;
		}
		ZipFile zipFile = new ZipFile(source);
		if (zipFile.isEncrypted() && password != null) {
			logger.write("Configuring password for encrypted zipped file",
					ILogger.SEVERITY_INFO);
			zipFile.setPassword(password);
		}
		try {
			logger.write("Extracting files from ['" + source.getAbsolutePath()
					+ "']...", ILogger.SEVERITY_INFO);
			zipFile.extractAll(destination.getAbsolutePath());
		} catch (ZipException e) {
			if (e.getCode() == ZipExceptionConstants.WRONG_PASSWORD) {
				logger.error("Wrong password");
			}
			throw e;
		}
		logger.write("Files extracted from ['" + source.getAbsolutePath()
				+ "']", ILogger.SEVERITY_INFO);
		return true;

	}

}
