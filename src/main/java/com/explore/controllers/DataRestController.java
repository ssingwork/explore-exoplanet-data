/**
 * @File EVVEController.java
 * Date			Author		Changes
 * -------		--------	--------
 * 22-May-2019		Vipin		Created
 *
 **/
package com.explore.controllers;

import com.explore.services.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DataRestController {

  private static Logger log = LoggerFactory.getLogger(DataRestController.class);


  @Autowired
  DataManager dataManager;


  @GetMapping(value = "/")
  public String welcome() {
      return "index";
  }



}
