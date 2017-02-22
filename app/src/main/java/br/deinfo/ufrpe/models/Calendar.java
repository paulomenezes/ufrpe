package br.deinfo.ufrpe.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paulo on 30/10/2016.
 */
@Parcel
public class Calendar {
    private List<Event> events = new ArrayList<Event>();
    //private List<Object> warnings = new ArrayList<Object>();
    //private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     *
     * @param events
     * The events
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     *
     * @return
     * The warnings
     */
//    public List<Object> getWarnings() {
//        return warnings;
//    }
//
//    /**
//     *
//     * @param warnings
//     * The warnings
//     */
//    public void setWarnings(List<Object> warnings) {
//        this.warnings = warnings;
//    }
//
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }

}
