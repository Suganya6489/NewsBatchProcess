package com.scansee.batch.execution;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scansee.batch.common.ApplicationConstants;
import com.scansee.batch.exception.NewsTemplateBatchProcessesException;

import com.scansee.batch.service.NewsTemplateServiceImpl;

/**
 * Batch Process for News Template.
 * 
 * @author vaidehi.ne
 */
interface NewsFeeds {

	static Logger LOG = Logger.getLogger(NewsTemplateBatchProcessMain.class.getName());

}

public class NewsTemplateBatchProcessMain implements NewsFeeds {

	public static void main(String[] args) throws NewsTemplateBatchProcessesException {

		LOG.info("START OF THE NEWS TEMPLATE BATCH PROCESS :->" + Calendar.getInstance().getTime());
		long startTime = System.currentTimeMillis();

		@SuppressWarnings("resource")
		final ApplicationContext context = new ClassPathXmlApplicationContext("newsTemplate-service.xml");
		final NewsTemplateServiceImpl newsTempService = (NewsTemplateServiceImpl) context.getBean("newsTemplateService");
		try

		{
			LOG.info("************************************* NewsTemplateBatchProcess Start ******************************************");

			/**
			 * Delete history from staging table
			 */
			newsTempService.deleteFeedHistory();
			LOG.info("<================CLEARING OF STATGING TABLE COMPLETED===============>");

			/**
			 * Get News Feeds details and insert into staging table
			 */
			newsTempService.getNewsFeedDetails();
			LOG.info("<==============NEWS INSERTION INTO STAGING TABLE COMPLETED==========>");

			/**
			 * insert feed data from staging table to main table
			 */
			newsTempService.mainTableInsertion();
			LOG.info("<==============NEWS INSERTION INTO MAIN TABLE COMPLETED=============>");

			/**
			 * sending mail with news feeds status
			 */
			newsTempService.sendEmail();
			LOG.info("<=====================EMAIL SENDING COMPLETED=======================>");

		} catch (NewsTemplateBatchProcessesException exception) {
			LOG.info(ApplicationConstants.EXCEPTIONOCCURRED + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		LOG.info("************************************* NewsTemplateBatchProcess Ends ******************************************");
		LOG.info("END OF THE NEWS TEMPLATE BATCH PROCESS :->" + Calendar.getInstance().getTime());
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		String totalTime = formatter.format((endTime - startTime) / 1000d) + " seconds";
		LOG.info("================================================================================================================");
		LOG.info("TOTAL EXECUTION TIME OF BATCH PROCESS :->" + totalTime);
		LOG.info("================================================================================================================");

	}

}
