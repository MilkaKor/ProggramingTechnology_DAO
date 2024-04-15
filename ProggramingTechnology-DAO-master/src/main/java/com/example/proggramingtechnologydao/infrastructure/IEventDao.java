package com.example.proggramingtechnologydao.infrastructure;

import com.example.proggramingtechnologydao.model.Event;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

public interface IEventDao {
    public void add(Event account) throws SQLException, ParseException;
    public void update(Event account) throws SQLException, ParseException;
    public Event get(int event_id) throws SQLException;
    public Collection<Event> getAll() throws SQLException;
    public void delete(int event_id) throws SQLException;
}
