package fr.nemolovich.apps.securefolder.command;

import java.io.File;
import java.util.Arrays;

import fr.nemolovich.apps.securefolder.command.option.CommandOption;
import fr.nemolovich.apps.securefolder.zip.ZipUtils;

public class SecureBackOption extends CommandOption {

	public SecureBackOption() {
		super("rezip", 'r');
	}

	@Override
	public boolean execute() {
		this.logger.setMethodName("execute");
		if (this.parameters != null && this.parameters.size() >= 1) {
			File source = new File(this.parameters.get(0));
			return ZipUtils.secureBackFolder(source);
		} else {
			this.logger.error("Invalid parameters "
					+ (this.parameters != null ? Arrays
							.toString(this.parameters.toArray()) : "null"));
		}
		this.logger.info("Usage: --rezip|-r <SOURCE_FILE>");
		return false;
	}

}
