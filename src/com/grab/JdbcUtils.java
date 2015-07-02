package com.grab;

import com.grab.entity.HrMailEntity;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * Created by baiqunwei on 15/6/29.
 */
public class JdbcUtils {
    final static BasicDataSource dataSource = new BasicDataSource();
    static {
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/db/hr.db");
        dataSource.setDriverClassName("org.h2.Driver");
    }

    final static JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    public static void insertIntoHrEmail(HrMailEntity hrMailEntity){
        if (jdbcTemplate.queryForList("select * from t_email where title=?", hrMailEntity.getTitle()).size()==0) {
            jdbcTemplate.update("insert into t_email(name, title, source, type, sent_date,file_name) values (?,?,?,?,?,?)", hrMailEntity.getName(),
                    hrMailEntity.getTitle(), hrMailEntity.getSource(), hrMailEntity.getType(), hrMailEntity.getSentDate(), hrMailEntity.getFileName());
        }
    }

    public static boolean isExist(String title){
        return jdbcTemplate.queryForList("select * from t_email where title=?", title).size()!=0;
    }
}
