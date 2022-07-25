package com.urzaizcoding.iusteimanserver.domain.user;

import java.util.ArrayList;
import java.util.List;

public enum Role {
	PRE_STUDENT("P",List.of(Permission.UPDATE_SUBSCRIPTION,Permission.DOWNLOAD_FORM,Permission.DOWNLOAD_QUITUS)),
	STUDENT("S",List.of(Permission.DOWNLOAD_QUITUS,Permission.MANAGE_NOTIFICATIONS,Permission.MANAGE_PROFILE)),
	MANAGER("M",STUDENT.getPermissions(),List.of(Permission.MANAGE_PART,Permission.READ_FOLDERS,Permission.WRITE_FOLDERS)),
	ADMINISTRATOR("A",MANAGER.getPermissions(),List.of(Permission.WRITE_COURSE));
	
	private String code;
	List<Permission> permissions;

	private Role(String code,List<Permission> permissions) {
		this.permissions = permissions;
		this.code = code;
	}
	
	

	private Role(String code,List<Permission> former, List<Permission> newOnes) {
		this.code = code;
		this.permissions = new ArrayList<>();
		this.permissions.addAll(former);
		this.permissions.addAll(newOnes);
	}



	public final String getCode() {
		return code;
	}
	
	public final List<Permission> getPermissions(){
		return permissions;
	}
	
	
	
	
}
