package br.com.eye.data;

public enum TypesData {

	USER(1),
	SERVICE(2),
	API_ENDPOINT(3),
	UNINFORMED(4),
	QUEUE(5),
	HQL(6),
	SQL(7),
	DEPENDENCY(8)
	;
	
	private int value;
	
	private TypesData(int value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value+"";
	}
	
	public int getValue(){
		return value;
	}
}
