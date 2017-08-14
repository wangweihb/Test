
package com.ymhw.website.utils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.jfinal.plugin.activerecord.Model;

public class ExcelExportUtil
{

	/**
	 * @param response
	 * @param request
	 * @param filename
	 *            导出的文件名
	 * @param titles
	 *            标题列和列名的对应.column:列名,title标题名
	 * @param records
	 *            记录
	 */
	@SuppressWarnings("unchecked")
	public static <M extends Model<M>> void exportByRecord(HttpServletResponse response, HttpServletRequest request,
			String filename, List<Pair> titles, List<M> records)
	{
		exportByRecord(response, request, filename, new SheetData<M>(titles, records));
	}

	/**
	 * @param response
	 * @param request
	 * @param filename
	 *            导出的文件名
	 * @param sheetDatas
	 *            产生一个sheet需要的数据
	 */
	public static <M extends Model<M>> void exportByRecord(HttpServletResponse response, HttpServletRequest request,
			String filename, SheetData<M>... sheetDatas)
	{

		HSSFWorkbook wb = new HSSFWorkbook();

		// 标题行的style
		CellStyle titleCellStyle = wb.createCellStyle();
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		titleCellStyle.setWrapText(true); // 自动换行
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 加粗
		font.setFontName("微软雅黑");
		titleCellStyle.setFont(font);

		// 内容行的style
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
		cellStyle.setWrapText(true);
		Font font2 = wb.createFont();
		font2.setFontName("微软雅黑");
		cellStyle.setFont(font2);

		// 多张sheet
		for (SheetData<M> sheetData : sheetDatas)
		{
			List<Pair> titles = sheetData.titles;

			List<M> records = sheetData.records;

			HSSFSheet sheet = wb.createSheet();

			int rowIndex = 0, cellIndex = 0;

			HSSFRow row = null;
			HSSFCell cell = null;

			// 创建标题行
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 450);
			rowIndex++;

			for (Pair pair : titles)
			{

				cell = row.createCell(cellIndex);
				cell.setCellStyle(titleCellStyle); // 设置样式
				cellIndex++;

				cell.setCellValue(pair.title);
			}

			// 处理每一行
			for (M record : records)
			{

				row = sheet.createRow(rowIndex);
				row.setHeight((short) 450);
				rowIndex++;
				cellIndex = 0;

				for (Pair pair : titles)
				{

					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle); // 设置样式
					cellIndex++;

					Object value = record.get(pair.column);

					if (value != null)
					{

						cell.setCellValue(value.toString());
					}
				}
			}
		}

		// 序号
		writeStream(filename, wb, response, request);
	}

	/**
	 * 写到输出流
	 */
	private static void writeStream(String filename, HSSFWorkbook wb, HttpServletResponse response,
			HttpServletRequest request)
	{

		try
		{
			String agent = request.getHeader("USER-AGENT");

			filename += ".xls";

			filename.replaceAll("/", "-");
			// filename = new String(filename.getBytes("gbk"),"ISO8859_1");

			if (agent.toLowerCase().indexOf("firefox") > 0)
			{
				filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
			}
			else
			{
				filename = URLEncoder.encode(filename, "UTF-8");
			}

			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 标题列和列名的对应
	 */
	public static class Pair
	{
		public String	column;

		public String	title;

		public Pair(String column, String title)
		{
			this.column = column;

			this.title = title;

		}
	}

	/**
	 * 创建一个sheet需要的数据
	 */
	public static class SheetData<M extends Model<M>>
	{
		public List<Pair>	titles;
		public List<M>		records;

		public SheetData(List<Pair> titles, List<M> records)
		{
			this.titles = titles;

			this.records = records;
		}
	}
}
