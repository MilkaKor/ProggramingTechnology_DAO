package com.example.proggramingtechnologydao.dao;

import com.example.proggramingtechnologydao.Manager;
import com.example.proggramingtechnologydao.infrastructure.IEventDao;
import com.example.proggramingtechnologydao.model.Event;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class EventDao implements IEventDao {

    @Override
    public void add(Event account) throws SQLException {
        var connection = Manager.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("insert into event(event_name, event_description, start_datetime, end_datetime) values (?,?,?)");
        state.setString(1,account.event_name);
        state.setString(2,account.event_description);
        state.setString(3,account.start_datetime);

        state.executeUpdate();
    }

    @Override
    public void update(Event account) throws SQLException {
        var connection = Manager.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("update event set event_name = ?, event_description = ?, start_datetime = ? where event_id = ?");
        state.setString(1,account.event_name);
        state.setString(2,account.event_description);
        state.setString(3,account.start_datetime);
        state.setString(4, String.valueOf(account.event_id));

        state.executeUpdate();
    }

    @Override
    public Event get(int id) throws SQLException {
        var connection = Manager.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from event where event_id = ?");
        state.setInt(1, id);
        var result = state.executeQuery();

        Event account = null;
        while (result.next()){
            account = new Event(result.getInt("event_id"),result.getString("event_name"),
                    result.getString("event_description"),result.getString("start_datetime"));
        }
        return account;
    }

    @Override
    public Collection<Event> getAll() throws SQLException {
        var connection = Manager.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from event");
        var result = state.executeQuery();

        Collection<Event> list = new ArrayList<>();
        while (result.next()){
            list.add(new Event(result.getInt("event_id"),result.getString("event_name"),
                    result.getString("event_description"),result.getString("start_datetime"))
            );
        }
        return list;
    }

    @Override
    public void delete(int id) throws SQLException {
        var connection = Manager.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("delete from event where event_id = ?");
        state.setInt(1, id);

        state.executeUpdate();
    }
}
