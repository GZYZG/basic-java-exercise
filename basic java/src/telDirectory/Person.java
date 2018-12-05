package telDirectory;

import java.io.Serializable;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9137696618468634978L;
	private String name;
	private String tel;
	private String note;
	
	Person(){
		this.name = "bla";
		this.tel = "000";
		this.note = "beizhu";
	}
	
	Person(String name,String tel,String note){
		this.name = name;
		this.tel = tel;
		this.note = note;
	}
	
	Person(String name,String tel){
		this.name = name;
		this.tel = tel;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTel(String name) {
		this.tel = tel;
	}
	
	public void setNote(String name) {
		this.note = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTel() {
		return this.tel;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public boolean isSame(Person p) {
		if( this.name.equals(p.getName()) && this.tel.equals(p.getTel()) ){
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "name:"+this.name+" tel:"+ this.tel + " note:" + this.note;
	}
}