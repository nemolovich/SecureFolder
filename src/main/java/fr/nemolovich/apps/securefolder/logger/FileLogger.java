/**
 * 
 */
package fr.nemolovich.apps.securefolder.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A logger that write in a file
 * 
 * @author nemo
 * 
 */
public class FileLogger extends ConsoleLogger {

	/**
	 * The output file stream to write
	 */
	private OutputStream out;
	/**
	 * The file output
	 */
	private File fileLog;

	/**
	 * Construct the logger for the given file output
	 * @param filePath {@link String} - The output file path
	 */
	public FileLogger(String filePath) {
		this.fileLog = new File(filePath);
		this.fileLog.setWritable(false);
	}

	@Override
	public ILogger init() {
		try {
			this.fileLog.setWritable(true);
			this.out = new FileOutputStream(this.fileLog, true);
			this.fileLog.setWritable(false);
			this.write("*** LOGGER STARTED ***");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public void write(String message) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat(
				"[dd/MM/yyyy HH:mm:ss] | ");
		this.fileLog.setReadable(false);
		this.fileLog.setWritable(true);
		this.out.write(((format.format(Calendar.getInstance().getTime()))
				+ message + "\n").getBytes());
		this.fileLog.setWritable(false);
		this.fileLog.setReadable(true);
	}

	@Override
	public void close() {
		try {
			this.write("*** LOGGER CLOSED ***");
			this.fileLog.setWritable(true);
			this.fileLog.setReadable(true);
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
