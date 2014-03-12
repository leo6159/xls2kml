package com.leon;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.leon.bean.Folder;
import com.leon.bean.Placemark;
import com.leon.run.RunTest;

public class GenerateKML {
	public static String GenerateCotent(MainFrame mf, ArrayList<String> al) {
		ArrayList<Folder> folderList;
		StringBuffer content = new StringBuffer();
		if (al == null) {
			return content.toString();
		} else if (al.size() <= 0) {
			return content.toString();
		} else {
			content.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><kml xmlns=\"http://earth.google.com/kml/2.1\">");
			
			// 带有目录
			if (mf.jcb.isSelected()) {
				content.append("<Folder>");	
				int total = 0;
				folderList = new ArrayList<Folder>();

				for (int i = 0; i < al.size(); i++) {
					String c = al.get(i);
					String[] sp = c.split(",");
					// 不足四列
					if (sp.length < 4) {
						mf.log(RunTest.bundle.getString("xls_columeErrorKey")+RunTest.bundle.getString("wrongRecordIsKey"));
						mf.log(c);
						continue;
					}
					try {
						Double lng;
						Double lat;
						String folderName;
						String placeName;
						try {
							folderName = sp[0];
							placeName = sp[1];
							lng = Double.parseDouble(sp[2]);
							lat = Double.parseDouble(sp[3]);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							mf.log(RunTest.bundle.getString("xls_lonlatErrorKey")+RunTest.bundle.getString("wrongRecordIsKey"));
							mf.log(c);
							continue;
						}
						Placemark pm = new Placemark(placeName, lng, lat);
						boolean equls = false;
						// 遍历文件夹列表
						for (int j = 0; j < folderList.size(); j++) {
							Folder ft = folderList.get(j);
							if (ft.folderName.trim().equals(folderName.trim())) {
								equls = true;
								if (ft.list == null) {
									ft.list = new ArrayList<Placemark>();
								}
								ft.list.add(pm);
							}
						}
						// 在已有的列表中没有该目录名
						if (!equls) {
							Folder fd = new Folder(folderName);
							fd.list.add(pm);
							folderList.add(fd);
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						mf.log(RunTest.bundle.getString("wrongRecordIsKey")+RunTest.bundle.getString("wrongRecordIsKey"));
						mf.log(c);
						continue;
					}
					total++;
				}
				for (int k = 0; k < folderList.size(); k++) {
					content.append(folderList.get(k).toString());
				}
				content.append("</Folder></kml>");
				mf.log(MessageFormat.format(RunTest.bundle.getString("xls_FinishWithCountKey"),total));
			}
			// 没有目录
			else {
				content.append("<Folder>");
				int total = 0;
				for (int i = 0; i < al.size(); i++) {
					// TODO:没有文件夹
					String line = "";
					String c = al.get(i);
					String[] sp = c.split(",");
					// 不足三列
					if (sp.length < 3) {
						mf.log(RunTest.bundle.getString("xls_columeErrorKey")+RunTest.bundle.getString("wrongRecordIsKey"));
						mf.log(c);
						continue;
					}
					try {
//						line += "<name>" + sp[0] + "</name>";
//						line += "<Snippet maxLines=\"0\"></Snippet>"
//								+ " <styleUrl>#Style_icon</styleUrl>";

						Double lng;
						Double lat;
						String placeName;
						try {
							placeName = sp[0];
							lng = Double.parseDouble(sp[1]);
							lat = Double.parseDouble(sp[2]);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							mf.log(RunTest.bundle.getString("xls_lonlatErrorKey")+RunTest.bundle.getString("wrongRecordIsKey"));
							mf.log(c);
							continue;
						}
						
						Placemark pm = new Placemark(placeName, lng, lat);
						
						line += pm.toString();
						
						
//						line += "<Point><coordinates>" + lng + "," + lat
//								+ "</coordinates></Point>";
//						line += "</Placemark>";
					} catch (ArrayIndexOutOfBoundsException e) {
						mf.log(RunTest.bundle.getString("xls_missingContentKey")+RunTest.bundle.getString("wrongRecordIsKey"));
						mf.log(c);
						continue;
					}
					content.append(line);
					total++;

				}
				content.append("</Folder></kml>");
				mf.log(MessageFormat.format(RunTest.bundle.getString("xls_FinishWithCountKey"),total));
			}
		}

		return content.toString();
	}
}
