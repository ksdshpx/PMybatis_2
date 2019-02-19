package cn.ksdshpx.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月14日 下午3:35:38
 */
public interface EmployeeMapper {
	public Employee getEmployeeById(Integer id);

	public Employee getEmployeeByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);
	
	public Employee getEmployeeByMap(Map<String,Object> map);

	public void addEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public void deleteEmployeeById(Integer id);
	
	public List<Employee> getEmps();
}
