package com.grab.impl;

import com.grab.IProcessCurriculumVitae;
import com.grab.IProcessDocument;
import com.grab.entity.CurriculumVitae;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by baiqunwei on 15/6/5.
 */
public class Job51ProcessCurriculumVitae implements IProcessCurriculumVitae {
    private static final String SOURCE_NAME = "51job.com";
    private static final Logger logger = LoggerFactory.getLogger(Job51ProcessCurriculumVitae.class);

    @Override
    public CurriculumVitae process(File htmlFile) throws Exception {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setSource(SOURCE_NAME);

        String curriculumVitaeFile = htmlFile.getName();

        logger.debug("curriculumVitaeFile:{}", curriculumVitaeFile);

        if (!htmlFile.exists()) {
            logger.error("{} not exists!", htmlFile.getAbsolutePath());
            return curriculumVitae;
        }

        Document doc = Jsoup.parse(htmlFile, "gbk");

        logger.debug("curriculumVitaeFile:{}, html:{}", curriculumVitaeFile, doc.text());

        IProcessDocument applyInfo = new Job51ApplyInfo();
        applyInfo.process(curriculumVitae, doc);

        IProcessDocument job51Id = new Job51Id();
        job51Id.process(curriculumVitae, doc);

        if (doc.select("#p_content").size() > 0) {
            IProcessDocument job51ForPContent = new Job51ForPContent();
            job51ForPContent.process(curriculumVitae, doc);
        } else if (doc.select("#content").size() > 0) {
            IProcessDocument job51ForContent = new Job51ForContent();
            job51ForContent.process(curriculumVitae, doc);
        } else {
            IProcessDocument job51PersonalInfo = new Job51PersonalInfo();
            job51PersonalInfo.process(curriculumVitae, doc);

            IProcessDocument job51AddressInfo = new Job51AddressInfo();
            job51AddressInfo.process(curriculumVitae, doc);

            IProcessDocument job51SchoolInfo = new Job51SchoolInfo();
            job51SchoolInfo.process(curriculumVitae, doc);
        }

        return curriculumVitae;
    }
}
