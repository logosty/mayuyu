package com.logosty.mayuyu.server.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * @author logosty(ganyingle) on 2021/2/22 10:50
 */
@Slf4j
public class Huaguxi {

  public static final String test = "E:\\临时-花鼓戏";

  public static final String root = test;

  //  public static final String test = "/Volumes/Untitled/test";
  static int global = 1;

  static Pattern p = Pattern.compile("^((\\d+\\.)+)(.*\\..*$)");

  public static void main(String[] args) throws IOException {
    String fileName = test;
    File f = new File(fileName);
    log.info("f.canWrite() {}", f.canWrite());

    File oldF = new File(root + File.separator + "old.txt");
    File newF = new File(root + File.separator + "new.txt");

    //if file doesnt exists, then create it
    if (!oldF.exists()) {
      oldF.createNewFile();
    } else {
      oldF.delete();
      oldF.createNewFile();
    }
    if (!newF.exists()) {
      newF.createNewFile();
    } else {
      newF.delete();
      newF.createNewFile();
    }

    try (
        FileWriter oldFileWritter = new FileWriter(oldF, true);
        FileWriter newFileWritter = new FileWriter(newF, true);
        BufferedWriter oldB = new BufferedWriter(oldFileWritter);
        BufferedWriter newB = new BufferedWriter(newFileWritter);
    ) {
      scan(f, oldB, newB);
    }
    log.info("sdf");
  }

  private static void scan(File f, BufferedWriter oldWritter, BufferedWriter newWritter)
      throws IOException {
    if (f == null || f.isHidden()) {
      return;
    }
    if (f.isDirectory()) {
      File[] fileArray = f.listFiles();
      if (fileArray != null) {
        Arrays.sort(fileArray);
        //目录
        oldWritter.newLine();
        oldWritter.append("目录："+f.getName());
        oldWritter.newLine();

        newWritter.newLine();
        newWritter.append("目录："+f.getName());
        newWritter.newLine();

        for (int i = 0; i < fileArray.length; i++) {
          // 递归调用
          scan(fileArray[i], oldWritter, newWritter);
        }
      }
    } else {
      System.out.println(f);
      int num = global++;
      String formatNum = String.format("%0" + 4 + "d", num);

      String name = f.getName();

      Matcher matcher = p.matcher(name);
      if (matcher.find() && matcher.groupCount() > 2) {
        name = matcher.group(3);
      }

      String newName = f.getParent() + File.separator + formatNum + "." + name;

//      newName=  f.getParent()+ File.separator  + name;

      File newFile = new File(newName);
      //目录
      oldWritter.append(f.getName());
      oldWritter.newLine();

      newWritter.append(newFile.getName());
      newWritter.newLine();

      //rename
      boolean b = f.renameTo(newFile);
      log.info("rename [{}] [{}] [{}]", f.getName(), newName, b);
    }
  }


}

