package com.ge.cab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"superUserId",
	"sso"
})

@Entity
@Table(name="superUser")
public class SuperUser {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty("superUserId")
	private long superUserId;
	@Column(name="sso")
	@JsonProperty("sso")
	private long sso;
	
	@JsonProperty("superUserId")
	public long getSuperUserId() {
		return superUserId;
	}
	@JsonProperty("superUserId")
	public void setSuperUserId(long superUserId) {
		this.superUserId = superUserId;
	}
	@JsonProperty("sso")
	public long getSso() {
		return sso;
	}
	@JsonProperty("sso")
	public void setSso(long sso) {
		this.sso = sso;
	}
}