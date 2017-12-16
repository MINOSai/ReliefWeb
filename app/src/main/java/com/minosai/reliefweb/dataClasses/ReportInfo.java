package com.minosai.reliefweb.dataClasses;

import java.util.List;

/**
 * Created by minos.ai on 16/12/17.
 */

public class ReportInfo {

    class date {
        String original;
        String changed;
        String created;
    }

    class DisasterType {
        String id;
        String name;
        String code;
    }

    class Disaster {
        String id;
        String name;
        List<DisasterType> type;
    }

    class Country {
        String href;
        int id;
        String name;
    }

    class PrimaryCountry {
        String href;
        String name;
    }

    class File {
        String id;
        String description;
        String url;
        String filename;
    }

    class Fields {
        int id;
        String title;
        String published;
        String body;
        List<File> file;
        String origin;
        PrimaryCountry primary_country;
        List<Country> country;
        List<Disaster> disaster;
        String url;
    }

    class Data {
        String id;
        List<Fields> fields;
    }

    String href;
    List<Data> data;
}
