package cn.ksdshpx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.ksdshpx.mybatis.beans.Department;
import cn.ksdshpx.mybatis.beans.Employee;
import cn.ksdshpx.mybatis.dao.DepartmentMapper;
import cn.ksdshpx.mybatis.dao.EmployeeMapper;
import cn.ksdshpx.mybatis.dao.EmployeeMapperAnnotation;
import cn.ksdshpx.mybatis.dao.EmployeeMapperPlus;

/**
 * @author peng.x
 * @date 2019年2月14日 下午2:36:03
 */
public class MybatisTest {

	/**
	 * 1.根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
	 * 2.sql映射文件：配置了每一个sql以及sql的封装规则等 
	 * 3.将sql映射文件注册在全局配置文件中 
	 * 4.写代码
	 * 	1)根据全局配置文件得到sqlSessionFactory 
	 * 	2)使用sqlSessionFactory获取到sqlSession对象来执行增删改查，
	  *           一个sqlSession就代表和数据库的一次会话，用完关闭，和Connection一样都是非线程安全的，每次使用都应该使用新的对象
	 *  3)使用sql的唯一标识来告诉Mybatis来执行哪个sql,sql都是保存在sql映射文件中
	 */
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.获取sqlSession实例，能直接执行已经映射的sql语句
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// statement:sql语句的唯一标识
			// parameter:执行sql语句需要的参数
			Employee employee = sqlSession.selectOne("cn.ksdshpx.mybatis.dao.EmployeeMapper.selectEmp", 1);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void test01() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmployeeById(1);
			System.out.println(employee);
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
		// 2.通过SqlSessionFactory得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
			Employee employee = mapper.getEmployeeById(1);
			System.out.println(employee);
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
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			// 添加
			Employee employee = new Employee("Jerry", "1", "jerry@163.com");
			mapper.addEmployee(employee);
			System.out.println(employee.getId());
			// 更新
			//Employee employee = new Employee(2,"Jerry", "1", "jerry@sfit.com");			
			//mapper.updateEmployee(employee);
			// 删除
			// mapper.deleteEmployeeById(2);
			//手动提交
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
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
			Employee employee = mapper.getEmployeeByIdAndLastName(1, "Tom");
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test05() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Map<String,Object> map = new HashMap<>();
			map.put("id", 1);
			map.put("lastName", "Tom");
			Employee employee = mapper.getEmployeeByMap(map);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test06() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			List<Employee> emps = mapper.getEmps();
			for (Employee employee : emps) {
				System.out.println(employee);
			}
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test07() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
			System.out.println(map);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test08() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Map<Integer, Employee> map = mapper.getEmpsReturnMap();
			System.out.println(map);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test09() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = mapper.getEmployeeById(1);
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test10() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = mapper.getEmpAndDeptById(1);
			System.out.println(employee);
			System.out.println(employee.getDept());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test11() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = mapper.getEmpAndDeptByIdStep(1);
			System.out.println(employee.getEmail());
			//System.out.println(employee.getDept());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test12() throws IOException {
		// 1.从全局配置文件中获取SqlSessionFactory对象
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.通过SqlSessionFactory得到SqlSession,获取到的sqlSession不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
			Department dept = mapper.getDeptAndEmpById(1);
			System.out.println(dept);
			System.out.println(dept.getEmps());
		} finally {
			sqlSession.close();
		}
	}
}
