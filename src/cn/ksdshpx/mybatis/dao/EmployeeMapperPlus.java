package cn.ksdshpx.mybatis.dao;

import java.util.List;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月19日 上午10:10:36
 */
public interface EmployeeMapperPlus {
	public Employee getEmployeeById(Integer id);
	public Employee getEmpAndDeptById(Integer id);
	public Employee getEmpAndDeptByIdStep(Integer id);
	public List<Employee> getEmpsByDeptId(Integer deptId);
}
