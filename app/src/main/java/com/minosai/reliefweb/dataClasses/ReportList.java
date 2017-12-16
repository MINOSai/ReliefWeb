package com.minosai.reliefweb.dataClasses;

import java.util.List;

/**
 * Created by minos.ai on 16/12/17.
 */

public class ReportList {

    public static class Field {
        public String title;

        public String getTitle() {
            return title;
        }
    }

    public static class Data{
        public String id;
        public int score;
        public String href;
        public Field fields;

        public String getId() {
            return id;
        }

        public int getScore() {
            return score;
        }

        public String getHref() {
            return href;
        }

        public Field getFields() {
            return fields;
        }
    }

    public String href;
    public int time;
    public int totalCount;
    public List<Data> data;

    public String getHref() {
        return href;
    }

    public int getTime() {
        return time;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<Data> getData() {
        return data;
    }
}
