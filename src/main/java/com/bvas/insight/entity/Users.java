package com.bvas.insight.entity;

// ~--- non-JDK imports --------------------------------------------------------

// ~--- JDK imports ------------------------------------------------------------
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "users")
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "actualrole")
	public String actualrole;

	@Column(name = "password")
	public String password;

	@Column(name = "role")
	public String role;

	@Id
	@Column(name = "username")
	public String username;

	public Users() {
		super();
	}

	public Users(String username, String password, String role, String actualrole) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.actualrole = actualrole;
	}

	public String getActualrole() {

		return actualrole;
	}

	public String getPassword() {

		return password;
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

	public void setPassword(String password) {

		this.password = password;
	}

	public void setRole(String role) {

		this.role = role;
	}

	public void setUsername(String username) {

		this.username = username;
	}
}
