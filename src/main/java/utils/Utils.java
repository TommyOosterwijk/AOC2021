package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class Utils {

    public Scanner getScannerFromFileName(String filename) throws URISyntaxException, FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filename);
        if (resource != null) {
            return new Scanner(new File(resource.toURI()));
        }
        return null;
    }
}
