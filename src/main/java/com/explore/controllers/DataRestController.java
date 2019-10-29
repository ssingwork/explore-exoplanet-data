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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class DataRestController {

  private static Logger log = LoggerFactory.getLogger(DataRestController.class);


  @Autowired
  DataManager dataManager;


  @GetMapping(value = "/planets/orphan-count")
  public @ResponseBody
  Map<String,Object> orphanPlanetCount() {
    Map<String,Object> result = new HashMap<>();
    result.put("count",dataManager.getOrphanPlanetCount());
    return result;
  }

  @GetMapping(value = "/planets/hottest-star")
  public @ResponseBody
  Map<String,Object> findPlanetOrbitingHottestStar() {
    Map<String,Object> result = new HashMap<>();
    result.put("planet-list",dataManager.findPlanetOrbitingHottestStar());
    return result;
  }

  @GetMapping(value = "/planets/discovery-timeline-by-year-size")
  public @ResponseBody
  Map<String,Object> getDiscoverTimelineByYear() {
    Map<String,Object> result = new HashMap<>();
    result.put("timeline-list",dataManager.getDiscoverTimelineByYear());
    return result;
  }



}
