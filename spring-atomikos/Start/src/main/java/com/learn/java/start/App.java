package com.learn.java.start;

import javax.annotation.Resource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.java.bo.TestBo;

/**
 * Hello world!
 *   
 */
@RestController
@SpringBootApplication(exclude = { MybatisAutoConfiguration.class })
@ComponentScan(basePackages = { "com.learn.java.bo.impl" })
@ImportResource("applicationContext.xml")
public class App {
	@Resource(name = "springTransactionManager")
	private JtaTransactionManager txManager;

	@Autowired
	private TestBo testBo;

	

	@RequestMapping("/home") String home() {
		return "Hello World!";
	}

	@RequestMapping("/test")
	String test() {
		UserTransaction userTransaction = txManager.getUserTransaction();
		try {
			userTransaction.begin();
			
			
			testBo.branchOneExecute();
//			if(false)
//				throw new RuntimeException();
			
			testBo.branchTwoExecute();
			
			
			try {
				userTransaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} 

		} catch (Exception e) {
			e.printStackTrace();
			try {
				userTransaction.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		} 
		
		
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
