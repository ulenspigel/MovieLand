package com.dkovalov.movieland.dataimport.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface DataLoader {
    void loadFromFile(BufferedReader reader) throws IOException;
}
