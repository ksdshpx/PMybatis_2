package cn.ksdshpx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.ksdshpx.mybatis.beans.Employee;
import cn.ksdshpx.mybatis.dao.EmployeeMapperDynamicSQL;

/**
 * @author peng.x
 * @date 2019年2月25日 上午10:09:42
 */
public class MybatisTestForDynamicSQL {
	@Test
	public void test01() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null, "Tom", null, null);
			List<Employee> emps = mapper.getEmpsByCondition(employee);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			emps = mapper.getEmpsByConditionTrim(employee);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
		} finally {
			sqlSession.close();
		}
	}
}
