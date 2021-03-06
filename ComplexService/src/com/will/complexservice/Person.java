package com.will.complexservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

	private int id;
	private String name;
	private String pass;
	
	public Person() {}
	
	public Person(int id, String name, String pass) {
		this.id = id;
		this.name = name;
		this.pass = pass;
	}
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name != null ? name.hashCode() : 0));
		result = prime * result + ((pass != null ? pass.hashCode() : 0));
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		
		if (pass == null) {
			if (other.pass != null) {
				return false;
			}
		}
		else if (!pass.equals(other.pass)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(pass);
	}

	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Person(source.readInt(), source.readString(), source.readString());
		}

		@Override
		public Person[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Person[size];
		}
	};
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
