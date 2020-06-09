package utils;

import models.DirectedGraph;
import models.Graph;
import models.NonDirectedGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class FileManager {

    File file;

    public FileManager(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        this.file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    }

    /**
     * Reads file and return the non-directed graph created from it
     *
     * @return Non-directed Graph created from file
     * @throws IOException if an I/O exception happens
     */
    public NonDirectedGraph readNonDirectedGraphFile() throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        NonDirectedGraph graph;

        try {
            fr = new FileReader(this.file);
            br = new BufferedReader(fr);
            br.readLine();
            graph = new NonDirectedGraph();

            String line;
            while ((line = br.readLine()) != null)
                graph.addVertexFromString(line);

        } finally {
            assert br != null;
            br.close();
            fr.close();
        }

        return graph;
    }

    /**
     * Reads file and return the directed graph created from it
     *
     * @return Graph created from file
     * @throws IOException if an I/O exception happens
     */
    public DirectedGraph readDirectedGraphFile() throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        DirectedGraph graph;

        try {
            fr = new FileReader(this.file);
            br = new BufferedReader(fr);
            br.readLine();
            graph = new DirectedGraph();

            String line;
            while ((line = br.readLine()) != null)
                graph.addVertexFromString(line);
        } finally {
            assert br != null;
            br.close();
            fr.close();
        }

        return graph;
    }
}