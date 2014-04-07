package fr.nemolovich.apps.securefolder.zip;


public interface IZipConstants {
	static final int SUCCESS_STATUS = 0;
	static final String SUCCESS_STATUS_DESC = "Success!";
	static final int WRONG_FILE_STATUS = 1;
	static final String WRONG_FILE_STATUS_DESC = "File is incorrect! See log for more informations";
	static final int WRONG_PATH_STATUS = 2;
	static final String WRONG_PATH_STATUS_DESC = "Folder path is incorrect! See log for more informations";
	static final int WRONG_PASSWORD_STATUS = 3;
	static final String WRONG_PASSWORD_STATUS_DESC = "Password seems to be wrong! See log for more informations";
	static final int ERROR_STATUS = 4;
	static final String ERROR_STATUS_DESC = "An error occured! See log for more informations";
}
