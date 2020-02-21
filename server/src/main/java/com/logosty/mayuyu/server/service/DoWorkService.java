package com.logosty.mayuyu.server.service;

import com.logosty.mayuyu.server.dto.DeveloperListDTO;
import com.logosty.mayuyu.server.dto.DeveloperListDTO.Developer;
import java.io.IOException;
import java.util.List;

/**
 * @author logosty(ganyingle) on 2020/1/15 14:16
 */
public interface DoWorkService {

  int doWork() throws IOException;

  DeveloperListDTO getDeveloperListDTO();

  boolean getCrmResult(Developer developer);
}
