package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AocUtils {

    public Scanner getScannerFromFileName(String filename) throws URISyntaxException, FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filename);
        if (resource != null) {
            return new Scanner(new File(resource.toURI()));
        }
        return null;
    }

    public List<Integer> getIntegerListFromFile(String path) {
        List<Integer> integers = new ArrayList<>();

        try {
            integers = convertFileToArray(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return integers;
    }

    private List<Integer> convertFileToArray(String path) throws IOException {
        return Files.lines(Paths.get("src/main/resources/"+path)).map(Integer::parseInt).collect(Collectors.toList());
    }
}
