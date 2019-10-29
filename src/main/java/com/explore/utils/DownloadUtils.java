package com.explore.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadUtils {
    private static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);

    /**
     *
     * @param sourceURL
     * @param downloadDirectory
     * @param prefix
     * @return
     */
    public static File downloadJsonFile(URL sourceURL, File downloadDirectory,String prefix){
       return downloadFile(sourceURL,downloadDirectory, StringUtils.defaultIfBlank(prefix,"jsondata"),".json");
    }

    /**
     * if successfully downloaded then return downloaded file.
     * else return null.
     *
     * @param sourceURL : required
     * @param downloadDirectory : optional
     * @param prefix : optional
     * @param suffix : optional
     * @return
     */
    public static File downloadFile(URL sourceURL, File downloadDirectory,String prefix,String suffix){
        File downloadedFile = null;
        Assert.notNull(sourceURL,"URL should be passed");

        logger.info("Downloading url: {}, prefix: {}, suffix: {} ",sourceURL,prefix,suffix);

        FileOutputStream fileOutputStream = null;
        prefix = StringUtils.defaultIfBlank(prefix,"downloaded-data");
        suffix = StringUtils.defaultIfBlank(suffix,".data");
        try {
            if(downloadDirectory != null){
                downloadedFile = File.createTempFile(prefix,suffix,downloadDirectory);
            }else{
                downloadedFile = File.createTempFile(prefix,suffix);
            }

            ReadableByteChannel readableByteChannel = Channels.newChannel(sourceURL.openStream());
            fileOutputStream = new FileOutputStream(downloadedFile);
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            logger.info("Download completed successfully. {} ",downloadedFile.getPath());

        }catch (Exception e){
            logger.error("Download failed ",e);
            downloadedFile = null;
        }finally {
            if(fileOutputStream!= null){
                try {
                    fileOutputStream.close();
                }catch (Exception e){
                    logger.warn("Output stream didn't closed.");
                }
            }
        }
        return  downloadedFile;
    }
}
