package com.example.javatest.controller;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    //refreshRate constant
    private static final long refreshRate = 30000;

    //timeout constant in seconds
    private static final long timeOut = 60;

    @Scheduled(fixedRate = refreshRate)
    private static void deleteObject() throws ParseException {

        //choosing data directory
        File dir = new File(TestController.dirname);

        try{
            if(dir.mkdir()) {
                System.out.println("Directory Created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        //listing out all data files
        File[] files = dir.listFiles();

        //setting local datetime with timezone
        LocalDateTime localCurrentDate = LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("UTC")));
        //Date format
        //Date currentdate = Date.from(localcurrentdate.atZone(ZoneId.of("UTC")).toInstant());

        for (File file: files) {

            //parsing filename to find out the date
            Date FileDate =
                    TestController.dateFormat.parse(file.getName().replaceAll("data|.csv",""));

            //adding timeout
            LocalDateTime localFileDateWithTimeout =
                    LocalDateTime.from(FileDate.toInstant().atZone(ZoneId.of("UTC"))).plusSeconds(timeOut);

            //going back to date
            //filedate = Date.from(localfiledate.atZone(ZoneId.of("UTC")).toInstant());
            if(localCurrentDate.isAfter(localFileDateWithTimeout)) {
                file.delete();
                System.out.println("Files: " + "\n\t\t|"+file.getName() +"\n" + "has been successfully removed.");
            }

        }
        //listing out all data files
        files = dir.listFiles();
        System.out.println(new Date()+
                "\n\t\t|System is up to date. \n\t\t|Total files: "+files.length);
    }


}