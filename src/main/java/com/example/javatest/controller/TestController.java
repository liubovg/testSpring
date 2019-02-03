package com.example.javatest.controller;
import com.example.javatest.domain.User;
import com.example.javatest.service.MockService;

import java.io.File;
import java.io.FileWriter;

import org.supercsv.io.ICsvMapWriter;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.cellprocessor.ift.CellProcessor;

import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("root")
public class TestController {
   private final MockService mockService;

    public TestController(MockService mockService) {
     this.mockService = mockService;
    }

    private static Map<String, String> result = new HashMap<>();

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss");

    //setting directory for saving files
    public static final String dirname = "data/";

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
    public void receiveObject(@RequestBody User user) throws Exception  {

        //passing json to hashmap
        result.put("name",user.getName());
        result.put("id", String.valueOf(user.getId()));

        //saving object to a file
        SaveObjectToFile(dirname);
        System.out.println("New user " + result.get("name"));

    }

    private static void SaveObjectToFile(String dirname) throws Exception {

        final String[] header = new String[] { "name", "id"};

        ///////////
        //Scheduler works
        //////////////

        ICsvMapWriter mapWriter = null;
        try {
            mapWriter = new CsvMapWriter(new FileWriter(dirname+"data"+dateFormat.format(new Date())+".csv"),
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
                System.out.println("Successfully saved "+new Date());
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
