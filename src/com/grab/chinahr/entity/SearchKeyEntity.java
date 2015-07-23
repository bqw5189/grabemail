package com.grab.chinahr.entity;

import java.util.Date;
import java.util.Map;

/**
 * Created by baiqunwei on 15/7/23.
 */
public class SearchKeyEntity {
    private Long id;
    private String key;
    private int page;
    private int max;
    private int total;
    private int recordCount;
    private Date lastRefresh;

    private Map<String, String> searchKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(Map<String, String> searchKey) {
        this.searchKey = searchKey;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
}
