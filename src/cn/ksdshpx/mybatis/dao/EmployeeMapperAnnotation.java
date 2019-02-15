package cn.ksdshpx.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月14日 下午3:35:38
 */
public interface EmployeeMapperAnnotation {
	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmployeeById(Integer id);
}
