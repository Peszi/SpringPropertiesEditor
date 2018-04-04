package com.spring.springPropertiesEditor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Log {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String ADD_PREFIX = "[ADD]";
    private static final String EDIT_PREFIX = "[EDT]";
    private static final String REMOVE_PREFIX = "[RMV]";
    private static final String DATA_SEPARATOR = "###";
    private static final String VALUE_SEPARATOR = "=";
    private static final String DATE_SEPARATOR = "[AT]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String created;

    public Log() {}

    @PrePersist
    private void onInit() {
        this.created = new SimpleDateFormat(this.DATE_FORMAT).format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public String getCreated() {
        return created;
    }

    public Log setAddLog(String key, String value) {
        this.message = (this.ADD_PREFIX + key + this.DATA_SEPARATOR + value);
        return this;
    }

    public Log setEditLog(String key, String oldValue, String newValue) {
        this.message = (this.EDIT_PREFIX + key + this.DATA_SEPARATOR + oldValue + this.VALUE_SEPARATOR + newValue);
        return this;
    }

    public Log setRemoveLog(String key, String value) {
        this.message = (this.REMOVE_PREFIX + key + this.DATA_SEPARATOR + value);
        return this;
    }
}
