package com.scansee.batch.pojo;

/**
 * @author vaidehi.ne
 */
import java.util.List;

/**
 * @author vaidehi.ne
 * 
 */
public class Item {

	private int id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String image;
	/**
	 * 
	 */
	private String link;
	/**
	 * 
	 */
	private String feedType;
	/**
	 * 
	 */
	private String HcHubCitiID;
	/**
	 * 
	 */
	private String HubCitiName;
	/**
	 * 
	 */
	private String classification;
	/**
	 * 
	 */
	private String section;
	/**
	 * 
	 */
	private String adcopy;
	/**
	 * 
	 */
	private String date;
	/**
	 * 
	 */
	private String shortDesc;
	/**
	 * 
	 */
	private Integer rowCount;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private List<Item> items;
	/**
	 * 
	 */
	private String message;
	/**
	 * 
	 */
	private String url;
	/**
	 * 
	 */
	private String newsCategoryID;
	/**
	 * 
	 */
	private String[] newsCategoryName;
	/**
	 * 
	 */
	private String author;
	/**
	 * 
	 */
	private String thumbnail;
	/**
	 * 
	 */
	private String subcategory;
	/**
	 * 
	 */
	private String time;
	/**
	 * 
	 */
	private String videoLink;
	/**
	 * 
	 */
	private String newscount;
	/**
	 * 
	 */
	private Boolean isSubCategory;
	/**
	 * 
	 */
	private String[] newsSubCategoryName;
	/**
	 * 
	 */
	private String newsSubCategoryURL;

	public Item() {

	}

	public Item(String tit, String description, String image, String link, String date, String shortDesc, String message, String author, String time,
			String thumbnail, String videoLink) {
		this.title = tit;
		this.description = description;
		this.image = image;
		this.link = link;
		this.date = date;
		this.shortDesc = shortDesc;
		this.message = message;
		this.author = author;
		this.time = time;
		this.thumbnail = thumbnail;
		this.videoLink = videoLink;
	}

	public Item(String tit, String description, String image, String link, String date, String shortDesc, String videoLink) {
		this.title = tit;
		this.description = description;
		this.image = image;
		this.link = link;
		this.date = date;
		this.shortDesc = shortDesc;
		this.videoLink = videoLink;
	}

	public Item(String tit, String description, String image, String link) {
		this.title = tit;
		this.description = description;
		this.image = image;
		this.link = link;
	}

	public Item(String tit, String description, String image, String link, String date, String shortDesc, String message, String adcopy, String section,
			String classification, String time, String author, String thumbnail, String videoLink) {
		super();
		this.adcopy = adcopy;
		this.section = section;
		this.classification = classification;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * @param classification
	 *            the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the adcopy
	 */
	public String getAdcopy() {
		return adcopy;
	}

	/**
	 * @param adcopy
	 *            the adcopy to set
	 */
	public void setAdcopy(String adcopy) {
		this.adcopy = adcopy;
	}

	/**
	 * @return the HcHubCitiID
	 */
	public String getHcHubCitiID() {
		return HcHubCitiID;
	}

	/**
	 * @param HcHubCitiID
	 *            the HcHubCitiID to set
	 */
	public void setHcHubCitiID(String hcHubCitiID) {
		HcHubCitiID = hcHubCitiID;
	}

	/**
	 * @return the rowCount
	 */
	public Integer getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the shortDesc
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * @param shortDesc
	 *            the shortDesc to set
	 */
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the feedType
	 */
	public String getFeedType() {
		return feedType;
	}

	/**
	 * @param feedType
	 *            the feedType to set
	 */
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		if (title != null) {
			this.title = title;
		} else {
			this.title = null;
		}
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the HubCitiName
	 */
	public String getHubCitiName() {
		return HubCitiName;
	}

	/**
	 * @param HubCitiName
	 *            the HubCitiName to set
	 */
	public void setHubCitiName(String hubCitiName) {
		this.HubCitiName = hubCitiName;
	}

	/**
	 * @return the newsCategoryID
	 */
	public String getNewsCategoryID() {
		return newsCategoryID;
	}

	/**
	 * @param newsCategoryID
	 *            the newsCategoryID to set
	 */
	public void setNewsCategoryID(String newsCategoryID) {
		this.newsCategoryID = newsCategoryID;
	}

	/**
	 * @return the newsCategoryName
	 */
	public String[] getNewsCategoryName() {
		return newsCategoryName;
	}

	/**
	 * @param newsCategoryName
	 *            the newsCategoryName to set
	 */
	public void setNewsCategoryName(String[] newsCategoryName) {
		this.newsCategoryName = newsCategoryName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the subcategory
	 */
	public String getSubcategory() {
		return subcategory;
	}

	/**
	 * @param subcategory
	 *            the subcategory to set
	 */
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the videoLink
	 */
	public String getVideoLink() {
		return videoLink;
	}

	/**
	 * @param videoLink
	 *            the videoLink to set
	 */
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	/**
	 * @return the newscount
	 */
	public String getNewscount() {
		return newscount;
	}

	/**
	 * @param newscount
	 *            the newscount to set
	 */
	public void setNewscount(String newscount) {
		this.newscount = newscount;
	}

	/**
	 * @return the isSubCategory
	 */
	public Boolean getIsSubCategory() {
		return isSubCategory;
	}

	/**
	 * @param isSubCategory
	 *            the isSubCategory to set
	 */
	public void setIsSubCategory(Boolean isSubCategory) {
		this.isSubCategory = isSubCategory;
	}

	/**
	 * @return the newsSubCategoryName
	 */
	public String[] getNewsSubCategoryName() {
		return newsSubCategoryName;
	}

	/**
	 * @param newsSubCategoryName
	 *            the newsSubCategoryName to set
	 */
	public void setNewsSubCategoryName(String[] newsSubCategoryName) {
		this.newsSubCategoryName = newsSubCategoryName;
	}

	/**
	 * @return the newsSubCategoryURL
	 */
	public String getNewsSubCategoryURL() {
		return newsSubCategoryURL;
	}

	/**
	 * @param newsSubCategoryURL
	 *            the newsSubCategoryURL to set
	 */
	public void setNewsSubCategoryURL(String newsSubCategoryURL) {
		this.newsSubCategoryURL = newsSubCategoryURL;
	}

}
