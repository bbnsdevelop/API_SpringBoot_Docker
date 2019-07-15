package br.com.restWithSpringBoot.mapper;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.github.dozermapper.core.Mapping;

public class PersonVO extends ResourceSupport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Mapping("id")
	private Long key;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String gender;
	
	public PersonVO() {
	}
	
	public PersonVO(Long key, String firstName, String lastName, String address, String gender) {
		this.key = key;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
	}

	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

}
