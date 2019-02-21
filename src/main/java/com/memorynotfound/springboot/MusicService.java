

package com.memorynotfound.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "instruments")
public class MusicService {

    private static Logger log = LoggerFactory.getLogger(MusicService.class);

    @CacheEvict(allEntries = true)
    public void clearCache(){}

    @Cacheable(key = "#instrument")
    public String play(String instrument) {
        log.info("Executing cash: " + this.getClass().getSimpleName() + ".play(\"" + instrument + "\");");
        return "paying " + instrument + "!";
    }

}
