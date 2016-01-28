package com.stella.pals.backend.model;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.stella.pals.backend.PalsDatabase;

import java.util.Map;

/**
 * Created by DJ on 2016/01/24.
 * Project: Stella Pals
 */
@Table(database = PalsDatabase.class, allFields = true)
public class Cookie extends BaseModel {

    @PrimaryKey
    String key;
    String value;

    public Cookie() {}

    public Cookie(Map.Entry<String, String> entry) {
        key = entry.getKey();
        value = entry.getValue();

        if (exists()) {
            update();
        } else {
            save();
        }
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
