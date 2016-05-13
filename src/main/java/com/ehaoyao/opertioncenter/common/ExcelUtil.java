package com.ehaoyao.opertioncenter.common;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil {

	private static final String EXCEL_CELL_STYLE_TITLE = "TITLE";
	private static final String EXCEL_CELL_STYLE_CONTENT = "CONTENT";
	
	/**
	 * 获取Excel样式
	 * @param wb HSSFWorkbook
	 * @param type 样式类型
	 * @return
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook wb,String type){
		HSSFCellStyle style = null;
		if(type!=null && type.length()>0){
			if(ExcelUtil.EXCEL_CELL_STYLE_TITLE.equals(type)){
				style = getTitleCellStyle(wb);
			}
			if(ExcelUtil.EXCEL_CELL_STYLE_CONTENT.equals(type)){
				style = getContentCellStyle(wb);
			}
		}
		return style;
	}
	
	/**
	 * 获取标题样式
	 * @param wb HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getTitleCellStyle(HSSFWorkbook wb){
		
		HSSFCellStyle style = wb.createCellStyle();
		
		//设置Excel中的边框(表头的边框)
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setLocked(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		style.setWrapText(true);//设置自动换行
		
		String fontName = "Arial";
		HSSFFont font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font.setFontHeightInPoints((short) 10);
		
		style.setFont(font);
		
		return style;
	}
	
	/**
	 * 获取内容样式
	 * @param wb HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getContentCellStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		//设置Excel中的边框(表头的边框)
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//		style.setWrapText(true);//设置自动换行
		
		String fontName = "Arial";
		HSSFFont font = wb.createFont();
		font.setFontName(fontName);
		font.setFontHeightInPoints((short) 10);
		
		style.setFont(font);
		
		return style;
	}
}
