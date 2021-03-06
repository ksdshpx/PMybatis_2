package cn.ksdshpx.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月25日 上午9:45:04
 */
public interface EmployeeMapperDynamicSQL {
	// 查询员工，携带了哪个字段，就把哪个字段带上
	public List<Employee> getEmpsByCondition(Employee employee);

	public List<Employee> getEmpsByConditionTrim(Employee employee);

	public List<Employee> getEmpsByConditionChoose(Employee employee);

	public void updateEmployee(Employee employee);

	// 批量添加
	public void addEmps(@Param("emps") List<Employee> emps);

	public List<Employee> getEmpsTestInnerParameter(Employee employee);
}
