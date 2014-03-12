package com.leon.bean;

import java.util.ArrayList;

public class Folder {
	public String folderName;
	public String open;
	public ArrayList<Placemark> list;

	public Folder(String folderName) {
		super();
		this.folderName = folderName;
		this.list = new ArrayList<Placemark>();
	}

	@Override
	public String toString() {
		String folderLine = "";
		folderLine += "<Folder>";
		folderLine += "<name>" + this.folderName + "</name>";
		folderLine += "<open>0</open>";

		String listString = "";
		for (int i = 0; i < list.size(); i++) {
			listString += list.get(i).toString();
		}
		folderLine += listString;
		folderLine += "</Folder>";
		return folderLine;
	}

}
