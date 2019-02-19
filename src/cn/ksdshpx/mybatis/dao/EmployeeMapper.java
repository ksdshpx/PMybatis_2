package cn.ksdshpx.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
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
	
	//返回List
	public List<Employee> getEmps();
	
	//返回一条记录的map,key为列名，值为列的值
	public Map<String,Object> getEmpByIdReturnMap(Integer id);
	
	//多条记录封装map
	//告诉MyBatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("id")
	public Map<Integer,Employee> getEmpsReturnMap();
}
