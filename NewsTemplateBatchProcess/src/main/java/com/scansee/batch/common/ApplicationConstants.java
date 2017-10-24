package com.scansee.batch.common;

/**
 * ApplicationConstants.
 * 
 * @author vaidehi.ne
 */
public class ApplicationConstants {

	/**
	 * MethodStart declared as String for logger messages.
	 */
	public static final String METHODSTART = "In side method >> ";
	/**
	 * MethodEnd declared as String for logger messages.
	 */
	public static final String METHODEND = "Exiting method >>";
	/**
	 * ExceptionOccurred declared as String for logger messages.
	 */
	public static final String EXCEPTIONOCCURRED = "Exception Occurred in  >>> ";

	public static final String SUCCESS = "Success";

	public static final String FAILURE = "Failure";

	public static final String SCHEMANAME = "dbo";

	/**
	 * This constant for retrieving Database error message.
	 */
	public static final String ERRORMESSAGE = "ErrorMessage";
	/**
	 * This constant for retrieving Database error code.
	 */
	public static final String ERRORNUMBER = "ErrorNumber";

	public static final String SMTP_PORT = "smtp_port";
	public static final String SMTP_SERVER = "smtp_server";
	public static final String EMAIL_SUBJECT = "subject";
	public static final String SENDER_TO_LIST = "sender_toList";
	public static final String FROM_MAIL = "from_emailId";
	public static final String BATCH_PROGRAM = "batch_program";
	public static final String HUBCITI_ID = "hubciti_id";
	public static final String MAIL_CONTENT = "mail_content_first_line";
	public static final String EMPTYLIST = "EmptyList";

	ApplicationConstants() {
		// constructor for class
	}

}
