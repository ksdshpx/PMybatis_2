package cn.ksdshpx.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.ksdshpx.mybatis.beans.Employee;

/**
 * @author peng.x
 * @date 2019年2月14日 下午2:36:03
 */
public class MybatisTest {

	/**
	 * 1.根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
	 * 		有数据源一些运行环境信息
	 * 2.sql映射文件：配置了每一个sql以及sql的封装规则等
	 * 3.将sql映射文件注册在全局配置文件中
	 * 4.写代码
	 * 		1)根据全局配置文件得到sqlSessionFactory
	 * 		2)使用sqlSessionFactory获取到sqlSession对象来执行增删改查，
	 *        一个sqlSession就代表和数据库的一次会话，用完关闭
	 *      3)使用sql的唯一标识来告诉Mybatis来执行哪个sql,sql都是保存在sql映射文件中
	 */
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//2.获取sqlSession实例，能直接执行已经映射的sql语句
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {			
			//statement:sql语句的唯一标识
			//parameter:执行sql语句需要的参数
			Employee employee = sqlSession.selectOne("cn.ksdshpx.mybatis.EmployeeMapper.selectEmp", 1);
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}
	}

}
