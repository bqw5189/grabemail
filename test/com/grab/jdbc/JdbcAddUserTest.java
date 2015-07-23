package com.grab.jdbc;

import com.grab.IProcessCurriculumVitae;
import com.grab.ProcessCurriculumVitaeFactory;
import com.grab.entity.CurriculumVitae;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class JdbcAddUserTest {
    @Test
    public void testSql(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/db/hr.db");
        dataSource.setDriverClassName("org.h2.Driver");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);




        List<Map<String, Object>> queryList = jdbcTemplate.queryForList("select * from t_email  where type in ('JAVA', 'IOS','ANDROID','WEB','NET','oracle')");

        System.out.println(queryList.size());
        int i = 0;
        for (Map<String, Object> row: queryList){
            if ((row.get("file_name") + "").indexOf("51job") > -1){
                if ((row.get("file_name") + "").endsWith(".html")){
                    IProcessCurriculumVitae processCurriculumVitae = ProcessCurriculumVitaeFactory.instance("@quickmail.51job.com");
                    try {
                        CurriculumVitae curriculumVitae = processCurriculumVitae.process(new File(row.get("file_name") + ""));
                        if (!StringUtils.isEmpty(curriculumVitae.getEmail())) {
                            System.out.println(curriculumVitae.getName() + "->" + curriculumVitae.getEmail());
                            i++;
                        }else{
//                            System.out.println(row.get("file_name"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("users:" + i);


    }
}
