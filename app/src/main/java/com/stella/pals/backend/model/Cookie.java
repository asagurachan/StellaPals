package com.stella.pals.backend.model;

import java.util.Map;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DJ on 2016/01/24.
 * Project: Stella Pals
 */
public class Cookie extends RealmObject {

    @PrimaryKey
    private String key;
    private String value;

    public Cookie() {}

    public Cookie(Map.Entry<String, String> entry) {
        key = entry.getKey();
        value = entry.getValue();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
