package com.logosty.mayuyu.server.controller;

import com.logosty.mayuyu.server.service.DoWorkService;
import com.logosty.mayuyu.server.util.CSVUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author logosty(ganyingle) on 2020/1/13 17:20
 */
@Slf4j
@RestController
@RequestMapping(value = "/gateway")
public class GatewayController {

  @Autowired
  private DoWorkService doWorkService;

  private void printBegin() {
    log.info("begin debug {}", Thread.currentThread().getStackTrace()[2].getMethodName());
  }

  private void printEnd() {
    log.info("end debug {}", Thread.currentThread().getStackTrace()[2].getMethodName());
  }

  @GetMapping(value = "/testCSV")
  public String testCSV() throws IOException {
    String str = "hello, testCSV!";
    printBegin();
    log.info(str);
    printEnd();
    List<List<Object>> lists = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      List<Object> row = new ArrayList<>();
      for (int j = 0; j < 5; j++) {
        row.add(i + ":" + j);
      }
      lists.add(row);
    }

    List<Object> objects = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      objects.add("column" + i);
    }

    String stringDate = FastDateFormat.getInstance("MM-dd HH:mm")
        .format(System.currentTimeMillis());
    //定义文件类型
    String fileName = "开发者列表 excel" + File.separator + stringDate + ".csv";
    CSVUtils.createCSVFile(fileName, lists, objects);
    return str;
  }

  @GetMapping(value = "/hello")
  public String test() {
    String str = "hello, mayuyu!";
    printBegin();
    log.info(str);
    printEnd();
    return str;
  }

  @GetMapping(value = "/doWork")
  public void doWork() throws IOException {
    printBegin();
    int i = doWorkService.doWork();
    printEnd();
  }

}
