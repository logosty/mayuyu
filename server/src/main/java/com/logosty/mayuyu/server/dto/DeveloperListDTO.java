package com.logosty.mayuyu.server.dto;

import java.util.List;
import lombok.Data;

/**
 * @author logosty(ganyingle) on 2020/1/15 14:33
 */
@Data
public class DeveloperListDTO {


  List<Developer> developerList;

  @Data
  public static class Developer {

    private int devId;
    private String email;
    private String companyName;
    private String contact;
    private String phone;
    private String createTime;
    private String regPage;
    private String regFrom;
    private String salesName;
    private String devName;
    private boolean salesToFollowUp;
  }

  @Data
  public static class DeveloperListRespDTO {

    int code;
    Content content;

  }

  private static class Content {

    List<Developer> data;;
  }
}


