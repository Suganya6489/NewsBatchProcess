package com.scansee.batch.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;

import org.jboss.resteasy.client.ClientRequest;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.scansee.batch.exception.NewsTemplateBatchProcessesException;
import com.scansee.batch.pojo.Item;
import com.scansee.batch.service.NewsTemplateServiceImpl;

/**
 * 
 * @author vaidehi.ne Utility class for reusable methods.
 * 
 */

public class Utility {

	private static Logger LOG = LoggerFactory.getLogger(Utility.class.getName());

	/**
	 * method to display email
	 * 
	 * @throws NewsTemplateBatchProcessesException
	 * 
	 */
	public static String emailBody(List<Item> newsItemList) throws NewsTemplateBatchProcessesException {
		LOG.info("Inside Utility : emailBody ");

		StringBuilder emailBody = new StringBuilder();
		try {
			emailBody.append("Hi,");
			emailBody.append("</br></br>");

			if (null != newsItemList && !newsItemList.isEmpty()) {
				emailBody.append(PropertiesReader.getPropertyValue(ApplicationConstants.MAIL_CONTENT));
				emailBody.append("</br></br>");
				emailBody.append("For the Time Stamp  &nbsp;<b>: &nbsp;&nbsp;" + Utility.getCurrentDateandTime() + "</b>.\n");
				emailBody.append("</br></br>");
				emailBody
						.append("<table cellspacing='0' cellpadding='0' border='1'><tr bgcolor='#FFFF99'><th>&nbsp;Sl No.&nbsp;</th><th>&nbsp;HubCiti(s)&nbsp;</th><th>&nbsp;NewsType(s)&nbsp;</th><th>&nbsp;News Count&nbsp;</th><th>&nbsp;Reason&nbsp;</th>");
				emailBody.append("</tr>");

				for (int i = 0; i < newsItemList.size(); i++) {
					Item objItem = newsItemList.get(i);

					emailBody.append("<tr>");

					emailBody.append("<td align=\"center\">");
					emailBody.append(i);
					emailBody.append("</td>");

					emailBody.append("<td align=\"center\">");
					emailBody.append(objItem.getHubCitiName());
					emailBody.append("</td>");

					emailBody.append("<td align=\"center\">");
					emailBody.append(objItem.getFeedType());
					emailBody.append("</td>");

					emailBody.append("<td align=\"center\">");
					emailBody.append(objItem.getNewscount());
					emailBody.append("</td>");

					emailBody.append("<td align=\"left\">");
					emailBody.append(objItem.getMessage());
					emailBody.append("</td>");

					emailBody.append("</tr>");
				}
				emailBody.append("</table>");
			}
			emailBody.append("</br></br>");
			emailBody.append("Regards,</br>");
			emailBody.append("HubCiti Team");
		} catch (NewsTemplateBatchProcessesException exception) {
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return emailBody.toString();
	}

	/**
	 * method to get current date and time
	 * 
	 * @return Current Date & Time
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static String getCurrentDateandTime() throws NewsTemplateBatchProcessesException {
		Date now = new Date();
		String currentDate = null;
		try {
			currentDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(now);
		} catch (Exception exception) {
			LOG.error("Inside Utility :  getCurrentDateandTime : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return currentDate;
	}

	/**
	 * method to fetch MP4 video URL using video Id.
	 * 
	 * @param videoId
	 *            as input parameter.
	 * @return video URL.
	 * @throws NewsTemplateBatchProcessesException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getMP4RssFeedVideo(long videoId) throws NewsTemplateBatchProcessesException, ParseException, IOException {

		String videoURL = null;
		String url = "http://api.brightcove.com/services/library?command=find_video_by_id&video_fields=name,length,FLVURL&media_delivery=http&token=x95LXczyNI5-G9kX0cjsHM9edPFzaKFTE4PANJ7L2rQfuF-swGUxJg.."
				+ "&video_id=" + videoId;

		try {

			InputStream input = new URL(url).openStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
			String jsonText = bufferRead(read);
			JSONObject json = new JSONObject(jsonText);

			videoURL = json.getString("FLVURL");
		} catch (NewsTemplateBatchProcessesException exception) {
			LOG.error("Inside Utility : getMP4RssFeedVideo :  " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return videoURL;
	}

	/**
	 * method to read BufferedReader.
	 * 
	 * @param bufrRead
	 *            as input parameter.
	 * @return StringBuilder sbuilder.
	 * @throws NewsTemplateBatchProcessesException
	 */
	private static String bufferRead(Reader bufrRead) throws NewsTemplateBatchProcessesException {
		StringBuilder sbuilder = new StringBuilder();
		int bldrValue;
		try {
			while ((bldrValue = bufrRead.read()) != -1) {
				sbuilder.append((char) bldrValue);
			}
		} catch (IOException exception) {
			LOG.error("Inside Utility : readAll :  " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return sbuilder.toString();
	}

	/**
	 * XML Parser to parse the RSS Feed URLs
	 * 
	 * @param category
	 *            as input parameter
	 * @param subcategory
	 *            as input parameter
	 * @param hubCitiID
	 *            as input parameter
	 * @param feedURL
	 *            as input parameter
	 * @return title, long description, short description, image, link,
	 *         published date, published time,author, video and thumbnail for
	 *         each item
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static void getXMLFeedDetails(String category, String subcategory, String hubCitiID, String urls) throws NewsTemplateBatchProcessesException {

		LOG.info("Inside Utility : getNewsFeedDetails ");

		List<Item> items = new ArrayList<Item>();
		@SuppressWarnings("resource")
		final ApplicationContext context = new ClassPathXmlApplicationContext("newsTemplate-service.xml");
		final NewsTemplateServiceImpl newsTemplateService = (NewsTemplateServiceImpl) context.getBean("newsTemplateService");
		InputStream inputStream = null;
		try {
			DocumentBuilder db = DomConfig.getDocumentConfig();
			inputStream = getDOMConnection(urls);
			if (inputStream.available() > 0) {
				Document doc = db.parse(new InputSource(inputStream));
				doc.getDocumentElement().normalize();
				NodeList nodeList = doc.getElementsByTagName("item");

				for (int i = 0; i < nodeList.getLength(); i++) {
					String title = null;
					String lDescription = null;
					String image = null;
					String link = null;
					String date = null;
					String time = null;
					String sDescription = null;
					String author = null;
					String thumbnail = null;
					String videoURL = null;
					Node node = nodeList.item(i);
					Element fstElmnt = (Element) node;
					String[] description = null;

					// get image from feed URL
					image = getfeedImage(fstElmnt, category, subcategory);

					if (fstElmnt.getElementsByTagName("link") != null) {
						NodeList linkList = fstElmnt.getElementsByTagName("link");
						Element linkElement = (Element) linkList.item(0);
						linkList = linkElement.getChildNodes();
						Node linkNode = ((Node) linkList.item(0));
						if (null != linkNode) {
							link = linkNode.getNodeValue();
						}
					}

					if (fstElmnt.getElementsByTagName("title") != null) {
						NodeList titleList = fstElmnt.getElementsByTagName("title");
						Element titleElement = (Element) titleList.item(0);
						titleList = titleElement.getChildNodes();
						Node titleNode = ((Node) titleList.item(0));
						if (null != titleNode) {
							title = ((Node) titleList.item(0)).getNodeValue().toString();
							title = replaceRegExOfDescription(title);
						}
					}

					if (fstElmnt.getElementsByTagName("pubDate") != null) {
						NodeList dateList = fstElmnt.getElementsByTagName("pubDate");
						Element dateElement = (Element) dateList.item(0);
						dateList = dateElement.getChildNodes();
						String datee = ((Node) dateList.item(0)).getNodeValue();
						String[] dates = datee.split(" ");
						if (dates.length > 5) {
							date = dates[2] + " " + dates[1] + " " + " , " + dates[3];
							time = dates[4];
						} else {
							date = datee;
						}
					}

					if (fstElmnt.getElementsByTagName("author") != null) {
						NodeList authorList = fstElmnt.getElementsByTagName("author");
						Element authorElement = (Element) authorList.item(0);
						Node authorNode = ((Node) authorList.item(0));
						if (authorNode != null) {
							authorList = authorElement.getChildNodes();
							author = ((Node) authorList.item(0)).getNodeValue();
						}
					}

					// get thumbnail from feed URL
					thumbnail = getThumbnail(fstElmnt);

					// get long description and short description from feed URL
					description = getDescription(fstElmnt, image, category, subcategory);
					lDescription = description[0];
					sDescription = description[1];

					// get video URL from feed URL
					videoURL = getfeedVideoURL(fstElmnt);

					Item item = new Item(title, lDescription, image, link, date, sDescription, null, author, time, thumbnail, videoURL);
					item.setId(i + 1);
					items.add(item);

				}
				newsTemplateService.stagingTableInsertion(items, category, subcategory, hubCitiID);
			}
		} catch (Exception e) {
			LOG.error("Inside Utility : getNewsFeedDetails :  " + e.getMessage());
			String message = e.getMessage();
			Item item = new Item(null, null, null, null, null, null, message, null, null, null, null);
			items.add(item);

			try {
				newsTemplateService.stagingTableInsertion(items, category, subcategory, hubCitiID);
			} catch (NewsTemplateBatchProcessesException exception) {
				LOG.error("Inside Utility : getNewsFeedDetails :  " + exception.getMessage());
				throw new NewsTemplateBatchProcessesException(exception);
			}
		}
	}

	/**
	 * method to clear the cache
	 * 
	 * @param clearCacheURL
	 *            as input parameter.
	 * @return success.
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static String clearHubCitiNewsCache(String clearCacheURL) throws NewsTemplateBatchProcessesException {
		final String methodName = "clearHubCitiFindCache";
		LOG.info(ApplicationConstants.METHODSTART + methodName);

		final String[] urls = clearCacheURL.split(",");

		for (String url : urls) {
			ClientRequest request = new ClientRequest(url);
			request.getHeaders();

			try {
				String response = request.getTarget(String.class);
				LOG.info(methodName + "URI" + url);
				LOG.info(methodName + "Response" + response);
			} catch (Exception e) {
				LOG.info(methodName + "URI" + url);
				LOG.error(ApplicationConstants.EXCEPTIONOCCURRED + e.getMessage());
				throw new NewsTemplateBatchProcessesException(e);
			}
		}
		LOG.info(ApplicationConstants.METHODEND + methodName);
		return ApplicationConstants.SUCCESS;
	}

	/**
	 * method to establish connection for the feed URL
	 * 
	 * @param urls
	 *            as input parameter.
	 * @return inputStream.
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static InputStream getDOMConnection(String urls) throws Exception {

		URL url = null;
		InputStream inputStream = null;
		URLConnection connection;
		try {
			url = new URL(urls);
			connection = url.openConnection();
			// connection.addRequestProperty("User-Agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");
			connection.getInputStream();
			inputStream = connection.getInputStream();
		} catch (Exception exception) {
			LOG.error("Inside Utility :  getDOMConnection : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return inputStream;
	}

	/**
	 * method to get image of the feed URL.
	 * 
	 * @param fstElmnt
	 *            as input parameter.
	 * @param category
	 *            as input parameter.
	 * @param subcategory
	 *            as input parameter.
	 * @return image.
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static String getfeedImage(Element fstElmnt, String category, String subcategory) throws NewsTemplateBatchProcessesException {
		String image = null;
		String mediaurl = null;
		try {
			NodeList mediaContent = fstElmnt.getElementsByTagName("media:content");
			int index = 0;
			if (mediaContent.item(index) != null && mediaContent.item(index).getAttributes() != null
					&& mediaContent.item(index).getAttributes().getNamedItem("url") != null) {
				for (index = 0; index < mediaContent.getLength(); index++) {
					mediaurl = mediaContent.item(index).getAttributes().getNamedItem("url").getNodeValue();
					if (image == null) {
						image = mediaurl;
					} else if ("Photos".equals(category) || "Photos".equals(subcategory)) {
						image = image + "," + mediaurl;
					}
				}
			} else if (image == null && null != fstElmnt.getElementsByTagName("content:encoded")) {
				NodeList imgList = fstElmnt.getElementsByTagName("content:encoded");
				Element imgElement = (Element) imgList.item(index);
				if (null != imgElement && null != imgList.item(index)) {
					imgList = imgElement.getChildNodes();
					if (null != imgList.item(index)) {

						if (imgList.item(index).toString() != null) {
							org.jsoup.nodes.Document docHtml = Jsoup.parse(imgList.item(index).toString());
							Elements imgEle = docHtml.select("img");
							for (index = 0; index < imgList.getLength(); index++) {
								if (image == null) {
									image = imgEle.attr("src");
								} else if ("Photos".equals(category) || "Photos".equals(subcategory)) {
									image = image + "," + imgEle.attr("src");
								}
							}
						}
					}
				} else if (image == null && null != fstElmnt.getElementsByTagName("enclosure")) {
					NodeList mediaEnclosure = fstElmnt.getElementsByTagName("enclosure");
					if (mediaEnclosure.item(index) != null && mediaEnclosure.item(index).getAttributes() != null
							&& mediaEnclosure.item(index).getAttributes().getNamedItem("url") != null) {
						for (index = 0; index < mediaEnclosure.getLength(); index++) {
							mediaurl = mediaEnclosure.item(index).getAttributes().getNamedItem("url").getNodeValue();
							if (image == null) {
								image = mediaurl;
							} else if ("Photos".equals(category) || "Photos".equals(subcategory)) {
								image = image + "," + mediaurl;
							}
						}
					}
				}
			}
		} catch (Exception exception) {
			LOG.error("Inside Utility :  getfeedImage : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return image;
	}

	/**
	 * method to get image of the feed URL.
	 * 
	 * @param fstElmnt
	 *            as input parameter.
	 * @param category
	 *            as input parameter.
	 * @param subcategory
	 *            as input parameter.
	 * @param image
	 *            as input parameter.
	 * @return ldescription.
	 * @return sdescription.
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static String[] getDescription(Element fstElmnt, String image, String category, String subcategory) throws NewsTemplateBatchProcessesException {
		String lDescription = null;
		String sDescription = null;
		try {
			NodeList contentList = fstElmnt.getElementsByTagName("content:encoded");
			Element contentElement = (Element) contentList.item(0);
			Node contentEnctdNode = ((Node) contentList.item(0));
			if (null != contentEnctdNode) {
				contentList = contentElement.getChildNodes();
				if (null != contentList.item(0)) {

					if (contentList.item(0).toString() != null) {
						lDescription = contentList.item(0).toString();
						if (null != lDescription && !"".equals(lDescription)) {
							lDescription = lDescription.replace("[#text:", "");
							lDescription = lDescription.substring(0, lDescription.length() - 1).trim();
							lDescription = replaceRegExOfDescription(lDescription);
						}
						if (null != lDescription && lDescription.length() > 150) {
							sDescription = lDescription.substring(0, 150) + "...";
						} else {
							sDescription = null;
						}
					}
				}
			}

			NodeList websiteList = fstElmnt.getElementsByTagName("description");
			if (lDescription == null) {
				Element websiteElement = (Element) websiteList.item(0);
				Node descriptionNode = ((Node) websiteList.item(0));
				if (null != descriptionNode) {
					websiteList = websiteElement.getChildNodes();
					if (null != websiteList.item(0)) {

						if (websiteList.item(0).toString() != null) {
							lDescription = websiteList.item(0).toString();

							if (null != lDescription && !"".equals(lDescription)) {
								lDescription = lDescription.replace("[#text:", "");

								if (null == image) {
									org.jsoup.nodes.Document docHtml = Jsoup.parse(lDescription.toString());
									Elements imgEle = docHtml.select("img");
									image = imgEle.attr("src");
									if (image == null) {
										image = imgEle.attr("src");
									} else if ("Photos".equals(category) || "Photos".equals(subcategory)) {
										image = image + "," + imgEle.attr("src");
									}
								}
								lDescription = lDescription.substring(0, lDescription.length() - 1).trim();
								lDescription = replaceRegExOfDescription(lDescription);
							}
							if (null != lDescription && lDescription.length() > 150) {
								sDescription = lDescription.substring(0, 150) + "...";
							} else {
								sDescription = null;
							}
						}
					}
				}
			}
			if (lDescription == null) {
				Node mediaText = fstElmnt.getElementsByTagName("media:text").item(0);
				if (mediaText != null) {
					if (mediaText != null && mediaText.getAttributes() != null && mediaText.getAttributes().getNamedItem("type") != null) {
						String value = mediaText.getAttributes().getNamedItem("type").getNodeValue();
						if (lDescription == null) {
							if (value.equalsIgnoreCase("html")) {
								NodeList linkList = fstElmnt.getElementsByTagName("media:text");
								Element linkElement = (Element) linkList.item(0);
								Node mediaTxtNode = ((Node) linkList.item(0));
								if (null != mediaTxtNode) {
									linkList = linkElement.getChildNodes();
									if (null != linkList.item(0)) {

										if (linkList.item(0).toString() != null) {
											lDescription = linkList.item(0).toString();

											if (null != lDescription && !"".equals(lDescription)) {
												lDescription = lDescription.replace("[#text:", "");
												lDescription = lDescription.substring(0, lDescription.length() - 1).trim();
												lDescription = replaceRegExOfDescription(lDescription);
											}
											if (null != lDescription && lDescription.length() > 150) {
												sDescription = lDescription.substring(0, 150) + "...";
											} else {
												sDescription = null;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			Node descriptionNode = ((Node) websiteList.item(0));
			// Node contentEnctdNode = ((Node) contentList.item(0));
			if (null != descriptionNode) {
				if (null != lDescription && lDescription.length() > 150) {
					sDescription = lDescription.substring(0, 150) + "...";
				} else {
					sDescription = lDescription;
				}
			} else if (null != contentEnctdNode) {
				if (null != lDescription && lDescription.length() > 150) {
					sDescription = lDescription.substring(0, 150) + "...";
				} else {
					sDescription = lDescription;
				}
			} else {
				sDescription = lDescription;
			}
		} catch (Exception exception) {
			LOG.error("Inside Utility :  getDescription : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return new String[] { lDescription, sDescription };
	}

	/**
	 * method to fetch MP4 video URL using video Id.
	 * 
	 * @param videoId
	 *            as input parameter.
	 * @return video URL.
	 * @throws NewsTemplateBatchProcessesException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getfeedVideoURL(Element fstElmnt) throws NewsTemplateBatchProcessesException {

		String videoURL = null;
		try {
			Node videoLink = fstElmnt.getElementsByTagName("media:text").item(1);
			if (videoLink != null) {
				if (videoLink != null && videoLink.getAttributes() != null && videoLink.getAttributes().getNamedItem("type") != null) {
					String value = videoLink.getAttributes().getNamedItem("type").getNodeValue();
					if (value.equalsIgnoreCase("video")) {
						String videoId = null;
						NodeList linkList = fstElmnt.getElementsByTagName("media:text");
						Element linkElement = (Element) linkList.item(1);
						linkList = linkElement.getChildNodes();
						String nodeValue = ((Node) linkList.item(0)).getNodeValue();

						if (null != nodeValue && !"".equals(nodeValue)) {
							String media = nodeValue.replace("[#text:", "");

							String pattern = ".*videoPlayer\"\\s+value=\"(\\d+).*";
							Pattern ptn = Pattern.compile(pattern);
							Matcher matcher = ptn.matcher(media);
							while (matcher.find()) {
								videoId = matcher.group(1);
							}

							if (!matcher.find()) {
								String pattern1 = ".*videoId=.*";
								Pattern ptn1 = Pattern.compile(pattern1);
								Matcher matcher1 = ptn1.matcher(media);
								while (matcher1.find()) {
									org.jsoup.nodes.Document docHtml = Jsoup.parse(linkList.item(0).toString());
									Elements imgEle = docHtml.select("iframe");
									String medLink = imgEle.attr("src");
									int endIndex = medLink.length();
									int startIndex = medLink.indexOf("=") + 1;
									videoId = (String) medLink.subSequence(startIndex, endIndex);
								}
							}
							BigDecimal bd = new BigDecimal(videoId);
							videoURL = getMP4RssFeedVideo(bd.longValue());
						}
					}
				}
			}
		} catch (Exception exception) {
			LOG.error("Inside Utility :  getfeedVideoURL : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return videoURL;
	}

	/**
	 * method to replace RegEx.
	 * 
	 * @param text
	 *            as input parameter.
	 * @return text.
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static String replaceRegExOfDescription(String text) throws NewsTemplateBatchProcessesException {
		try {
			text = text/* .replaceAll("([^\\w\\s\'.,-:&\"<>])", "") */.replaceAll("&rsquo", "'").replaceAll("&lsquo", "'").replaceAll("&ldquo", "\"")
					.replaceAll("&rdquo", "\"").replace("&39", "'").replaceAll("&8211", ".").replaceAll("&8216", "'").replaceAll("&8217", "'")
					.replaceAll("&hellip", "...").replaceAll("&8212", "--").replaceAll("&8230", "...").replaceAll("&mdash", "--").replaceAll("&ndash", "-")
					.replaceAll("&38", "&").replaceAll("&8220", "\"").replaceAll("&8221", "\"").replaceAll("&quot", "\"").replaceAll("&160", " ")
					.replaceAll("&amp", "&");

			if ("".equals(text)) {
				text = null;
			}
		} catch (Exception exception) {
			LOG.error("Inside Utility :  replaceRegExOfDescription : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return text;
	}

	/**
	 * method to get thumbnail of feed item.
	 * 
	 * @param fstElmnt
	 *            as input parameter.
	 * @return thumbnail.
	 * @throws NewsTemplateBatchProcessesException
	 */
	public static String getThumbnail(Element fstElmnt) throws NewsTemplateBatchProcessesException {
		String thumbnail = null;
		try {
			if (fstElmnt.getElementsByTagName("media:thumbnail") != null) {
				NodeList mediaThumbnail = fstElmnt.getElementsByTagName("media:thumbnail");
				if (mediaThumbnail.item(0) != null && mediaThumbnail.item(0).getAttributes() != null
						&& mediaThumbnail.item(0).getAttributes().getNamedItem("url") != null) {
					String thumbnailUrl = mediaThumbnail.item(0).getAttributes().getNamedItem("url").getNodeValue();
					thumbnail = thumbnailUrl;
				} else if (fstElmnt.getElementsByTagName("thumbnail") != null) {
					NodeList thumbnailList = fstElmnt.getElementsByTagName("thumbnail");
					Element thumbnailElement = (Element) thumbnailList.item(0);
					if (thumbnailElement != null) {
						thumbnailList = thumbnailElement.getChildNodes();
						thumbnail = ((Node) thumbnailList.item(0)).getNodeValue().replaceAll("([^\\w\\s\'.,-:&\"<>])", "");
					}
				}
			}
		} catch (Exception exception) {
			LOG.error("Inside Utility :  getThumbnail : " + exception.getMessage());
			throw new NewsTemplateBatchProcessesException(exception);
		}
		return thumbnail;
	}

}
