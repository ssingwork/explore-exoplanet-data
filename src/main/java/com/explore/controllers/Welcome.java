/**
 * @File EVVEController.java
 * Date			Author		Changes
 * -------		--------	--------
 * 22-May-2019		Vipin		Created
 *
 **/
package com.explore.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Welcome {

  private static Logger log = LoggerFactory.getLogger(Welcome.class);


  @GetMapping(value = "/")
  public String welcome() {
      return "index";
  }



}
