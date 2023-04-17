package org.ssglobal.training.codes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(catalog = "users", name = "user_tokens")
public class UserToken {

	private Integer userId;
	private String token;
	private User user;
	
	public UserToken() { }
	
	public UserToken(Integer userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}

	@XmlElement
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@XmlElement
	@Column(name = "token", nullable = false, length = 50)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@OneToOne
	@JoinColumn(name = "user_id", updatable = false, insertable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
