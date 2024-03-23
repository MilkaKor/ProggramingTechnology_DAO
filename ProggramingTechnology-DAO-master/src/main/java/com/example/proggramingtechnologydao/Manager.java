package com.example.proggramingtechnologydao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class Manager {
    public static DataSource createDataSource(){
        final String url =
                "jdbc:postgresql://localhost:5432/db_servies?user=postgres&password=0000";
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
