package com.will.complexservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

	private String name;
	private double weight;
	
	public Pet() {}
	
	public Pet(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeDouble(weight);
	}

	public static final Parcelable.Creator<Pet> CREATOR = new Parcelable.Creator<Pet>() {

		@Override
		public Pet createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Pet(source.readString(), source.readDouble());
		}

		@Override
		public Pet[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Pet[size];
		}
	};
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Pet [name=" + name + ", weight=" + weight;
	}
	
}
