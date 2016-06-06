package com.dkovalov.movieland.dataimport;

import com.dkovalov.movieland.dataimport.service.DataLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//TODO: add logging routine
//TODO: test coverage
//TODO: multiple JDBC connections are created
public class DataImport {
    public static final String DATA_FILE_EXTENSION = ".txt";

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Parameter containing path to a directory with data files is expected.");
        }

        File dataDirectory = new File(args[0]);
        if (!dataDirectory.isDirectory()) {
            throw new RuntimeException("Invalid path to data directory: " + args[0]);
        }

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/root-context.xml");
        for (File dataFile : dataDirectory.listFiles((item, name) -> name.toLowerCase().endsWith(DATA_FILE_EXTENSION))) {
            DataLoader loader = (DataLoader) context.getBean(dataFile.getName().replace(DATA_FILE_EXTENSION, "") + "Service");
            try (BufferedReader reader = new BufferedReader(new FileReader(dataFile.getAbsolutePath()))) {
                loader.loadFromFile(reader);
            }
        }
    }
}
