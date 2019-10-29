package com.explore.services;

import com.explore.domains.DiscoveryTimelineAgg;
import com.explore.domains.Planet;
import com.explore.services.impl.DataManagerImpl;
import com.explore.utils.DownloadUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@PrepareForTest({ DownloadUtils.class })
@RunWith(PowerMockRunner.class)
public class DataService_No_Data_Tests {

    Environment environmentTest = null;

    DataManager dataManager = null;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(DownloadUtils.class);
        //File outfile = new File("C:\\junks\\data\\exoplanet_data169825614380233163.json");
        File outfile =  new File(this.getClass().getResource("/sample_data_no_data.json").getFile());;
        PowerMockito.when(DownloadUtils.downloadJsonFile(any(), any(),any())).thenReturn(outfile);

        environmentTest = PowerMockito.mock(Environment.class);
        PowerMockito.when(environmentTest.getRequiredProperty("explore.input.url")).thenReturn("https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets");
        PowerMockito.when(environmentTest.getRequiredProperty("explore.output.download-path")).thenReturn("C:\\junks\\data");

        dataManager = new DataManagerImpl(environmentTest);
    }



    @Test
    public void downloadJsonFile_Success() throws Exception{
        int orphanPlanetCount = dataManager.getOrphanPlanetCount();
        Assert.assertTrue("Count",orphanPlanetCount==0);
    }


//    @PrepareForTest({ DownloadUtils.class })
    @Test
    public void findPlanetOrbitingHottestStar_Success() throws Exception{
        List<Planet>  planet = dataManager.findPlanetOrbitingHottestStar();
        Assert.assertTrue("planet",planet==null);
    }

    @Test
    public void getDiscoverTimelineByYear_Success() throws Exception{
        List<DiscoveryTimelineAgg>  data = dataManager.getDiscoverTimelineByYear();
//        Assert.assertTrue("Count",orphanPlanetCount==2);
        for(int i=0;i<data.size();i++){
            if(data.get(i).getYear()==2004){
                if(data.get(i).getSize().equals("small")) {
                    Assert.assertEquals(data.get(i).getPlanetCount(),0);
                }else if(data.get(i).getSize().equals("medium")) {
                    Assert.assertEquals(data.get(i).getPlanetCount(),0);
                }else if(data.get(i).getSize().equals("large")) {
                    Assert.assertEquals(data.get(i).getPlanetCount(),0);
                }
            }
        }
    }

}
