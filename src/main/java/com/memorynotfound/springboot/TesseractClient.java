package com.memorynotfound.springboot;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Created by fay on 23/2/19.
 */
@Component
public class TesseractClient {

    public String ocr(BufferedImage image){
        //File imageFile = new File("/Users/fay/Downloads/derek.jpg");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("/usr/local/Cellar/tesseract/4.0.0_1/share/tessdata"); // path to tessdata directory
        //instance.setLanguage("eng");
        String result = "";
        try {
            result = instance.doOCR(image);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
