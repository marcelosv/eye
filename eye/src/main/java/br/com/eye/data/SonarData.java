package br.com.eye.data;

/**
 * Object that the monitor will receive from the clients with all the data.
 *
 * @author Marcelo de Souza Vieira
 *
 */
public class SonarData {

	private String description;
	private String[] tags;
	private int type;
	private long timeInit;
	private long timeExec;
	private boolean error;
	private String messageError;
	private String client;
	private String server; // sistema
	private String exception;
	private String version;
	private String messageStackTrace;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getTimeInit() {
		return timeInit;
	}

	public void setTimeInit(long timeInit) {
		this.timeInit = timeInit;
	}

	public long getTimeExec() {
		return timeExec;
	}

	public void setTimeExec(long timeExec) {
		this.timeExec = timeExec;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMessageStackTrace() {
		return messageStackTrace;
	}

	public void setMessageStackTrace(String messageStackTrace) {
		this.messageStackTrace = messageStackTrace;
	}
}
