package fr.nemolovich.apps.securefolder.command.option;

import java.io.File;
import java.util.Arrays;

import net.lingala.zip4j.exception.ZipException;
import fr.nemolovich.apps.securefolder.zip.ZipUtils;

public class UnzipOption extends CommandOption {

	public UnzipOption() {
		super("unzip", 'u');
	}

	@Override
	public boolean execute() {
		this.logger.setMethodName("execute");
		if (this.parameters != null && this.parameters.size() >= 2) {
			File source = null;
			try {
				source = new File(this.parameters.get(0));
				return ZipUtils.unsecureFolder(source, this.parameters.get(1));
			} catch (ZipException e) {
				this.logger.error("Error while unsecuring folder ['"
						+ source.getAbsolutePath() + "']");
				return false;
			}
		} else {
			this.logger.error("Invalid parameters "
					+ (this.parameters != null ? Arrays
							.toString(this.parameters.toArray()) : "null"));
		}
		this.logger.info("Usage: --unzip|-u <SOURCE_FILE> <PASSWORD>");
		return false;
	}

}
