package com.scansee.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.scansee.batch.common.ApplicationConstants;
import com.scansee.batch.common.DBConnection;

import com.scansee.batch.common.PropertiesReader;
import com.scansee.batch.exception.NewsTemplateBatchProcessesException;
import com.scansee.batch.pojo.Item;

/**
 * Insert data to staging table and main table, and send email to respective
 * user.
 * 
 * @author vaidehi.ne
 */

public class NewsTemplateDaoImpl implements NewsTemplateDao {
	/**
	 * Logger instance.
	 */
	private static Logger LOG = LoggerFactory.getLogger(NewsTemplateDaoImpl.class.getName());
	/**
	 * Variable for jdbcTemplate.
	 */
	private JdbcTemplate jdbcTemplate;
	/**
	 * To call stored procedure.
	 */
	private SimpleJdbcCall simpleJdbcCall;

	/**
	 * To get database connection.
	 */
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void batchInsertRecordsIntoTable(List<Item> itemList, String newsType, String subcategory, String hubcitiId) throws SQLException,
			NewsTemplateBatchProcessesException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO RssNewsFirstFeedNewsStagingTable(Title, ImagePath, ShortDescription, LongDescription, Link,PublishedDate, NewsType, Message, HcHubCitiID, Adcopy, Section, Classification, author, thumbnail, subcategory, PublishedTime, VideoLink) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {

			dbConnection = DBConnection.getDBConnection();
			if (null != dbConnection) {

				preparedStatement = dbConnection.prepareStatement(insertTableSQL);
				if (null != itemList && !itemList.isEmpty()) {
					for (Item news : itemList) {
						preparedStatement.setString(1, news.getTitle());
						preparedStatement.setString(2, news.getImage());
						preparedStatement.setString(3, news.getShortDesc());
						preparedStatement.setString(4, news.getDescription());
						preparedStatement.setString(5, news.getLink());
						preparedStatement.setString(6, news.getDate());
						preparedStatement.setString(7, newsType);
						preparedStatement.setString(8, news.getMessage());
						preparedStatement.setString(9, hubcitiId);
						preparedStatement.setString(10, news.getAdcopy());
						preparedStatement.setString(11, news.getSection());
						preparedStatement.setString(12, news.getClassification());
						preparedStatement.setString(13, news.getAuthor());
						preparedStatement.setString(14, news.getThumbnail());
						preparedStatement.setString(15, subcategory);
						preparedStatement.setString(16, news.getTime());
						preparedStatement.setString(17, news.getVideoLink());
						preparedStatement.addBatch();
					}
					dbConnection.setAutoCommit(false);
					preparedStatement.executeBatch();
					dbConnection.commit();

				}
			} else {
				LOG.info("Unable to establish Connection object");
			}
		} catch (SQLException e) {
			dbConnection.rollback();

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	/**
	 * Below method is used to insert rss news feed data to staging table.
	 * 
	 */
	public String insertStagingTable(List<Item> itemList, String newsType, String subcategory, String hubcitiId) throws NewsTemplateBatchProcessesException {
		String Response = null;
		LOG.info("Inside FeedsBatchProcssDAOImpl : insertStagingTable : " + newsType);

		try {
			batchInsertRecordsIntoTable(itemList, newsType, subcategory, hubcitiId);
		} catch (SQLException exception) {
			LOG.error("Inside FeedsBatchProcssDAOImpl : insertStagingTable : " + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return Response;
	}

	/**
	 * Below method is used to move data from staging table to main table.
	 * 
	 */
	public String insertMainTable() throws NewsTemplateBatchProcessesException {
		final String methodName = "insertMainTable";
		LOG.info(ApplicationConstants.METHODSTART + methodName);
		Boolean result = null;
		String response = null;

		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.setSchemaName(ApplicationConstants.SCHEMANAME);
			simpleJdbcCall.withProcedureName("usp_WebRssNewsFirstFeedNewsInsertion");

			MapSqlParameterSource externalAPIListParameters = new MapSqlParameterSource();
			final Map<String, Object> resultFromProcedure = simpleJdbcCall.execute(externalAPIListParameters);
			result = (Boolean) resultFromProcedure.get("Status");
			if (null != result && result == false) {
				response = (String) resultFromProcedure.get("ClearCacheURL");
			} else {
				final Integer errorNum = (Integer) resultFromProcedure.get(ApplicationConstants.ERRORNUMBER);
				final String errorMsg = (String) resultFromProcedure.get(ApplicationConstants.ERRORMESSAGE);
				LOG.info(ApplicationConstants.EXCEPTIONOCCURRED + errorNum + "errorMsg.." + errorMsg);
				response = methodName + ApplicationConstants.EXCEPTIONOCCURRED + errorNum + "errorMsg.." + errorMsg;
			}
		} catch (Exception exception) {
			LOG.error(ApplicationConstants.EXCEPTIONOCCURRED + methodName + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return response;

	}

	/**
	 * Method is used to get status of main table to send Email.
	 * 
	 * @return List of hubciti, category and news feeds records count.
	 */
	@SuppressWarnings("unchecked")
	public List<Item> sendEmail() throws NewsTemplateBatchProcessesException {
		LOG.info("Inside NewsTemplateDAOImpl : sendEmail ");
		List<Item> emailList = null;

		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.setSchemaName(ApplicationConstants.SCHEMANAME);
			simpleJdbcCall.withProcedureName("usp_BatchRssNewsFirstFeedEmailNotification");
			simpleJdbcCall.returningResultSet("emptyItemsList",

			new BeanPropertyRowMapper<Item>(Item.class));
			final MapSqlParameterSource map = new MapSqlParameterSource();
			map.addValue("HcHubCitiID", PropertiesReader.getPropertyValue(ApplicationConstants.HUBCITI_ID));
			final Map<String, Object> resultFromProcedure = simpleJdbcCall.execute(map);
			final Integer errorNum = (Integer) resultFromProcedure.get("ErrorNumber");
			final String errorMsg = (String) resultFromProcedure.get("ErrorMessage");

			if (null != resultFromProcedure) {
				if (null == errorNum) {
					emailList = (ArrayList<Item>) resultFromProcedure.get("emptyItemsList");
				} else {
					LOG.info("Inside NewsTemplateDAOImpl : sendEmail : " + errorNum + " errorMsg " + errorMsg);
				}
			}
		} catch (DataAccessException exception) {
			LOG.error("Inside NewsTemplateDAOImpl : sendEmail : " + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return emailList;
	}

	/**
	 * The DAO method to get HubCitiId, category, subcategory and feed URL
	 * 
	 * @return HubCitiId, HubCitiName, categoryId, categoryName, subcategoryId,
	 *         subcategory name and URL
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getHubitiInfo() throws NewsTemplateBatchProcessesException {
		final String methodName = "getHubitiInfo";
		LOG.info(ApplicationConstants.METHODSTART + methodName);
		ArrayList<Item> hubCitiList = null;

		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.setSchemaName(ApplicationConstants.SCHEMANAME);
			simpleJdbcCall.withProcedureName("usp_NewsFirstHubcitiCategory");
			MapSqlParameterSource feed = new MapSqlParameterSource();
			simpleJdbcCall.returningResultSet("hubcitilst", new BeanPropertyRowMapper<Item>(Item.class));
			final Map<String, Object> resultFromProcedure = simpleJdbcCall.execute(feed);
			final Integer errorNum = (Integer) resultFromProcedure.get(ApplicationConstants.ERRORNUMBER);
			final String errorMsg = (String) resultFromProcedure.get(ApplicationConstants.ERRORMESSAGE);

			if (null != resultFromProcedure) {
				if (null == errorNum) {
					hubCitiList = (ArrayList<Item>) resultFromProcedure.get("hubcitilst");
				} else {
					LOG.info("Inside  : getHubitiInfo : " + errorNum + "errorMsg  .." + errorMsg);
				}
			}
		} catch (DataAccessException exception) {
			LOG.info("Inside NewsTemplateDAOImpl : getHubitiInfo : " + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return hubCitiList;
	}

	/**
	 * The DAO method to delete feed history from staging table
	 * 
	 */

	public String deleteFeedData() throws NewsTemplateBatchProcessesException {
		LOG.info("Inside NewsTemplateDAOImpl : deleteFeedData ");
		String strResponse = null;

		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.setSchemaName(ApplicationConstants.SCHEMANAME);
			simpleJdbcCall.withProcedureName("usp_WebRssNewsFirstFeedNewsDeletion");

			final MapSqlParameterSource map = new MapSqlParameterSource();
			final Map<String, Object> resultFromProcedure = simpleJdbcCall.execute(map);
			final Integer errorNum = (Integer) resultFromProcedure.get("ErrorNumber");
			final String errorMsg = (String) resultFromProcedure.get("ErrorMessage");

			if (null != resultFromProcedure) {
				if (null == errorNum) {
					strResponse = ApplicationConstants.SUCCESS;
				} else {
					strResponse = ApplicationConstants.FAILURE;
					LOG.error("Inside NewsTemplateDAOImpl : deleteFeedData : " + errorNum + " errorMsg " + errorMsg);
				}
			}
		} catch (DataAccessException exception) {
			LOG.error("Inside NewsTemplateDAOImpl : deleteFeedData : " + exception);
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return strResponse;
	}
}