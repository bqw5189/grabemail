package com.grab.impl;

import com.grab.IApplyInfo;
import com.grab.entity.CurriculumVitae;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Job51Id implements IApplyInfo{
    private static final Logger logger = LoggerFactory.getLogger(Job51Id.class);
    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        Elements id = doc.select("html body table tbody tr td table tbody tr td table tbody tr td table tbody tr td table tbody tr td span");

        if (id.size() > 1) {
            curriculumVitae.setId(id.get(1).text());
        } else {
            curriculumVitae.setId(id.text());
        }

        if(id.indexOf("ID:")<0){
            id = doc.select("html body table tbody tr td table tbody tr td table tbody tr td table:eq(0) tbody tr td table tbody tr td p");
            curriculumVitae.setId(id.text());
        }
    }
}
