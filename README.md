SpringBoot + Npm + Webpack + VUE

SpringBoot工程搭建

1.入口文件
package fluently.myzone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan。
分开解释@Configuration,@EnableAutoConfiguration,@ComponentScan。
1、@Configuration：提到@Configuration就要提到他的搭档@Bean。使用这两个注解就可以创建一个简单的spring配置类，可以用来替代相应的xml配置文件。
   @Configuration的注解类标识这个类可以使用Spring IoC容器作为bean定义的来源。@Bean注解告诉Spring，一个带有@Bean的注解方法将返回一个对象，
     该对象应该被注册为在Spring应用程序上下文中的bean。
2、@EnableAutoConfiguration：能够自动配置spring的上下文，试图猜测和配置你想要的bean类，通常会自动根据你的类路径和你的bean定义自动配置。
3、@ComponentScan：会自动扫描指定包下的全部标有@Component的类，并注册成bean，当然包括@Component下的子注解@Service,@Repository,@Controller。
*/

//MyBatis 支持
@MapperScan("fluently.myzone.dao")
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource({"classpath:application.properties"})
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

2.控制器层

package fluently.myzone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fluently.myzone.model.UserVO;
import fluently.myzone.service.IOperationUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IOperationUserService iOperationUserService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void addUser(UserVO user) {
		iOperationUserService.addUser(user);
	}
	
	@RequestMapping(value = "/queryAllUser", method = RequestMethod.GET)
	public List<UserVO> queryAllUser() {
		return iOperationUserService.queryAllUser();
	}
}

3.Service层

package fluently.myzone.service;

import java.util.List;

import fluently.myzone.model.UserVO;

public interface IOperationUserService {
	
	public void addUser(UserVO user);
	
	public List<UserVO> queryAllUser();
}


package fluently.myzone.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fluently.myzone.dao.IOperationUserDao;
import fluently.myzone.model.UserVO;
import fluently.myzone.service.IOperationUserService;

@Service
public class OperationUserServiceImpl implements IOperationUserService {

	@Resource
	private IOperationUserDao addUserDao;
	
	@Override
	public void addUser(UserVO user) {
		addUserDao.addUser(user);
	}

	@Override
	public List<UserVO> queryAllUser() {
		return addUserDao.queryAllUser();
	}

}
	
4.Dao层

package fluently.myzone.dao;

import java.util.List;

import fluently.myzone.model.UserVO;

public interface IOperationUserDao {

	public void addUser(UserVO user);

	public List<UserVO> queryAllUser();
}

5.Model层

package fluently.myzone.model;

public class UserVO {
	
	private String id;
	private String name;
	private String birthday;
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

6.application.properties

server.port=8090

spring.datasource.url=jdbc:mysql://localhost:3306/myapp?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=xxxxxx
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource

#为实体对象所在的包，跟数据库表一一对应
mybatis.typeAliasesPackage=fluently.myzone.model
#mapper文件的位置
mybatis.mapperLocations=classpath*:mybatis/*.xml

7.xml配置

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="fluently.myzone.dao.IOperationUserDao">

	<insert id="addUser" parameterType="fluently.myzone.model.UserVO">
		INSERT INTO User (id,name,birthday,address) VALUES
		(#{id},#{name},#{birthday},#{address});
	</insert>
	
	<select id="queryAllUser" resultType="fluently.myzone.model.UserVO">
		SELECT id,name,birthday,address FROM User
	</select>
	
</mapper>

8.pom

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>fluently</groupId>
	<artifactId>myzone</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>myzone</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<!-- Spring默认使用jdk1.6，添加属性使用1.8 -->
		<java.version>1.8</java.version>
	</properties>

	<!-- Inherit defaults from Spring Boot -->
	<!-- spring-boot-starter-parent包含了大量配置好的依赖管理 -->
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.3.1.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<!-- Add typical dependencies for a web application -->
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.0.0</version>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.39</version>
		</dependency>
		<dependency>
			<groupId>com.zaxxer</groupId>
			<artifactId>HikariCP-java6</artifactId>
			<version>2.3.8</version>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>fastjson</artifactId>
			<version>1.2.8</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<dependencies>
					<dependency>
						<groupId>org.springframework</groupId>
						<artifactId>springloaded</artifactId>
						<version>1.2.5.RELEASE</version>
					</dependency>
				</dependencies>
			</plugin>
		</plugins>
	</build>
</project>
