package com.memorynotfound.springboot;

import com.google.common.hash.HashingInputStream;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import net.sourceforge.tess4j.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Component;

/**
 * Created by fay on 23/2/19.
 */
@Component
@CacheConfig(cacheNames = "ocr")
public class OCRService {

    @Resource
    TesseractClient tesseractClient;


    @Cacheable(key = "#hash")
    public String convert(String hash, InputStream is) throws Exception{
        System.out.print("hit hash!!!!!!!!!!");
        BufferedImage bi = ImageIO.read(is);
        File file1 = new File("1.png");
        ImageIO.write(bi, "png", file1);//JPEG或JPG
        return tesseractClient.ocr(bi);
    }


}
