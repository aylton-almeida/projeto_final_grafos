package models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NonDirectedGraph extends Graph {

    @Override
    public void addVertexFromString(String string){
        addVertexFromString(string, false);
    }
}
