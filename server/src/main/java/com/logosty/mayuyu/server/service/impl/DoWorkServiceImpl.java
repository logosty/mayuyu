package com.logosty.mayuyu.server.service.impl;

import com.google.common.collect.ImmutableList;
import com.logosty.mayuyu.server.dto.DeveloperListDTO;
import com.logosty.mayuyu.server.dto.DeveloperListDTO.Developer;
import com.logosty.mayuyu.server.dto.DeveloperListDTO.DeveloperListRespDTO;
import com.logosty.mayuyu.server.service.DoWorkService;
import com.logosty.mayuyu.server.util.CSVUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author logosty(ganyingle) on 2020/1/15 14:24
 */
@Service
@Slf4j
public class DoWorkServiceImpl implements DoWorkService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${url.developerList}")
  private String developerListUrl;

  @Value("${url.crm}")
  private String crmUrl;

  @Override
  public int doWork() throws IOException {

    DeveloperListDTO developerListDTO = this.getDeveloperListDTO();
    if (developerListDTO == null || developerListDTO.getDeveloperList() == null) {
      log.error("get developerList null!");
      throw new RuntimeException("没有查询到开发者列表数据！");
    }
    //crmFileName
    String stringDate = FastDateFormat.getInstance("MM-dd HH:mm")
        .format(System.currentTimeMillis());
    String fileName = "开发者列表 excel" + File.separator + stringDate + ".csv";

    //excel
    List<Developer> developerList = developerListDTO.getDeveloperList();
    List<List<Object>> dataLists = developerList.stream().map(this::covert)
        .collect(Collectors.toList());

    List<Object> head = ImmutableList.builder()
        .add("id", "邮箱", "公司名称", "联系人", "联系电话", "注册时间", "注册页面", "来源", "销售负责人", "开发者名称")
        .build();
    CSVUtils.createCSVFile(fileName, dataLists, head);

    //crm
    List<Object> crmHead = ImmutableList.builder()
        .add("id", "邮箱", "公司名称", "联系人", "联系电话", "注册时间", "注册页面", "来源", "销售负责人", "开发者名称", "是否销售跟进")
        .build();
    List<List<Object>> rcmDataLists = new ArrayList<>();
    String crmFileName = "开发者crm结果表 excel" + File.separator + "crm" + stringDate + ".csv";
    CSVUtils.createCSVFile(crmFileName, rcmDataLists, crmHead);
    for (int i = 0; i < developerList.size(); i++) {
      boolean isSalesToFollowUp = false;
      if (this.getCrmResult(developerList.get(i))) {
        isSalesToFollowUp = true;
      }

      List<Object> objects = dataLists.get(i);
      objects.add(isSalesToFollowUp);

      CSVUtils.append(crmFileName, objects);
    }
    return developerList.size();
  }


  @Override
  public DeveloperListDTO getDeveloperListDTO() {
//    DeveloperListDTO developerListDTO = new DeveloperListDTO();
////    List<Developer> developerList = new ArrayList<>();
////    for (int i = 0; i < 1000; i++) {
////      Developer developer = new Developer();
////      developer.setId(i);
////      developer.setCompanyName("公司" + i);
////      developer.setContactPerson("联系人" + i);
////      developer.setContactPhoneNumber("联系手机" + i);
////      developer.setDevelopersName("开发者" + i);
////      developer.setEmail("邮箱" + i);
////      developer.setRegistrationPage("setRegistrationPage" + i);
////      developer.setRegistrationTime("setRegistrationTime" + i);
////      developer.setSource("setSource" + i);
////
////      if (RandomUtils.nextBoolean()) {
////        developer.setSalesDirector("setSalesDirector" + i);
////      }
////      developerList.add(developer);
////    }
////
////    developerListDTO.setDeveloperList(developerList);
////    return developerListDTO;
    RestTemplate restTemplate = new RestTemplate();
    String developerListUrl = "http://devsvc.jpushoa.com/iportal-dev/v1/dev/list";

    UriComponents uriComponents = UriComponentsBuilder
        .fromHttpUrl(developerListUrl)
        .queryParam("pageIndex", 1)
        .queryParam("pageSize", 50)
        .build();
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.add("Authorization",
        "sessionId=30add607d56031574debdc6271b931c9027bde1ac2bc62351fdb646eb4845d42c875d0b23141109aefcb14c3a1d01c50214a0b8d66eddd3cb3a00fc2f5bac7fd3843b021d52088882a40cf8272e3e1baca9681217f71363c7697df34115c0aaf55b8f863038389922a1a2e4a988c5e0599e4e47d425469c9c81fd0606125d75d256e1e707da3cf6ac8e1382413e0b1b8bff7116f8b7381be58279b9ffa24174091340cc163ee94ca2a0671c3d6f87497225a4cd7216848da39ddcd54730d8f884afe63eef6b30316dedc939e791181c012a42d7488d0d903a5eeec323069ad82_3103");
    requestHeaders.add("Cookie",
        "JSESSIONID=3083D3EE1B881996A7E25F8A1C29F0F0; SESSION_ID=30add607d56031574debdc6271b931c9027bde1ac2bc62351fdb646eb4845d42c875d0b23141109aefcb14c3a1d01c50214a0b8d66eddd3cb3a00fc2f5bac7fd3843b021d52088882a40cf8272e3e1baca9681217f71363c7697df34115c0aaf55b8f863038389922a1a2e4a988c5e0599e4e47d425469c9c81fd0606125d75d256e1e707da3cf6ac8e1382413e0b1b8bff7116f8b7381be58279b9ffa24174091340cc163ee94ca2a0671c3d6f87497225a4cd7216848da39ddcd54730d8f884afe63eef6b30316dedc939e791181c012a42d7488d0d903a5eeec323069ad82_3103");

    requestHeaders.add("Referer",
        "http://devsvc.jpushoa.com/plugeserver/developer/?stamp=1579089319084&projectId=1");
    requestHeaders.add("User-Agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36");
    requestHeaders.add("x-j-referer",
        "http://devsvc.jpushoa.com/plugeserver/developer/?stamp=1579089319084&projectId=1#/dev/list");
    requestHeaders.add("Host", "devsvc.jpushoa.com");

    //body
    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

    HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody,
        requestHeaders);

    ResponseEntity<String> response = restTemplate
        .exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, String.class);

    DeveloperListDTO developerListDTO = restTemplate
        .getForObject(uriComponents.toUri(), DeveloperListDTO.class);
    return developerListDTO;
  }

  @Override
  public boolean getCrmResult(Developer developer) {
    if (RandomUtils.nextInt(0, 10) < 2) {
      return false;
    }
    return true;

//    UriComponents uriComponents = UriComponentsBuilder
//        .fromHttpUrl(crmUrl)
//        .queryParam("from", "from")
//        .queryParam("area", "area")
//        .build();
//
//    CrmListDTO crmListDTO = restTemplate
//        .getForObject(uriComponents.toUri(), CrmListDTO.class);
//    if (crmListDTO == null || crmListDTO.getCrmList() == null) {
//      log.warn("get developerList null!");
//    }
//
//    return true;
  }

  private List<Object> covert(Developer developer) {
    List<Object> list = new ArrayList<>();
    list.add(developer.getDevId());
    list.add(developer.getEmail());
    list.add(developer.getCompanyName());
    list.add(developer.getContact());
    list.add(developer.getPhone());
    list.add(developer.getCreateTime());
    list.add(developer.getRegPage());
    list.add(developer.getRegFrom());
    list.add(developer.getSalesName());
    list.add(developer.getDevName());
    return list;
  }


  public static void main(String[] args) {
    DoWorkService doWorkService = new DoWorkServiceImpl();
    doWorkService.getDeveloperListDTO();
  }

}
