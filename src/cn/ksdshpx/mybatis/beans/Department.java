package cn.ksdshpx.mybatis.beans;

/**
 * @author peng.x
 * @date 2019年2月19日 上午10:31:44
 */
public class Department {
	private Integer id;
	private String deptName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", deptName=" + deptName + "]";
	}

}
