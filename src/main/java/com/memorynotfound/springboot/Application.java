package com.memorynotfound.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import java.lang.management.ManagementFactory;
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

@EnableCaching
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MusicService musicService;

    @Resource
    private CacheManager cacheManager;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
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


}

    private void play(String instrument){
        log.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + instrument + "\");");
        musicService.play(instrument);
    }
}
