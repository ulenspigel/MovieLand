package com.dkovalov.movieland.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface DataLoader {
    void loadFromFile(BufferedReader reader) throws IOException;
}
