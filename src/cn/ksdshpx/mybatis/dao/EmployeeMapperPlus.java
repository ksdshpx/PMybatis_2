package cn.ksdshpx.mybatis.dao;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月19日 上午10:10:36
 */
public interface EmployeeMapperPlus {
	public Employee getEmployeeById(Integer id);
}
