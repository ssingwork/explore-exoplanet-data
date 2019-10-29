package com.explore.services.impl;

import com.explore.domains.DiscoveryTimelineAgg;
import com.explore.domains.Planet;
import com.explore.services.DataManager;
import com.explore.utils.DownloadUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DataManagerImpl extends BaseDataManager implements DataManager {

    private static Logger logger = LoggerFactory.getLogger(DataManagerImpl.class);

    private static File downloadedJsonFile = null;
    private static Object downloadedJsonFileLock = new Object();

    private Environment environment;

    public DataManagerImpl(Environment environment) {
        this.environment = environment;
    }

//    @Override
    public int getOrphanPlanetCountStream() {
        int orphanPlanetCnt =0;
        JsonParser parser = null;
        try {
            downloadJsonFileIfNotAlready();
            JsonFactory jsonFactory = new JsonFactory();
            parser = jsonFactory.createParser(getDownloadedFileName());
            JsonToken jsonToken = parser.nextToken();

            while (jsonToken != JsonToken.END_ARRAY) {
                String fieldname = parser.getCurrentName();
                String typeFlag = "";
                if ("TypeFlag".equals(fieldname)) {
                    parser.nextToken();
                    typeFlag = parser.getText();
                    //logger.info("Type flag {}",typeFlag);
                    if(StringUtils.isNotBlank(typeFlag) && Integer.parseInt(typeFlag)==3){
                        orphanPlanetCnt++;
                    }
                }
                jsonToken = parser.nextToken();
            }

            parser.close();

        }catch (Exception e){
            logger.error("Problem counting orphan planet ",e);
            throw new RuntimeException(e);
        }finally {
            if(parser != null && !parser.isClosed()){
                try {
                    parser.close();
                } catch (IOException e) {
                    logger.warn("Parser didn't close properly {} ",e.getMessage());
                }
            }
        }
        logger.info("orphanPlanetCnt {} ",orphanPlanetCnt);
        return orphanPlanetCnt;
    }

    @Override
    public int getOrphanPlanetCount() {
        int orphanPlanetCnt =0;

        try {
            downloadJsonFileIfNotAlready();
            List<Planet> data = parsePlanetJSON();

            orphanPlanetCnt = (int)data.stream().filter(d-> d.getTypeFlag()==3).count();

        }catch (Exception e){
            logger.error("Problem counting orphan planet ",e);
            throw new RuntimeException(e);
        }
        logger.info("orphanPlanetCnt {} ",orphanPlanetCnt);
        return orphanPlanetCnt;
    }

    private  void downloadJsonFileIfNotAlready() {

       if(downloadedJsonFile == null) {
           synchronized (downloadedJsonFileLock) {
               if (downloadedJsonFile == null) {
                    String jsonURL = environment.getRequiredProperty("explore.input.url");
                    String downloadDir = environment.getRequiredProperty("explore.output.download-path");

                    if(!new File(downloadDir).exists()){
                        logger.info("Running first time!! download folder {} is not exists, try to create.",downloadDir);
                        new File(downloadDir).mkdirs();
                    }

                    try {

                        downloadedJsonFile = DownloadUtils.downloadJsonFile(new URL(jsonURL),new File(downloadDir),"exoplanet_data");
                     }catch (MalformedURLException e){
                        logger.error("Invalid URL {} ",jsonURL );
                        new RuntimeException("Invalid URL "+jsonURL,e);
                    }

                    if(downloadedJsonFile == null){
                        throw new RuntimeException("Downloaded failed");
                    }

               }
           }
       }
    }


    private File getDownloadedFileName() {
        Assert.notNull(downloadedJsonFile,"File is not downloaded");
        return  downloadedJsonFile;
    }

    /**
     * Find the planet which orbit star with maximum temperature, if multiple
     * planet found with
     * @return
     */
    @Override
    public List<Planet> findPlanetOrbitingHottestStar() {
        List<Planet> planetOrbitingHottestStars = null;
        try {
            downloadJsonFileIfNotAlready();
            List<Planet> data = parsePlanetJSON();

            Planet hottestStar = data.stream()
                    .filter(d->d.getHostStarTempK() != null)
                    .max(Comparator.comparingLong(Planet::getHostStarTempK))
                    .orElse(null);

            planetOrbitingHottestStars = data.stream()
                    .filter(d->d.getHostStarTempK() != null && hottestStar != null && d.getHostStarTempK().equals(hottestStar.getHostStarTempK()))
                    .collect(Collectors.toList());

            if(planetOrbitingHottestStars != null && planetOrbitingHottestStars.size() ==0)
                planetOrbitingHottestStars = null;

            logger.info("planetOrbitingHottestStar {}  ",planetOrbitingHottestStars);

        }catch (Exception e){
            logger.error("Problem finding planet orbiting hottest start ",e);
            throw new RuntimeException(e);
        }
        return planetOrbitingHottestStars;
    }

    private List<Planet> parsePlanetJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(getDownloadedFileName(), new TypeReference<List<Planet>>(){});
    }

    @Override
    public List<DiscoveryTimelineAgg> getDiscoverTimelineByYear() {
        List<DiscoveryTimelineAgg> results = null;
        try {
            downloadJsonFileIfNotAlready();
            List<Planet> data = parsePlanetJSON();

            Map<Integer,Map<String,List<DiscoveryTimelineAgg>>> aggList = data.stream()
                    .filter(d -> d.getDiscoveryYear() != null && d.getRadiusJpt() !=null ) // filter out unknown/ invalid years
                    .map(e->{
                        DiscoveryTimelineAgg discoveryTimelineAgg = new DiscoveryTimelineAgg();
                        discoveryTimelineAgg.setYear(e.getDiscoveryYear());
                        String size = "";
                        if(e.getRadiusJpt() < 1.0){
                            size = "small";
                        }else if(e.getRadiusJpt() < 2.0){
                            size = "medium";
                        }else{
                            size = "large";
                        }
                        discoveryTimelineAgg.setSize(size);
                        discoveryTimelineAgg.setPlanetCount(1L);

                        return discoveryTimelineAgg;
                    })
//                    .sorted(Comparator.comparingLong(DiscoveryTimelineAgg::getYear) )
                    .collect(groupingBy(DiscoveryTimelineAgg::getYear,groupingBy(DiscoveryTimelineAgg::getSize)));

            String[] sizeList = new String[]{"small","medium","large"};
            List<Integer> yearKeyList = aggList.keySet().stream().sorted().collect(Collectors.toList());
            results = new ArrayList<>();
            for(Integer key : yearKeyList){
                Map<String,List<DiscoveryTimelineAgg>> sizeMap = aggList.get(key);
                for(String sizeKey : sizeList) {

                    DiscoveryTimelineAgg discoveryTimelineAgg = new DiscoveryTimelineAgg();
                    int count = sizeMap != null && sizeMap.get(sizeKey) !=null ? sizeMap.get(sizeKey) .size() : 0;
                    discoveryTimelineAgg.setYear(key);
                    discoveryTimelineAgg.setSize(sizeKey);
                    discoveryTimelineAgg.setPlanetCount(count);

                    results.add(discoveryTimelineAgg);
                    logger.info("Year {} Size {} , count {} ", key, sizeKey, count   );
                }
            }

        }catch (Exception e){
            logger.error("Problem finding planet orbiting hottest start ",e);
            throw new RuntimeException(e);
        }
        return results;
    }
}


