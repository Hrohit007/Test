package com.ge.cab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"sso",
	"firstname",
	"lastname",
	"contactNo",
	"password",
	"emailid",
	"isPasswordUpdated",
	"applicationName"
})					
@Entity
@Table(name = "users")
public class User {
	@Id
	@JsonProperty("sso")
	private long sso;
	@Column(name="firstname")
	@JsonProperty("firstname")
	private String firstname;
	@Column(name="lastname")
	@JsonProperty("lastname")
	private String lastname;
	@Column(name="contactNo")
	@JsonProperty("contactNo")
	private long contactNo;
	@Column(name="emailid")
	@JsonProperty("emailid")
	private String emailid;
	@Column(name="isPasswordUpdated",columnDefinition= "boolean default 'false'")
	@JsonProperty("isPasswordUpdated")
	private boolean isPasswordUpdated;
	@Column(name="applicationName")
	@JsonProperty("applicationName")
	private String applicationName;
	@Column(name="password")
	private String password;

	@JsonProperty("sso")
	public long getSso() {
		return sso;
	}

	@JsonProperty("sso")
	public void setSso(Long sso) {
		this.sso = sso;
	}

	@JsonProperty("firstname")
	public String getFirstName() {
		return firstname;
	}

	@JsonProperty("firstname")
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	@JsonProperty("lastname")
	public String getLastName() {
		return lastname;
	}
	@JsonProperty("lastname")
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	@JsonProperty("contactNo")
	public long getContactNo() {
		return contactNo;
	}
	@JsonProperty("contactNo")
	public void setContactNo(String contactNo) {
		Long cont = Long.valueOf(contactNo);
		this.contactNo = cont;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonProperty("emailid")
	public String getEmailId() {
		return emailid;
	}
	@JsonProperty("emailid")
	public void setEmailId(String emailid) {
		this.emailid = emailid;
	}
	
	@JsonProperty("isPasswordUpdated")
	public boolean getIsPasswordUpdated() {
		return isPasswordUpdated;
	}
	@JsonProperty("isPasswordUpdated")
	public void setIsPasswordUpdated(boolean isUpdated) {
		this.isPasswordUpdated = isUpdated;
	}
	@JsonProperty("applicationName")
	public String getApplicationName() {
		return lastname;
	}
	@JsonProperty("applicationName")
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
}

