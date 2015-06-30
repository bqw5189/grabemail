package com.grab.jdbc;

import org.apache.commons.dbcp.BasicDataSource;
import org.crazycake.jdbcTemplateTool.JdbcTemplateTool;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class JdbcTest {
    @Test
    public void testSql(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/db/hr.db");
        dataSource.setDriverClassName("org.h2.Driver");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);




        jdbcTemplate.update("insert into t_email(name, title, from_address, to_address, sent_date) values (?,?,?,?,?)", "白群伟","java 白群伟", "baiqw@daoshenggroup.com", "baiqw@daoshenggroup.com", new Date());

        System.out.println(jdbcTemplate.queryForList("select * from t_email").size());


    }
}
