package com.grab;

import com.grab.entity.CurriculumVitae;

import java.io.File;

/**
 * Created by baiqunwei on 15/6/4.
 */
public interface IProcessCurriculumVitae {
    /**
     * 处理简历信息
     *
     * @param file html 简历 文件名
     * @return 简历对象
     */
    public CurriculumVitae process(File file) throws Exception;
}
