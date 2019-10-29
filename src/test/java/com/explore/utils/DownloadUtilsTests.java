package com.explore.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class DownloadUtilsTests {

    private final String VALID_PATH = "/data/junks4";

    @Before
    public void setUp() {
       File testPATH = new File(VALID_PATH);
       if(!testPATH.exists()){
           testPATH.mkdirs();
       }
    }

    @Test
    public void downloadJsonFile_Success() throws Exception{
        String url="https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        File outputFile = DownloadUtils.downloadJsonFile(new URL(url), new File(VALID_PATH),"jsondata");
        Assert.assertNotNull("Outputfile should be there ",outputFile);
        Assert.assertTrue("Should exists on filesystem ",outputFile.exists());
        Assert.assertTrue("Filelength should be >0 ", outputFile.length() > 0);
        System.out.println("Output file "+outputFile);
    }


    @Test
    public void downloadFileTest_Success() throws Exception{
        String url="https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        File outputFile = DownloadUtils.downloadFile(new URL(url), new File(VALID_PATH),"jsondata",".json");
        Assert.assertNotNull("Outputfile should be there ",outputFile);
        Assert.assertTrue("Should exists on filesystem ",outputFile.exists());
        Assert.assertTrue("Filelength should be >0 ", outputFile.length() > 0);
        System.out.println("Output file "+outputFile);
    }

    @Test
    public void downloadFileTest_success_without_optional_param() throws Exception{
        String url="https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        File outputFile = DownloadUtils.downloadFile(new URL(url),null,null,null);

        Assert.assertNotNull("Outputfile should be there ",outputFile);
        Assert.assertTrue("Should exists on filesystem ",outputFile.exists());
        Assert.assertTrue("Filelength should be >0 ", outputFile.length() > 0);
        System.out.println("Output file "+outputFile);

    }

    @Test
    public void downloadFileTest_failure_invalid_URL() throws Exception{
        String url="https://gist-test.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        File outputFile = DownloadUtils.downloadFile(new URL(url),null,null,null);
        System.out.println("Output file "+outputFile);
        Assert.assertNull("Outputfile should not be there ",outputFile);
    }

    @Test
    public void downloadFileTest_failure_non_exists_directory() throws Exception{
        String url="https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        File outputFile = DownloadUtils.downloadFile(new URL(url),new File("C:\\junks1"),null,null);
        System.out.println("Output file "+outputFile);
        Assert.assertNull("Outputfile should not be there ",outputFile);
    }
}
