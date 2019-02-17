package cn.ksdshpx.mybatis.beans;

/**
 * @author peng.x
 * @date 2019年2月14日 下午2:23:50
 */
public class Employee {
	private Integer id;
	private String lastName;
	private String gender;
	private String email;

	public Employee() {

	}

	public Employee(String lastName, String gender, String email) {
		super();
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
	}
	
	

	public Employee(Integer id, String lastName, String gender, String email) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", gender=" + gender + ", email=" + email + "]";
	}

}
