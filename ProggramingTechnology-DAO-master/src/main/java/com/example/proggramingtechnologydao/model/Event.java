package com.example.proggramingtechnologydao.model;

//@AllArgsConstructor
public class Event {
    public Event(int  event_id, String event_name, String event_description, String start_datetime){
        this.event_id = event_id; this.event_name = event_name; this.event_description = event_description; this.start_datetime = start_datetime;
    }
    public int event_id;
    public String event_name;
    public String event_description;
    public String start_datetime;

    @Override
    public String toString(){
        return event_id + " " + event_name + " "  + event_description + " " + start_datetime;
    }
}
