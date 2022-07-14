package com.bvas.insight.utilities;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public String actualrole;

	public String role;

	public String username;

	public AppUser() {
		super();
	}

	public AppUser(String actualrole, String role, String username) {
		super();
		this.actualrole = actualrole;
		this.role = role;
		this.username = username;
	}

	public String getActualrole() {

		return actualrole;
	}

	public String getRole() {

		return role;
	}

	public String getUsername() {

		return username;
	}

	public void setActualrole(String actualrole) {

		this.actualrole = actualrole;
	}

	public void setRole(String role) {

		this.role = role;
	}

	public void setUsername(String username) {

		this.username = username;
	}
}
