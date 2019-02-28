package cn.ksdshpx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.ksdshpx.mybatis.beans.Employee;
import cn.ksdshpx.mybatis.dao.EmployeeMapper;
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
			Employee employee = new Employee(1, "Admin", null, null);
			//测试<if><where>标签
			List<Employee> emps = mapper.getEmpsByCondition(employee);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			//测试<trim>标签
			emps = mapper.getEmpsByConditionTrim(employee);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			//测试<choose>标签
			emps = mapper.getEmpsByConditionChoose(employee);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
			//测试<set>标签
			mapper.updateEmployee(employee);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test02() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Employee> emps = new ArrayList<>();
			emps.add(new Employee(8888,"AA", "1", "AA@163.com"));
			emps.add(new Employee(9999,"BB", "0", "BB@163.com"));
			mapper.addEmps(emps);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test03() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee();
			employee.setLastName("e");
			List<Employee> emps = mapper.getEmpsTestInnerParameter(employee);
			for (Employee emp : emps) {
				System.out.println(emp);
			}
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	  * 两级缓存：
	 *    1)一级缓存(本地缓存)：与数据库同一次会话期间查询到的数据放在
	 *    					   本地缓存中，以后如果需要获取相同的数据，
	 *                        直接从缓存中拿，没必要再去查询数据库
	 *    2)二级缓存(全局缓存)：
	 *   	
	 * 
	 */
	@Test
	public void test04() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee01 = mapper.getEmployeeById(1);
			System.out.println(employee01);
			Employee employee02 =mapper.getEmployeeById(1);
			System.out.println(employee02);
			System.out.println(employee01 == employee02);//true
		} finally {
			sqlSession.close();
		}
	}
}
