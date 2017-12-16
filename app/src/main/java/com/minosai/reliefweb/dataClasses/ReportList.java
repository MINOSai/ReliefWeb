package com.minosai.reliefweb.dataClasses;

/**
 * Created by minos.ai on 16/12/17.
 */

public class ReportList {

    class Field {
        String title;
    }

    class Data{
        String id;
        int score;
        String href;
        Field fields;
    }

    String href;
    int time;
    int totalCount;
}
