package com.minosai.reliefweb.dataClasses;

import java.util.List;

/**
 * Created by minos.ai on 16/12/17.
 */

public class ReportInfo {

    public class Date {
        public String original;
        public String changed;
        public String created;

        public String getOriginal() {
            return original;
        }

        public String getChanged() {
            return changed;
        }

        public String getCreated() {
            return created;
        }
    }

    public class DisasterType {
        public int id;
        public String name;
        public String code;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

    public class Disaster {
        public String id;
        public String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<DisasterType> getType() {
            return type;
        }

        public List<DisasterType> type;
    }

    public class Source {
        public String href;
        public int id;
        public String name;
        public String shortname;
        public String longname;
        public String homepage;

        public String getHref() {
            return href;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShortname() {
            return shortname;
        }

        public String getLongname() {
            return longname;
        }

        public String getHomepage() {
            return homepage;
        }
    }

    public class Country {
        public String href;
        public int id;
        public String name;

        public String getHref() {
            return href;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class PrimaryCountry {
        public String href;
        public String name;

        public String getHref() {
            return href;
        }

        public String getName() {
            return name;
        }
    }

    public class File {
        public String id;
        public String description;
        public String url;
        public String filename;

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getFilename() {
            return filename;
        }
    }

    public class Fields {
        public int id;
        public String title;
        public String status;
        public String body;
        public List<File> file;
        public String origin;
        public PrimaryCountry primary_country;
        public List<Country> country;
        public List<Source> source;

        public List<Source> getSource() {
            return source;
        }

        public List<Disaster> disaster;
        public String url;
        public Date date;

        public Date getDate() {
            return date;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }

        public String getBody() {
            return body;
        }

        public List<File> getFile() {
            return file;
        }

        public String getOrigin() {
            return origin;
        }

        public PrimaryCountry getPrimary_country() {
            return primary_country;
        }

        public List<Country> getCountry() {
            return country;
        }

        public List<Disaster> getDisaster() {
            return disaster;
        }

        public String getUrl() {
            return url;
        }

    }

    public class Data {

        public Fields fields;
        public String id;

        public String getId() {
            return id;
        }

        public Fields getFields() {
            return fields;
        }
    }

    public String href;
    public List<Data> data;

    public String getHref() {
        return href;
    }

    public List<Data> getData() {
        return data;
    }
}
