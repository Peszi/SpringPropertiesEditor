package com.spring.springPropertiesEditor.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Property {

    @NotNull
    @NotEmpty
    private String key;

    @NotNull
    private String value;

    public Property() {}

    public Property(@NotNull @NotEmpty String key, @NotNull String value) {
        this.key = key.trim();
        this.value = value.trim();
    }

    public void setKey(String key) {
        this.key = key.trim();
    }

    public void setValue(String value) {
        this.value = value.trim();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void cleanUp() {
        this.key = "";
        this.value = "";
    }
}
