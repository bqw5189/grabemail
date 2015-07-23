package com.grab.chinahr;

import com.alibaba.fastjson.JSON;
import com.grab.JdbcUtils;
import com.grab.chinahr.entity.SearchKeyEntity;
import com.grab.chinahr.impl.XingudianziSzParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baiqunwei on 15/7/22.
 */
public class ChinaHr {
    public static void main(String[] args) throws Exception {
//        JdbcUtils.update("delete from t_search_key");
//
//        initSearchKey();

        List<SearchKeyEntity> postDatas = searchKeys();

        for (SearchKeyEntity postData:postDatas){
            XingudianziSzParser xingudianziSzParser = new XingudianziSzParser(postData);
            xingudianziSzParser.start();
        }

    }

    private static List<SearchKeyEntity> searchKeys() {
        List<Map<String, Object>> queryResult = JdbcUtils.query("select * from t_search_key");

        List<SearchKeyEntity> result = new ArrayList<SearchKeyEntity>();

        for (Map<String, Object> row : queryResult) {
            SearchKeyEntity searchKeyEntity = new SearchKeyEntity();

            searchKeyEntity.setId((Long) row.get("id"));
            searchKeyEntity.setKey(row.get("key") + "");
            searchKeyEntity.setPage((Integer)row.get("page"));
            searchKeyEntity.setSearchKey(JSON.parseObject(row.get("search_condition") + "", Map.class));

            result.add(searchKeyEntity);
        }

        return result;
    }

    private static void initSearchKey() {
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("flag", "1");
        postData.put("keywordSelect1", "0");
        postData.put("fuzzyWishPlace", "1");
        postData.put("matchLevel", "1,2");
        postData.put("detailLevel", "1,2");
        postData.put("wishJobType", "1");
        postData.put("searcherCount", "0");
        postData.put("used", "0");
        postData.put("allKeyword", "0");
        postData.put("allKeyword2", "0");
        postData.put("keywordSelect", "0");
        postData.put("page", "1");
        postData.put("keyword", "ios");


        //ios 详细/普通
        JdbcUtils.insertIntoSearchKey(postData);

        //android 本科 详细
        postData.put("detailLevel", "1");
        postData.put("keyword", "android");
        postData.put("degreeId", "3");
        JdbcUtils.insertIntoSearchKey(postData);

        //java 本科 一年 详细
        postData.put("keyword", "java");
        postData.put("degreeId", "3");
        postData.put("yearsId", "3");
        postData.put("detailLevel", "1");
        JdbcUtils.insertIntoSearchKey(postData);

    }
}
