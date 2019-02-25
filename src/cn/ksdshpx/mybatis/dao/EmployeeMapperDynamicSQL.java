package cn.ksdshpx.mybatis.dao;

import java.util.List;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月25日 上午9:45:04
 */
public interface EmployeeMapperDynamicSQL {
	//查询员工，携带了哪个字段，就把哪个字段带上
	public List<Employee> getEmpsByCondition(Employee employee);
}
