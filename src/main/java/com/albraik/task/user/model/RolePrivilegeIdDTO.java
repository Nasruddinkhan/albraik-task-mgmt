package com.albraik.task.user.model;

import java.util.List;

public final class RolePrivilegeIdDTO {

	private Integer id;
	private String name;
	private List<String> privilegeIdList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPrivilegeIdList() {
		return privilegeIdList;
	}

	public void setPrivilegeIdList(List<String> privilegeIdList) {
		this.privilegeIdList = privilegeIdList;
	}

}
