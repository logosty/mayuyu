package com.logosty.mayuyu.server.controller;

import lombok.extern.slf4j.Slf4j;
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

  private void printBegin() {
    log.info("begin debug {}", Thread.currentThread().getStackTrace()[2].getMethodName());
  }

  private void printEnd() {
    log.info("end debug {}", Thread.currentThread().getStackTrace()[2].getMethodName());
  }

  @GetMapping(value = "/hello")
  public void test() {
    printBegin();
    log.info("hello, mayuyu");
    printEnd();
  }

}
