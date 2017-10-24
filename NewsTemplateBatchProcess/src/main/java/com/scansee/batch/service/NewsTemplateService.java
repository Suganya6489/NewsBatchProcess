package com.scansee.batch.service;

import java.util.List;
import com.scansee.batch.exception.NewsTemplateBatchProcessesException;
import com.scansee.batch.pojo.Item;

/**
 * Inserting data to staging table and main table, and send email.
 * 
 * @author vaidehi.ne
 */

public interface NewsTemplateService {
	/**
	 * The method for inserting NewsTemplate data to staging feed URL parsing
	 * completed.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */

	String stagingTableInsertion(List<Item> items, String category, String subcategory, String hubcitiId) throws NewsTemplateBatchProcessesException;
	/**
	 * The method to delete all news records from staging table.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public String deleteFeedHistory() throws NewsTemplateBatchProcessesException;
	/**
	 * The method to insert news records from staging table to main table.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public String mainTableInsertion() throws NewsTemplateBatchProcessesException;
	/**
	 * The method to get HubCiti and URL info.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public String getNewsFeedDetails() throws NewsTemplateBatchProcessesException;
	/**
	 * The method to send status of news feeds to the sender_to list. completed.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public String sendEmail() throws NewsTemplateBatchProcessesException;

}
