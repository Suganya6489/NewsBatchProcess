package com.scansee.batch.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;

import com.scansee.batch.common.Utility;
import com.scansee.batch.exception.NewsTemplateBatchProcessesException;
import com.scansee.batch.pojo.Item;
import com.scansee.batch.service.NewsTemplateServiceImpl;

public class RssBatchThread implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(NewsTemplateServiceImpl.class.getName());

	Thread thread = null;

	public RssBatchThread(Item item) {
		thread = new Thread(this);
		this.item = item;
		thread.start();
	}

	public Thread getThread() {
		return thread;
	}

	/**
	 * Logger instance.
	 */

	private Item item;

	@Override
	public void run() {

		String[] strNews = item.getNewsCategoryName();
		try {
			if (null != item) {
				for (String category : strNews) {
					LOG.info("Inside run() : " + category);
					Utility.getXMLFeedDetails(category, null, item.getHcHubCitiID(), item.getUrl());

					if (item.getIsSubCategory()) {

						String[] subCats = item.getNewsSubCategoryName();
						if (null != subCats && subCats.length > 0) {
							for (String subcategory : subCats) {
								LOG.info("Inside run() : " + subcategory);
								Utility.getXMLFeedDetails(category, subcategory, item.getHcHubCitiID(), item.getNewsSubCategoryURL());
							}
						}
					}
				}
			}
		} catch (ParseException e) {
			LOG.error("Inside RssBatchThread:" + e.getMessage());
		} catch (NewsTemplateBatchProcessesException exception) {
			exception.printStackTrace();
		}
	}

}
