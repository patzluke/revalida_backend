package org.ssglobal.training.codes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(catalog = "users", name = "users")
public class User {
	
	private Integer employeeId;
	private String email;
	private String mobileNumber;
	private transient String password;
	private String userType;
	private String firstName;
	private String middleName;
	private String lastName;
	private String department;
	private LocalDate birthDate;
	private String gender;
	private String position;
	
	public User() {}
	
	public User(Integer employeeId, String email, String mobileNumber, 
				String password, String userType, String firstName, 
				String middleName, String lastName, String department, 
				LocalDate birthDate, String gender, String position) {
		super();
		this.employeeId = employeeId;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.userType = userType;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.department = department;
		this.birthDate = birthDate;
		this.gender = gender;
		this.position = position;
	}

	@XmlElement
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", nullable = false, unique = true)
	public Integer getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	@XmlElement
	@Column(name = "email", nullable = false, length = 70)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlElement
	@Column(name = "mobile_number", nullable = false, length = 20)
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	@XmlElement
	@Column(name = "password", nullable = false, length = 70)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@XmlElement
	@Column(name = "user_type", nullable = false, length = 70)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@XmlElement
	@Column(name = "first_name", nullable = false, length = 70)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@XmlElement
	@Column(name = "middle_name", nullable = false, length = 70)
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@XmlElement
	@Column(name = "last_name", nullable = false, length = 70)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@XmlElement
	@Column(name = "department", nullable = false, length = 70)
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@XmlElement
	@Column(name = "birth_date", nullable = false)
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	@XmlElement
	@Column(name = "gender", nullable = false, length = 10)
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@XmlElement
	@Column(name = "position", nullable = false, length = 70)
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "{%d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s}"
				.formatted(this.employeeId, this.email, this.mobileNumber, this.password, 
						   this.userType, this.firstName, this.middleName, this.lastName,
						   this.department, this.birthDate.toString(), this.gender, this.position);
	}
}
