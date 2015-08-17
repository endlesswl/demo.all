package com.lingcaibao.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lingcaibao.listener.GlobalParameterListener;

/**
 * @author xin.pang
 */
public class VerUtil {
    private static final Logger logger = LoggerFactory.getLogger(VerUtil.class);

    public static String ver(String path) {
        String ver = "";
        try {// velocityConfigurer.getVelocityEngine().getTemplate(name)
            File verFile = new File(GlobalParameterListener.getContextPath() + "/WEB-INF/views" + path);
            // System.out.println(verFile.getAbsolutePath());
            if (verFile.exists()) {
                return ("@" + FileUtils.readFileToString(verFile)).trim();
            }
            return ver;
        } catch (IOException e) {
            logger.error("ver 文件不存在 !" + path, e);
            return ver;
        }
    }
}
