package com.urzaizcoding.iusteimanserver.domain.user;

public enum Permission {
	WRITE_COURSE("Write course permission"),
	DOWNLOAD_QUITUS("Download quitus"),
	MANAGE_NOTIFICATIONS("Manage notifications, read, delete notifications"),
	READ_FOLDERS("Read registrations folders"),
	WRITE_FOLDERS("Write registration folder"),
	DELETE_FOLDERS("Delete registration folders"),
	MANAGE_PART("Manage a part can upload, download, archivate a part"),
	MANAGE_PROFILE("Manage own user profile"),
	MANAGE_ACCOUNTS("manage user accounts"),
	UPDATE_SUBSCRIPTION("update subscription"),
	DOWNLOAD_FORM("download form")
	;
	private final String description;
	
	private Permission(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
