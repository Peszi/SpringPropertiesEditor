package com.spring.springPropertiesEditor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Setter
@Getter
public class Property {

    @NotNull
    @Min(value = 1)
    private String key;

    @NotNull
    private String value;

    public Property() {}

    public void cleanUp() {
        this.key = "";
        this.value = "";
    }
}
