package cn.ksdshpx.mybatis.dao;

import cn.ksdshpx.mybatis.beans.Department;

/**
 * @author peng.x
 * @date 2019年2月22日 上午11:15:46
 */
public interface DepartmentMapper {
	public Department getDepartmentById(Integer id);
	public Department getDeptAndEmpById(Integer id);
}
