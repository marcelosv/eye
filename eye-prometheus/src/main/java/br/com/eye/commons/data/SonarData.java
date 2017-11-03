package br.com.eye.commons.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Object that the monitor will receive from the clients with all the data.
 *
 * @author Marcelo de Souza Vieira
 */
public class SonarData {

	@JsonProperty("description_")
	private String description;

	@JsonProperty("typeFmt_")
	private String typeFmt;

	@JsonProperty("type_")
	private int type;

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

	@JsonProperty("instance_id")
	private String instanceId;

	@JsonProperty("etag")
	private String etag;

	private static String[] names = null;
	private static Map<String, String> namesMethods = null;

	public static String[] getValues(SonarData sonarData) {

		String[] values = new String[names.length];

		int loop = 0;
		for (String item : names) {
			try {
				Object obj = SonarData.class.getDeclaredField( namesMethods.get( item ) ).get( sonarData );
				values[loop++] = obj == null ? "" : obj.toString();
			} catch (Exception e) {
				values[loop++] = "";
			}
		}

		return values;
	}

	public static String[] getNames() {

		if (names != null) {
			return names;
		}

		Field[] fields = SonarData.class.getDeclaredFields();

		names = new String[fields.length-2];
		namesMethods = new HashMap();

		int loop = 0;
		for (Field item : fields) {
			JsonProperty[] jsonPropertys = item.getAnnotationsByType( JsonProperty.class );
			if( jsonPropertys == null || jsonPropertys.length <= 0){
				continue;
			}

			JsonProperty jsonProperty = jsonPropertys[0];

			names[loop++] = jsonProperty.value();
			namesMethods.put( jsonProperty.value(), item.getName() );
		}

		return names;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeFmt() {
		return typeFmt;
	}

	public void setTypeFmt(String typeFmt) {
		this.typeFmt = typeFmt;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public boolean isEye() {
		return eye;
	}

	public void setEye(boolean eye) {
		this.eye = eye;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public static void setNames(String[] names) {
		SonarData.names = names;
	}

	public static Map<String, String> getNamesMethods() {
		return namesMethods;
	}

	public static void setNamesMethods(Map<String, String> namesMethods) {
		SonarData.namesMethods = namesMethods;
	}
}
