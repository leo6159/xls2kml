package com.leon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.leon.run.RunTest;

public class ReadExcelFile  {
	public static ArrayList<String> readit(MainFrame mf,String filepath) throws Exception {
		ArrayList<String> al = new ArrayList<String>();
		try {

			InputStream is = new FileInputStream(filepath);

			al = ReadExcelFile.extractTextFromXLS(mf,is);

		} catch (OfficeXmlFileException e) {
			try {
				al = ReadExcelFile.extractTextFromXLS2007(mf,filepath);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		return al;
	}

	
	@SuppressWarnings("deprecation")
	private static ArrayList<String> extractTextFromXLS(MainFrame mf,InputStream is) throws IOException {
		mf.log(RunTest.bundle.getString("xls_parse2003Start"));
//		StringBuffer content = new StringBuffer();
		ArrayList<String> al= new ArrayList<String>();
		
		HSSFWorkbook workbook = new HSSFWorkbook(is); //������Excel�������ļ�������    

		for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
			if (null != workbook.getSheetAt(numSheets)) {
				HSSFSheet aSheet = workbook.getSheetAt(numSheets); //���һ��sheet   

				for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
					if (null != aSheet.getRow(rowNumOfSheet)) {
						HSSFRow aRow = aSheet.getRow(rowNumOfSheet); //���һ��   
						
						String rowText = "";
						for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
							if (null != aRow.getCell(cellNumOfRow)) {
								HSSFCell aCell = aRow.getCell(cellNumOfRow); //�����ֵ   
								String colText = "";
								if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									colText += aCell.getNumericCellValue();
//									content.append(aCell.getNumericCellValue());
								} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
									colText += aCell.getBooleanCellValue();
									//content.append(aCell.getBooleanCellValue());
								} else {
									colText += aCell.getStringCellValue();
//									content.append(aCell.getStringCellValue());
								}
								if(rowText.equals("")){
									rowText = colText;
								}else{
									rowText += ","+colText;
								}
							}
						}
						al.add(rowText);
					}
				}
				
			}
		}
		mf.log(RunTest.bundle.getString("xls_parse2003Finish"));
		mf.log(MessageFormat.format(RunTest.bundle.getString("xls_parseConut"), al.size()));
		return al;
	}

	/**  
	 * @Method: extractTextFromXLS2007  
	 * @Description: ��excel 2007�ĵ�����ȡ���ı�  
	 *  
	 * @param   
	 * @return String  
	 * @throws  
	 */
	private static ArrayList<String> extractTextFromXLS2007(MainFrame mf,String fileName) throws Exception {
		mf.log(RunTest.bundle.getString("xls_parse2003Start"));
//		StringBuffer content = new StringBuffer();
		ArrayList<String> al= new ArrayList<String>();
		//���� XSSFWorkbook ����strPath �����ļ�·��       
		XSSFWorkbook xwb = new XSSFWorkbook(fileName);

		//ѭ��������Sheet   
		for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
			XSSFSheet xSheet = xwb.getSheetAt(numSheet);
			if (xSheet == null) {
				continue;
			}

			//ѭ����Row   
			for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				XSSFRow xRow = xSheet.getRow(rowNum);
				if (xRow == null) {
					continue;
				}

				//ѭ����Cell   
				String rowText = "";
				for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
					XSSFCell xCell = xRow.getCell(cellNum);
					if (xCell == null) {
						continue;
					}
					String colText = "";
					if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						colText += xCell.getBooleanCellValue();
//						content.append(xCell.getBooleanCellValue());
					} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						colText += xCell.getNumericCellValue();
//						content.append(xCell.getNumericCellValue());
					} else {
						colText += xCell.getStringCellValue();
//						content.append(xCell.getStringCellValue() + ",");
					}
					if(rowText.equals("")){
						rowText = colText;
					}else{
						rowText += ","+colText;
					}
				}
				al.add(rowText);
			}
		}
		mf.log(RunTest.bundle.getString("xls_parse2007Finish"));
		mf.log(MessageFormat.format(RunTest.bundle.getString("xls_parseConut"), al.size()));
		return al;
	}
}
