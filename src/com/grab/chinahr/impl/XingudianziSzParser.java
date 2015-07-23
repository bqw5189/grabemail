package com.grab.chinahr.impl;

import com.grab.chinahr.SuperParser;
import com.grab.chinahr.entity.SearchKeyEntity;

import java.io.IOException;

/**
 * Created by bqw on 14-10-1.
 */
public class XingudianziSzParser extends SuperParser {

    public XingudianziSzParser(SearchKeyEntity searchKeyEntity) throws IOException {
        super(searchKeyEntity);
    }

    @Override
    public String getUserName() {
        return "xingudianzi";
    }

    @Override
    public String getPassword() {
        return "qgdaosheng513";
    }
}
