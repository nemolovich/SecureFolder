package fr.nemolovich.apps.securefolder.command.option;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import net.lingala.zip4j.exception.ZipException;
import fr.nemolovich.apps.securefolder.batch.exception.BatchException;
import fr.nemolovich.apps.securefolder.zip.IZipConstants;
import fr.nemolovich.apps.securefolder.zip.ZipUtils;

public class ZipOption extends CommandOption {

	public ZipOption() {
		super("zip", 'z');
	}

	@Override
	public boolean execute() throws BatchException {
		this.logger.setMethodName("execute");
		if (this.parameters != null && this.parameters.size() >= 2) {
			File source = null;
			String error;
			try {
				source = new File(this.parameters.poll());
				int state = ZipUtils.secureFolder(source,
						this.parameters.poll());
				if (state == IZipConstants.SUCCESS_STATUS) {
					JOptionPane.showMessageDialog(null, "Folder secured",
							"Success!", JOptionPane.INFORMATION_MESSAGE);
					return true;
				} else {
					error = ZipUtils.descriptions.get(state);
				}
			} catch (FileNotFoundException e) {
				error = "The source ['" + source.getAbsolutePath()
						+ "'] can not be found";
				this.logger.error(error);
			} catch (ZipException e) {
				error = "Error while securing folder ['"
						+ source.getAbsolutePath() + "']";
				this.logger.error(error);
			}
			JOptionPane.showMessageDialog(null, error, "An error occured",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (this.parameters != null && this.parameters.size() == 1) {
			String password = "";
			while (password != null && password.isEmpty()) {
				password = GetPasswordFrame.getpassword();
			}
			if (password != null) {
				this.parameters.add(password);
				return this.execute();
			}
		} else {
			this.logger.error("Invalid parameters "
					+ (this.parameters != null ? Arrays
							.toString(this.parameters.toArray()) : "null"));
		}
		this.logger.info("Usage: --zip|-z <SOURCE_FOLDER> <PASSWORD>");
		return false;
	}

}
