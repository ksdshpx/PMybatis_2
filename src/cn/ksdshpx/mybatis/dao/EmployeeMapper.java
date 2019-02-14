package cn.ksdshpx.mybatis.dao;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月14日 下午3:35:38
 */
public interface EmployeeMapper {
	public Employee getEmployeeById(Integer id);
}
