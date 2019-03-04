package com.memorynotfound.springboot;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.management.ManagementFactory;
import javax.imageio.ImageIO;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ObjectInstance;
import java.util.Set;
import javax.cache.management.CacheStatisticsMXBean;
import javax.management.MBeanServerInvocationHandler;
import javax.annotation.Resource;
//import org.ehcache.config.builders.CacheManagerBuilder;

@EnableCaching
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MusicService musicService;

    @Resource
    private CacheManager cacheManager;

    @Resource
    OCRService ocrService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    public HashingInputStream hashInputStream(InputStream is) throws Exception {

        HashingInputStream his = new HashingInputStream(Hashing.sha256(), is);
        try {
            //ByteStreams.copy(his, os);
            return his;
        } catch (Exception e) {
            throw new RuntimeException("Unable to compute hash while signing request: " + e.getMessage(), e);
        }
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Spring Boot Ehcache 2 Caching Example Configuration");
        log.info("using cache manager:!!!!!!!!!!!!!!!!!!!!!!!! " + cacheManager.getClass().getName());
        Object nc = cacheManager.getCache("instruments").getNativeCache();
        log.info("name!!111 " + nc.getClass().getName());
        musicService.clearCache();

        /**play("trombone");
        play("guitar");
        play("trombone");
        play("bass");
        play("trombone");
        play("guitar");
        play("bass");*/
        play("bass4");
        play("bass4");
        play("bass4");
        play("bass5");
        play("bass5");
        play("bass5");
        play("bass4");

        final MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        doOCR( "/Users/fay/Downloads/derek.jpg");
        doOCR( "/Users/fay/Downloads/derek.jpg");
        doOCR("/Users/fay/Downloads/ocr.png");
        doOCR("/Users/fay/Downloads/ocr.png");
}

    public static InputStream clone(final InputStream inputStream) {
        try {
            inputStream.mark(inputStream.available()+1); //TODO:WHY???
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int readLength = 0;
            while ((readLength = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readLength);
            }
            inputStream.reset();
            outputStream.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    private  String generateKey(InputStream sourceInputStream) throws Exception{
        InputStream inputStreamDesc = clone(sourceInputStream);
        HashingInputStream his1 =  hashInputStream(inputStreamDesc);
        BufferedImage bi1 = ImageIO.read(his1);
        String key = his1.hash().toString();
        return key;
    }


    private void doOCR( String fileName) throws Exception{
        File file = new File(fileName);
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        //generate key
        String key =  generateKey(inputStream);
        HashingInputStream his = hashInputStream(inputStream);
        System.out.println("image hash before generate image:" + key);
        //do ocr
        ocrService.convert(key, his);
        //TODO: the new hash need to use the pregenerate key  as some will trigger cache so the code will be incorrect
        System.out.println("image hash after generate image:" + his.hash());
    }

    private void play(String instrument){
        log.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + instrument + "\");");
        musicService.play(instrument);
    }
}
