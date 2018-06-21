package com.deimzbet.android.sqlitecountry.database;

public final class CountryDbSchema {

    public static final class CountryTable {

        public static final String NAME = "country";

        public static final class Columns {

            public static final String COUNTRY_NAME = "name";
            public static final String REGION = "region";
            public static final String POPULATION = "population";
        }
    }
}
