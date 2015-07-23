package com.grab;

import com.alibaba.fastjson.JSON;
import com.grab.chinahr.entity.SearchKeyEntity;
import com.grab.entity.HrMailEntity;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public static List<Map<String, Object>> query(String query) {
        return jdbcTemplate.queryForList(query);
    }

    public static void insertIntoSearchKey(Map<String, String> postData) {
        jdbcTemplate.update("insert into t_search_key(key, search_condition,page) values (?,?,?)",postData.get("keyword"), JSON.toJSONString(postData),1);
    }

    public static void update(String query) {
        jdbcTemplate.update(query);
    }

    public static void updateSearchKeyEntity(SearchKeyEntity searchKeyEntity) {
        jdbcTemplate.update("update t_search_key set total=?, max=?,last_refresh=?,page=? where id=?", searchKeyEntity.getTotal(), searchKeyEntity.getMax(), searchKeyEntity.getLastRefresh(),searchKeyEntity.getPage(), searchKeyEntity.getId());
    }
}
