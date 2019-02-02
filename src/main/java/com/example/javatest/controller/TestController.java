package com.example.javatest.controller;

import com.example.javatest.domain.User;
import com.example.javatest.service.MockService;

import java.io.File;
import java.io.FileWriter;

import org.supercsv.io.ICsvMapWriter;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.cellprocessor.ift.CellProcessor;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("root")
public class TestController {
    private final static long refreshTimer = 5000;
    private final static long deleteTimer = 20000;
    private final MockService mockService;


    public TestController(MockService mockService) {
     this.mockService = mockService;
    }

    private static Map<String, String> result = new HashMap<>();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("Y-MM-dd.HH.mm.ss");

    @GetMapping("test")
    public Map<String, String> test()
    {
       // Map<String, String> result = new HashMap<>();
       // result.put("name","user_name");
       // result.put("id", "user_id");

        mockService.foo();

        return result;
    }

    @PostMapping("test")
    public void receiveObject(@RequestBody User user) throws Exception {

        result.put("name",user.getName());
        result.put("id", String.valueOf(user.getId()));
        //System.out.println(user);
        System.out.println(result.get("name"));
        SaveObjectToFile();

        // mockService.foo();
    }

    @Scheduled(fixedRate = refreshTimer)
    public static void fileNameReader() throws ParseException {
        File file = new File("data/");
        File[] files = file.listFiles();
        for(File f: files){
            String a= f.getName().replaceAll("data|.csv","");
            Date fileDate = dateFormat.parse(a);
            Date currentDate = new Date();
            Date checkDate = new Date(fileDate.getTime() + (1000  *60 *60 * 24));
//            if(currentDate.after(checkDate)){
//                System.out.println("delete"+fileDate);
//                f.delete();
//            }
            System.out.println(dateFormat.format(checkDate));
        }
    }


    private static void SaveObjectToFile() throws Exception {

        final String[] header = new String[] { "name", "id"};

        ICsvMapWriter mapWriter = null;
        try {

            File f = new File("data");
            try{
                if(f.mkdir()) {
                    System.out.println("Directory Created");
                }
//                else {
//                   // System.out.println("Directory is not created");
//                }
            } catch(Exception e){
                e.printStackTrace();
            }

            mapWriter = new CsvMapWriter(new FileWriter("data/data"+dateFormat.format(new Date())+".csv"),
                    CsvPreference.STANDARD_PREFERENCE);

            final CellProcessor[] processors = getProcessors();

            // write the header
            mapWriter.writeHeader(header);

            // write the customer maps
            mapWriter.write(result, header, processors);
        }
        finally {
            if( mapWriter != null ) {
                mapWriter.close();
                System.out.println("Successfully saved");
            } else {
                System.out.println("Error occured");
            }
        }
    }

    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[] {
                null,
                null
        };

        return processors;
    }
}
