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

@EnableCaching
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MusicService musicService;

    @Autowired
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

final Set<ObjectInstance> cacheBeans = beanServer.queryMBeans(
    ObjectName.getInstance("javax.cache:type=CacheStatistics,CacheManager=*,Cache=*"), null);

for ( ObjectInstance cacheBean : cacheBeans ) {
    final CacheStatisticsMXBean cacheStatisticsMXBean = MBeanServerInvocationHandler.newProxyInstance(beanServer, cacheBean.getObjectName(), CacheStatisticsMXBean.class, false);

   System.out.println("Gets: " + cacheStatisticsMXBean.getCacheGets());
    System.out.println("Hits: " + cacheStatisticsMXBean.getCacheHits());
    System.out.println("Misses: " + cacheStatisticsMXBean.getCacheMisses());
    System.out.println("Removals: " + cacheStatisticsMXBean.getCacheRemovals());
    System.out.println("Evictions: " + cacheStatisticsMXBean.getCacheEvictions());
    System.out.println("Avg Get Time: " + cacheStatisticsMXBean.getAverageGetTime());
    System.out.println("Avg Put Time: " + cacheStatisticsMXBean.getAveragePutTime());
    System.out.println("Avg Remove Time: " + cacheStatisticsMXBean.getAverageRemoveTime());
     
    }
}

    private void play(String instrument){
        log.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + instrument + "\");");
        musicService.play(instrument);
    }
}
