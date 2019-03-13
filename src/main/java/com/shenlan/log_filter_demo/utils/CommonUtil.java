package com.shenlan.log_filter_demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class CommonUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	public static void closeResource(Closeable... resources) {
		if(null != resources) {
			for (Closeable resource : resources) {
				try {
					if(null != resource) {
						resource.close();
					}
				} catch (Exception e) {
					logger.error("close resource error", e);
				}
			}
		}
	}

}
















