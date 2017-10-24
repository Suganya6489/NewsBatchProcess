package com.scansee.batch.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scansee.batch.common.ApplicationConstants;
import com.scansee.batch.common.EmailComponent;
import com.scansee.batch.common.PropertiesReader;
import com.scansee.batch.common.Utility;
import com.scansee.batch.dao.NewsTemplateDao;
import com.scansee.batch.exception.NewsTemplateBatchProcessesException;
import com.scansee.batch.pojo.Item;
import com.scansee.batch.threads.RssBatchThread;

public class NewsTemplateServiceImpl implements NewsTemplateService {

	/**
	 * Logger instance.
	 */
	private static Logger LOG = LoggerFactory.getLogger(NewsTemplateServiceImpl.class.getName());

	private NewsTemplateDao newsTemplateDao;

	public void setNewsTemplateDao(NewsTemplateDao newsTemplateDao) {
		this.newsTemplateDao = newsTemplateDao;
	}

	/**
	 * method to delete history of feed data from staging table
	 * 
	 */
	public String deleteFeedHistory() throws NewsTemplateBatchProcessesException {
		String strMethodName = "deleteFeedHistory";
		LOG.info(ApplicationConstants.METHODSTART + strMethodName);
		String response = null;
		try {
			response = newsTemplateDao.deleteFeedData();
		} catch (NewsTemplateBatchProcessesException exception) {
			LOG.error("Inside NewsTemplateServiceImpl : deleteFeedHistory : " + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return response;
	}

	/**
	 * method to get feed information from XML Parser
	 * 
	 */
	public String getNewsFeedDetails() throws NewsTemplateBatchProcessesException {
		String strMethodName = "getNewsFeedDetails";
		LOG.info(ApplicationConstants.METHODSTART + strMethodName);
		String response = null;
		ArrayList<Item> hubList = null;

		List<RssBatchThread> rssThreads = new ArrayList<RssBatchThread>();

		try {
			hubList = newsTemplateDao.getHubitiInfo();

			if (null != hubList && !hubList.isEmpty()) {

				for (Item item : hubList) {
					rssThreads.add(new RssBatchThread(item));
				}

				if (!rssThreads.isEmpty()) {
					for (RssBatchThread rssThread : rssThreads) {
						try {
							rssThread.getThread().join();

						} catch (InterruptedException e) {
							LOG.error("Inside NewsTemplateServiceImpl : getNewsFeedDetails() : " + e.getMessage());
						}
					}
				}
			}
		} catch (NewsTemplateBatchProcessesException exception) {
			LOG.error("Inside NewsTemplateServiceImpl : getNewsFeedDetails() : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return response;
	}

	/**
	 * method is used to insert news feed info into staging table.
	 * 
	 */
	public String stagingTableInsertion(List<Item> items, String category, String subcategory, String hubcitiId) throws NewsTemplateBatchProcessesException {
		String strMethodName = "stagingTableInsertion";
		LOG.info(ApplicationConstants.METHODSTART + strMethodName);

		String result = null;
		String response = null;
		try {

			if (null != items && !items.isEmpty()) {
				result = newsTemplateDao.insertStagingTable(items, category, subcategory, hubcitiId);
				if (null != result && result.equals(ApplicationConstants.SUCCESS)) {
					response = ApplicationConstants.SUCCESS;
				} else {
					response = ApplicationConstants.FAILURE;
				}
			} else {
				LOG.info("Inside NewsTemplateServiceImpl : stagingTableInsertion : " + " No Items in the List to insert into database.");
			}
		} catch (NewsTemplateBatchProcessesException exception) {
			LOG.error("Inside NewsTemplateServiceImpl : stagingTableInsertion : " + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return response;
	}

	/**
	 * method is used to insert news feeds info from staging table to main
	 * table.
	 * 
	 */
	/*
	 * public String mainTableInsertion() throws
	 * NewsTemplateBatchProcessesException { String response = null; String
	 * strMethodName = "mainTableInsertion";
	 * LOG.info(ApplicationConstants.METHODSTART + strMethodName); try {
	 * 
	 * response = newsTemplateDao.insertMainTable();
	 * 
	 * if (null != response) { response =
	 * Utility.clearHubCitiNewsCache(response); } else { response =
	 * ApplicationConstants.FAILURE; } } catch
	 * (NewsTemplateBatchProcessesException exception) {
	 * LOG.error("Inside NewsTemplateServiceImpl : mainTableInsertion : " +
	 * exception); throw new NewsTemplateBatchProcessesException(exception); }
	 * return response; }
	 */

	public String mainTableInsertion() throws NewsTemplateBatchProcessesException {
		String response = null;
		String strMethodName = "NewsFeedPorting";
		LOG.info(ApplicationConstants.METHODSTART + strMethodName);
		try {

			response = newsTemplateDao.insertMainTable();

			if (null != response) {
				// response = ApplicationConstants.SUCCESS;
				response = Utility.clearHubCitiNewsCache(response);
			} else {
				response = ApplicationConstants.FAILURE;
			}
		} catch (NewsTemplateBatchProcessesException e) {
			LOG.error("Inside NewsTemplateServiceImpl : processDatabaseOperation : " + e);
			throw new NewsTemplateBatchProcessesException(e);
		}
		LOG.info("Exit Service method NewsFeedPorting");
		return response;
	}

	/**
	 * method to send email to the configured recipients.
	 * 
	 * @throws NewsTemplateBatchProcessesException
	 * 
	 */
	public String sendEmail() throws NewsTemplateBatchProcessesException {
		LOG.info("Inside NewsTemplateServiceImpl : sendEmail");

		String response = null;
		List<Item> newsItemList = null;

		final String strSmtpPort = PropertiesReader.getPropertyValue(ApplicationConstants.SMTP_PORT);
		final String strSmtpHost = PropertiesReader.getPropertyValue(ApplicationConstants.SMTP_SERVER);
		final String strSubject = PropertiesReader.getPropertyValue(ApplicationConstants.EMAIL_SUBJECT);
		final String strEmailrecipient = PropertiesReader.getPropertyValue(ApplicationConstants.SENDER_TO_LIST);
		final String strFromEmailId = PropertiesReader.getPropertyValue(ApplicationConstants.FROM_MAIL);
		final String strEmailrecipients[] = strEmailrecipient.split(",");

		try {
			newsItemList = newsTemplateDao.sendEmail();

			if (null != newsItemList && !newsItemList.isEmpty()) {
				final String strMailContent = Utility.emailBody(newsItemList);
				response = ApplicationConstants.SUCCESS;
				EmailComponent.multipleUsersmailingComponent(strFromEmailId, strEmailrecipients, strSubject, strMailContent, strSmtpHost, strSmtpPort);
			} else {
				response = ApplicationConstants.FAILURE;
			}

		} catch (MessagingException e) {
			LOG.error("Inside NewsTemplateServiceImpl : sendEmail :  MessagingException : " + e.getMessage());

		} catch (NewsTemplateBatchProcessesException exception) {
			LOG.error("Inside NewsTemplateServiceImpl : sendEmail : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return response;
	}

}
