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
public class Job51ApplyInfo implements IApplyInfo{
    private static final Logger logger = LoggerFactory.getLogger(Job51ApplyInfo.class);
    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        Elements applyInfoTd = doc.select("table").get(0).select("tbody tr td table tbody tr td");
        if (StringUtils.isEmpty(applyInfoTd.text())) {
            applyInfoTd = doc.select("table").get(1).select("tbody tr td table tbody tr td");
        }

        logger.debug("applyInfoTd:{}", applyInfoTd.text());

        curriculumVitae.setApplyForPost(applyInfoTd.get(1).text());
        curriculumVitae.setApplyForCompany(applyInfoTd.get(3).text());
        curriculumVitae.setDeliverDate(applyInfoTd.get(5).text());
        curriculumVitae.setMatchPercentage(applyInfoTd.get(7).text());
    }
}
