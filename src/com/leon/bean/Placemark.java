package com.leon.bean;

public class Placemark {
	public String name;
	public Double lng;
	public Double lat;

	public Placemark(String name, Double lng, Double lat) {
		super();
		this.name = name;
		this.lng = lng;
		this.lat = lat;
	}

	@Override
	public String toString() {
		String line = "";
		line += "<Placemark>";
		line += "<name>" + this.name + "</name>";
		line += "<Point><coordinates>" + this.lng + "," +this.lat + "</coordinates></Point>";
		line += "</Placemark>";
		return line;
	}
}
