package com.scansee.batch.exception;

/**
 * Generic exception class. this will be thrown whenevr excpetions occurs in the
 * Service and controller layers of the application.
 * 
 * @author vaidehi.ne
 */

public class NewsTemplateBatchProcessesException extends Exception {

	/**
	 * serialVersionUID declared as long.
	 */

	private static final long serialVersionUID = -7685698697832539488L;
	/**
	 * errorCode declared as int.
	 */
	private int errorCode;
	/**
	 * myException is an Throwable obj.
	 */
	private Throwable myException;

	// private String errorMessage = null;
	/**
	 * Constructor.
	 */
	public NewsTemplateBatchProcessesException() {
		super();
	}

	/**
	 * constructor.
	 * 
	 * @param errorMessage
	 *            String
	 * @param errorCause
	 *            Throwable
	 */
	public NewsTemplateBatchProcessesException(String errorMessage, Throwable errorCause) {
		super(errorMessage, errorCause);
	}

	/**
	 * constructor.
	 * 
	 * @param errorMessage
	 *            .
	 * @param errorCode
	 *            .
	 * @param errorCause
	 *            .
	 */
	public NewsTemplateBatchProcessesException(String errorMessage, int errorCode, Throwable errorCause) {
		super(errorMessage, errorCause);
		this.errorCode = errorCode;
	}

	/**
	 * Constructor.
	 * 
	 * @param errorMessage
	 *            String
	 */
	public NewsTemplateBatchProcessesException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor.
	 * 
	 * @param errorCause
	 *            Throwable
	 */
	public NewsTemplateBatchProcessesException(Throwable errorCause) {
		super(errorCause);
	}

	/**
	 * getter for error code.
	 * 
	 * @return int
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * setter for error code.
	 * 
	 * @param errorCode
	 *            int
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * getter for myException.
	 * 
	 * @return Throwable
	 */
	public Throwable getMyException() {
		return myException;
	}

	/**
	 * setter for myException.
	 * 
	 * @param myException
	 *            Throwable
	 */
	public void setMyException(Throwable myException) {
		this.myException = myException;
	}
}
