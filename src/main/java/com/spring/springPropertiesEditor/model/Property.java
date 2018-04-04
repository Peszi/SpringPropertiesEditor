package com.spring.springPropertiesEditor.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Property {

    @NotNull
    private String key;
    @NotNull
    private String value;

    public Property() {}

    public Property(@NotNull String key, @NotNull String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void cleanUp() {
        this.key = "";
        this.value = "";
    }
}
