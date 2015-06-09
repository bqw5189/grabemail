package com.grab.impl;

import com.grab.IAddressInfo;
import com.grab.entity.CurriculumVitae;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Job51AddressInfoForContent implements IAddressInfo {
    private static final Logger logger = LoggerFactory.getLogger(Job51AddressInfoForContent.class);

    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {
        Elements baseInfoTd = doc.select("html body table tbody tr td div#content div.v3 div.v3_middle table tbody tr td table tbody tr td[height=20]");

        logger.debug("baseInfoTd:{}", baseInfoTd.html());

        if (baseInfoTd.size() >= 9) {
            curriculumVitae.setPlaceOfResidence(baseInfoTd.get(1).text());
            curriculumVitae.setResidenceCards(baseInfoTd.get(3).text());
            curriculumVitae.setAddress(baseInfoTd.get(5).text());
            curriculumVitae.setPhone(baseInfoTd.get(7).text());
            curriculumVitae.setEmail(baseInfoTd.get(9).text());
        } else {
            curriculumVitae.setPlaceOfResidence(baseInfoTd.get(1).text());
            curriculumVitae.setPhone(baseInfoTd.get(5).text());
            curriculumVitae.setEmail(baseInfoTd.get(7).text());
        }

    }
}
