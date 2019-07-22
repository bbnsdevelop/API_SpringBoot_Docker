package br.com.restWithSpringBoot.mapper;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;


@JsonPropertyOrder({"id"})
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	
	private String userName;
	
	private String fullname;
	
	private String password;
	
	@JsonIgnore
	private Boolean accountNomExpired;
	
	@JsonIgnore
	private Boolean accountNonLocked;
	
	@JsonIgnore
	private Boolean credentialsNonExpired;
	
	@JsonIgnore
	private Boolean enabled;
	
	private List<PermissionVO> permissions;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAccountNomExpired() {
		return accountNomExpired;
	}

	public void setAccountNomExpired(Boolean accountNomExpired) {
		this.accountNomExpired = accountNomExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<PermissionVO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionVO> permissions) {
		this.permissions = permissions;
	}

	
}
