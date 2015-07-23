package com.grab.chinahr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grab.JdbcUtils;
import com.grab.chinahr.entity.HrEntity;
import com.grab.chinahr.entity.SearchKeyEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by bqw on 14-10-1.
 */
public abstract class SuperParser extends Thread implements IParser {
    private SearchKeyEntity searchKeyEntity;

    public static int TIME_OUT = 10000;

    public static String LOGIN_URL = "http://www.chinahr.com/modules/hmcompanyx/?c=login&m=chklogin";

    public static String SEACH_URL = "http://www.chinahr.com/modules/jmw/SocketAjax.php?m=hmresume&f=resume&action=myresume&list_type=search&usetoken=1";

    private Map<String, String> cookies;

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        if (null !=this.cookies){
            this.cookies.putAll(cookies);
        }else {
            this.cookies = cookies;
        }
    }

    public  abstract String getUserName();

    public abstract  String getPassword();

    public SuperParser(SearchKeyEntity searchKeyEntity) throws IOException {
        this.searchKeyEntity = searchKeyEntity;

        setCookies(Jsoup.connect(LOGIN_URL).timeout(TIME_OUT).data("uname", getUserName(), "pass", getPassword()).method(Connection.Method.POST).execute().cookies());

//        setCookies(Jsoup.connect("http://www.chinahr.com/modules/hmresume/index.php?c=preview&m=view_waterfall&ids=ddd2ae84b793025557d50164j|ddd2ae84b793025558d50164j&keyword=%5B%22ios%22%5D").cookies(getCookies()).method(Connection.Method.GET).execute().cookies());


        log(getCookies() + "");
    }

    public SearchKeyEntity getSearchKeyEntity() {
        return searchKeyEntity;
    }

    public void setSearchKeyEntity(SearchKeyEntity searchKeyEntity) {
        this.searchKeyEntity = searchKeyEntity;
    }

    public JSONObject search() throws IOException {
        return JSON.parseObject(Jsoup.connect(SEACH_URL).cookies(getCookies()).data(searchKeyEntity.getSearchKey()).timeout(TIME_OUT).method(Connection.Method.POST).execute().body());
    }


    public void parseJson() throws Exception {
        JSONObject res = search().getJSONObject("res");

        JSONObject pageInfo = res.getJSONObject("page");
        log(pageInfo+"");

        JSONArray resumeList = res.getJSONArray("resumeList");

        if (resumeList.size() == 0){
            return;
        }

        log("#####################key:" + searchKeyEntity.getKey() +  " page:" + searchKeyEntity.getPage());

        for (int i = 0; i < resumeList.size(); i++){
            HrEntity hrEntity = resumeList.getObject(i, HrEntity.class);
            System.out.println(hrEntity);
        }

        if (1 == searchKeyEntity.getPage()){
            searchKeyEntity.setTotal(pageInfo.getIntValue("total"));
            searchKeyEntity.setMax(pageInfo.getIntValue("max"));
        }

        searchKeyEntity.setLastRefresh(new Date());

        JdbcUtils.updateSearchKeyEntity(searchKeyEntity);

        searchKeyEntity.setPage(searchKeyEntity.getPage()+1);
        if (searchKeyEntity.getPage() <= 2){

            searchKeyEntity.getSearchKey().put("page", searchKeyEntity.getPage()+"");
            parseJson();
        }

    }

    public void parseJson(String key) throws Exception {
        parseJson();
    }



    public void log(String log) {
        System.out.println(this.getClass().getName() + ":" + new Date().toLocaleString() + ":" + log);
    }

    @Override
    public void run() {
        try {
            parseJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
