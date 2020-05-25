/*
 * Creation : Jan 5, 2017
 */
package com.inetpsa.poc00.application.email;

import java.util.List;

/**
 * The Class Email.
 */
public final class Email {

	/** The to. */
	private List<String> to;

	/** The pdf path. */
	private String pdfPath;

	/** The subject. */
	private String subject;

	/** The host. */
	private String host;

	/** The port. */
	private String port;

	/** The from. */
	private String from;

	/** The user name. */
	private final String userName;

	/** The password. */
	private final String password;

	/** The file name. */
	private final String fileName;

	/**
	 * Instantiates a new email.
	 *
	 * @param builder the builder
	 */
	private Email(EmailBuilder builder) {
		super();
		this.to = builder.to;
		this.pdfPath = builder.pdfPath;
		this.subject = builder.subject;
		this.host = builder.host;
		this.port = builder.port;
		this.from = builder.from;
		this.userName = builder.userName;
		this.password = builder.password;
		this.fileName = builder.fileName;
	}

	/**
	 * The Class EmailBuilder.
	 */
	public static class EmailBuilder {

		/** The to. */
		private List<String> to;

		/** The pdf path. */
		private String pdfPath;

		/** The subject. */
		private String subject;

		/** The host. */
		private String host;

		/** The port. */
		private String port;

		/** The from. */
		private String from;

		/** The user name. */
		private final String userName;

		/** The password. */
		private final String password;

		/** The file name. */
		private final String fileName;

		/**
		 * Instantiates a new email builder.
		 *
		 * @param userName the user name
		 * @param password the password
		 * @param fileName the file name
		 */
		public EmailBuilder(String userName, String password, String fileName) {
			super();
			this.userName = userName;
			this.password = password;
			this.fileName = fileName;
		}

		/**
		 * Sets the to.
		 *
		 * @param to the to
		 * @return the email builder
		 */
		public EmailBuilder setTo(List<String> to) {
			this.to = to;
			return this;
		}

		/**
		 * Sets the pdf path.
		 *
		 * @param pdfPath the pdf path
		 * @return the email builder
		 */
		public EmailBuilder setPdfPath(String pdfPath) {
			this.pdfPath = pdfPath;
			return this;
		}

		/**
		 * Sets the subject.
		 *
		 * @param subject the subject
		 * @return the email builder
		 */
		public EmailBuilder setSubject(String subject) {
			this.subject = subject;
			return this;
		}

		/**
		 * Sets the host.
		 *
		 * @param host the host
		 * @return the email builder
		 */
		public EmailBuilder setHost(String host) {
			this.host = host;
			return this;
		}

		/**
		 * Sets the port.
		 *
		 * @param port the port
		 * @return the email builder
		 */
		public EmailBuilder setPort(String port) {
			this.port = port;
			return this;
		}

		/**
		 * Sets the from.
		 *
		 * @param from the from
		 * @return the email builder
		 */
		public EmailBuilder setFrom(String from) {
			this.from = from;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the email
		 */
		public Email build() {

			return new Email(this);
		}
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	/*
	 * 
	 * 
	 * All getters no setters
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * Gets the pdf path.
	 *
	 * @return the pdf path
	 */
	public String getPdfPath() {
		return pdfPath;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

}
