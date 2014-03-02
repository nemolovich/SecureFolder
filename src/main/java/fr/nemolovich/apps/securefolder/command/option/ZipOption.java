package fr.nemolovich.apps.securefolder.command.option;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import net.lingala.zip4j.exception.ZipException;
import fr.nemolovich.apps.securefolder.zip.ZipUtils;

public class ZipOption extends CommandOption {

	public ZipOption() {
		super("zip", 'z');
	}

	@Override
	public boolean execute() {
		this.logger.setMethodName("execute");
		if (this.parameters != null && this.parameters.size() >= 2) {
			File source = null;
			try {
				source = new File(this.parameters.get(0));
				return ZipUtils.secureFolder(source, this.parameters.get(1));
			} catch (FileNotFoundException e) {
				this.logger.error("The source ['" + source.getAbsolutePath()
						+ "'] can not be found");
			} catch (ZipException e) {
				this.logger.error("Error while securing folder ['"
						+ source.getAbsolutePath() + "']");
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
