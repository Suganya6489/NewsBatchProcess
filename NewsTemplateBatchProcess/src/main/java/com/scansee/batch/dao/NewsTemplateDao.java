package com.scansee.batch.dao;

import java.util.ArrayList;
import java.util.List;
import com.scansee.batch.exception.NewsTemplateBatchProcessesException;
import com.scansee.batch.pojo.Item;

/**
 * Inserting data to staging table and main table, and send email.
 * 
 * @author vaidehi.ne
 */
public interface NewsTemplateDao {
	/**
	 * The method for inserting NewsTemplate data to staging feed URL parsing
	 * completed.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */

	String insertStagingTable(List<Item> items, String newsType, String subcategory, String hubcitiId) throws NewsTemplateBatchProcessesException;

	/**
	 * The method to send status of news feeds to the sender_to list. completed.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public List<Item> sendEmail() throws NewsTemplateBatchProcessesException;

	/**
	 * The method to insert news records from staging table to main table.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public String insertMainTable() throws NewsTemplateBatchProcessesException;

	/**
	 * The method to get HubCiti and URL info.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public ArrayList<Item> getHubitiInfo() throws NewsTemplateBatchProcessesException;

	/**
	 * The method to delete all news records from staging table.
	 * 
	 * @param
	 * @throws NewsTemplateBatchProcessesException
	 */
	public String deleteFeedData() throws NewsTemplateBatchProcessesException;
}
