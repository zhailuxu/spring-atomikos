package com.learn.java.bo.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.learn.java.bo.TestBo;

@Configuration
public class TestBoImpl implements TestBo {
	@Resource(name = "Mysql1JdbcTemplate")
	private JdbcTemplate mysql1JdbcTemplate;

	@Resource(name = "Mysql2JdbcTemplate")
	private JdbcTemplate mysql2JdbcTemplate;

	@Override
	public boolean branchOneExecute() {
		mysql1JdbcTemplate.execute("update account_from set money=money - 50 where id=1");
		return true;
	}

	@Override
	public boolean branchTwoExecute() {
		mysql2JdbcTemplate.execute("update account_to set money= money + 50 where id=1");
		return true;
	}

}
