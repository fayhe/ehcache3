package com.memorynotfound.springboot;

/**
 * Created by fay on 4/3/19.
 */
import net.logstash.logback.marker.LogstashMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.logstash.logback.marker.Markers.append;
import java.util.HashMap;
public class Log4jTestDemo {
    private static Logger logger = LoggerFactory.getLogger(Log4jTestDemo.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println("This is println message.");

        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        HashMap<String, String> map =  new  HashMap<String, String>();
        map.put("1","1");
        LogstashMarker marker = append("111", map);
        logger.info(marker,"This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
        logger.info(append("name1", "value1").and(append("name2", "value2")), "log message");
    }


}