package br.com.eye.data;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Object that the monitor will receive from the clients with all the data.
 *
 * @author Marcelo de Souza Vieira
 *
 */
public class SonarData {

	@JsonProperty("description_")
	private String description;
	
	@JsonProperty("tags_")
	private String[] tags;
	
	@JsonProperty("typeFmt_")
	private String typeFmt;
	
	@JsonProperty("type_")
	private int type;
	
	@JsonProperty("timeInit_")
	private long timeInit;
	
	@JsonProperty("timeExec_")
	private long timeExec;
	
	@JsonProperty("error_")
	private boolean error;
	
	@JsonProperty("messageError_")
	private String messageError;
	
	@JsonProperty("server_")
	private String server;
	
	@JsonProperty("exception_")
	private String exception;
	
	@JsonProperty("version_")
	private String version;
	
	@JsonProperty("eye_")
	private boolean eye = true;
	
	@JsonProperty("messageStackTrace_")
	private String messageStackTrace;
	
	@JsonProperty("@timestamp")
	private String buildTimestamp;
	
	@JsonProperty("mOrigin_")
	private String mOrigin;
	
	@JsonProperty("mDestiny_")
	private String mDestiny;
	
	@JsonProperty("mLink_")
	private String mLink;
	
	// client
	@JsonProperty("client_")
	private String client;
	
	@JsonProperty("user_")
	private String user;
	
	@JsonProperty("timezone_")
	private String timezone;
	
	@JsonProperty("locale_")
	private String locale;
	
	@JsonProperty("flags_")
	private String flags;
	
	@JsonProperty("email_")
	private String email;
	
	// machine
	@JsonProperty("memory_")
	private String memory;
	
	@JsonProperty("configs_machine_")
	private String configs_machine;
	
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

	public String getBuildTimestamp() {
		return buildTimestamp;
	}

	public void setBuildTimestamp(String buildTimestamp) {
		this.buildTimestamp = buildTimestamp;
	}

	public boolean isEye() {
		return eye;
	}

	public void setEye(boolean eye) {
		this.eye = eye;
	}

	public String getmOrigin() {
		return mOrigin;
	}

	public void setmOrigin(String mOrigin) {
		this.mOrigin = mOrigin;
	}


	public String getmDestiny() {
		return mDestiny;
	}

	public void setmDestiny(String mDestiny) {
		this.mDestiny = mDestiny;
	}

	public String getmLink() {
		return mLink;
	}

	public void setmLink(String mLink) {
		this.mLink = mLink;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTypeFmt() {
		return typeFmt;
	}

	public void setTypeFmt(String typeFmt) {
		this.typeFmt = typeFmt;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getConfigs_machine() {
		return configs_machine;
	}

	public void setConfigs_machine(String configs_machine) {
		this.configs_machine = configs_machine;
	}

}
