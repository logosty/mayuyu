package com.logosty.mayuyu.server.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVUtils {

  /**
   * * 创建CSV文件类型  * @param dataLists  * @return
   */
  public static File createCSVFile(String fileName, List<List<Object>> dataLists,
      List<Object> headList) throws IOException {

    File csvFile = null;

    csvFile = new File(fileName);
    BufferedWriter csvWrite = null;
    try {
      //去文件目录
      File parent = csvFile.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }

      //创建文件
      csvFile.createNewFile();

      csvWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
          1024);

      //写入表头
      write(headList, csvWrite);
      //写入数据

      for (List<Object> dataList : dataLists) {
        write(dataList, csvWrite);
      }
      csvWrite.flush();
    } catch (IOException e) {
      throw new IOException("文件生成失败");
    } finally {

      try {
        csvWrite.close();

      } catch (IOException e) {

        throw new IOException("关闭文件流失败");
      }
    }

    return csvFile;
  }

  public static File append(String fileName, List<Object> dataList) throws IOException {
    File csvFile = null;

    csvFile = new File(fileName);
    BufferedWriter csvWrite = null;
    try {
      csvWrite = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(csvFile, true), "GB2312"),
          1024);

      //写入数据
      write(dataList, csvWrite);
      csvWrite.flush();
    } catch (IOException e) {
      throw new IOException("文件写入失败");
    } finally {

      try {
        csvWrite.close();

      } catch (IOException e) {

        throw new IOException("关闭文件流失败");
      }
    }

    return csvFile;
  }

  /**
   * * 将数据按行写入数据  *  * @param dataList
   *
   * @param csvWrite * @throws IOException
   */


  private static void write(List<Object> dataList, BufferedWriter csvWrite) throws IOException {

    for (Object data : dataList) {
      StringBuffer buffer = new StringBuffer();
      String rowStr = buffer.append("\"").append(data).append("\",").toString();
      csvWrite.write(rowStr);
//       csvWreite.newLine();
    }
    csvWrite.newLine();
  }

}