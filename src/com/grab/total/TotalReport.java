package com.grab.total;

import com.grab.JdbcUtils;
import com.grab.entity.HrMailEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class TotalReport {
    private static Map<String, Map<String, String>> total = new TreeMap<String, Map<String, String>>();
    private static List<String> TYPES = new ArrayList<String>();

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {

        List<Map<String, Object>> types = JdbcUtils.query("select distinct type from t_email");
        for (Map<String, Object> type: types){
            TYPES.add(type.get("TYPE")+"");
        }

        List<Map<String, Object>> queryResults = JdbcUtils.query("select sent_date, type, count(type) from t_email group by sent_date, type order by sent_date");
        for (Map<String, Object> queryResult:queryResults){
            Map<String, String> totalSent = total.get(queryResult.get("SENT_DATE"));
            if (null == totalSent){
                totalSent = new HashMap<String, String>();
                total.put(queryResult.get("SENT_DATE")+"", totalSent);
            }

            totalSent.put(queryResult.get("TYPE")+"", queryResult.get("COUNT(TYPE)")+"");
        }

        StringBuffer titleStr = new StringBuffer("日期\t");

        for (String type: TYPES){
            titleStr.append(type).append("\t");
        }

        System.out.println(titleStr.toString());

        for (Map.Entry<String, Map<String, String>> totalSent:total.entrySet()){
            StringBuffer rowStr = new StringBuffer(String.format("%s\t", totalSent.getKey()));
            for (String type: TYPES){
                rowStr.append(null == totalSent.getValue().get(type)?"0":totalSent.getValue().get(type)).append("\t");
            }

            System.out.println(rowStr.toString());
        }

    }


}
