package com.logosty.mayuyu.server.dto;

import java.util.List;
import lombok.Data;

/**
 * @author logosty(ganyingle) on 2020/1/15 16:14
 */
@Data
public class CrmListDTO {

  private List<Crm> crmList;

  @Data
  public static class Crm {

    private boolean gonghaichi;
    private boolean salesToFollowUp;
  }

}
