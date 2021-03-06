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
	  * 两级缓存：(查出的数据都会默认先放到一级缓存，只有会话提交或者关闭，一级缓存中的数据才会转移到二级缓存中)
	 *    1)一级缓存(本地缓存)：SqlSession级别的缓存，一直开启的，与数据库同一次会话期间查询到的数据放在
	 *    					   本地缓存中，以后如果需要获取相同的数据，
	 *                        直接从缓存中拿，没必要再去查询数据库
	 *                        
	 *            一级缓存的失效情况(没有使用到一级缓存的情况，效果就是需要再向数据库发送sql)
	 *            1.SqlSession不同
	 *            2.SqlSession相同，查询条件不同(当前一级缓存中还没有这个数据)
	 *            3.SqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
	 *            4.SqlSession相同，手动清除了一级缓存(缓存清空)
	 *            
	 *    2)二级缓存(全局缓存)：基于namespace级别的缓存，一个namespace可以对应一个二级缓存
	 *        工作机制:
	 *        1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中
	 *        2、如果会话关闭，那一级缓存中的数据会被保存到二级缓存中，新的会话查询信息，就会参照二级缓存中的内容
	 *        3、不同namespace查出的数据会被放在自己对应的缓存(map)中
	 *          sqlSession===EmployeeMapper==>Employee
	 *                    ===DepartmentMapper==>Department
	 *           
	  *        使用步骤：
	  *        1）开启全局二级缓存配置
	  *           <setting name="cacheEnabled" value="true"/>
	  *        2）去mapper.xml中配置使用二级缓存
	 *         3）Pojo需要实现序列化接口
	 *         
	  *    和缓存有关的设置和属性
	  *        1）cacheEnabled:设置为false只会关闭二级缓存，对一级缓存不生效
	  *        2）每个select标签都有useCache属性，默认为true，设置为false不使用二级缓存
	  *        3）每个增删改标签都有flushCache属性，默认为true，表示增删改操作后会清除缓存（一级缓存和二级缓存都会被清空）
	  *        		查询标签默认也有flushCache属性，但默认为false
	  *        4）sqlSession.clearCache()只是清除一级缓存，不会清除二级缓存
	  *        5）localCacheScope:本地缓存作用域，默认为SESSION,当前会话的所有数据都保存在会话缓存（一级缓存）中
	  *               	设置为STATEMENT则为禁用掉一级缓存（一般不会设置）
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
			//1.SqlSession不同
			//sqlSession = sqlSessionFactory.openSession();
			//mapper = sqlSession.getMapper(EmployeeMapper.class);
			//2.SqlSession相同，查询条件不同
			//Employee employee02 =mapper.getEmployeeById(3);
			//3.SqlSession相同，两次查询之间执行了增删改操作
			//mapper.addEmployee(new Employee(null,"testCache","1","dfs"));
			//System.out.println("数据添加成功！");
			//4.SqlSession相同，手动清除了一级缓存(缓存清空)
			sqlSession.clearCache();
			Employee employee02 =mapper.getEmployeeById(1);
			System.out.println(employee02);
			System.out.println(employee01 == employee02);//true
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
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		try {
			// 3.得到接口的代理类对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee01 = mapper.getEmployeeById(1);
			System.out.println(employee01);
			sqlSession.close();
			EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
			Employee employee02 = mapper2.getEmployeeById(1);
			System.out.println(employee02);
			sqlSession2.close();
		} finally {
			
		}
	}
}
