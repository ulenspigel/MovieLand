package com.dkovalov.movieland.dataimport;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.MovieService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

//TODO: add logging routine
//TODO: test coverage
//TODO: multiple JDBC connections are created
public class DataImport {
    public static final String DATA_FILE_EXTENSION = ".txt";

    public static void main(String[] args) throws IOException {
        /*if (args.length != 1) {
            throw new RuntimeException("Parameter containing path to a directory with data files is expected.");
        }

        File dataDirectory = new File(args[0]);
        if (!dataDirectory.isDirectory()) {
            throw new RuntimeException("Invalid path to data directory: " + args[0]);
        }

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/root-context.xml");
        for (InitDataSource dataSource : InitDataSource.values()) {
            DataLoader loader = (DataLoader) context.getBean(dataSource.getName() + "Service");
            try (BufferedReader reader = new BufferedReader(new FileReader(
                    dataDirectory.getAbsolutePath() + File.separator + dataSource.getName() + DATA_FILE_EXTENSION))) {
                loader.loadFromFile(reader);
            }
        }*/
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/root-context.xml");
        MovieService service = (MovieService) context.getBean("movieService");
        List<Movie> movies =  service.getAll();
    }

    private static enum InitDataSource {
        USER("user"), GENRE("genre"), MOVIE("movie"), REVIEW("review");
        private String name;

        InitDataSource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
