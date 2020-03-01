package com.vignesh.healthcare.common;

import com.vignesh.healthcare.R;

import java.util.HashMap;

public class FieldNameMapping {
    static public HashMap<String, Integer> fieldNameMapping = new HashMap<>();
    static {
        fieldNameMapping.put("name", R.string.name);
        fieldNameMapping.put("email", R.string.email);
        fieldNameMapping.put("contact", R.string.contact);
        fieldNameMapping.put("age", R.string.age);
        fieldNameMapping.put("gender", R.string.gender);
        fieldNameMapping.put("password", R.string.password);
        fieldNameMapping.put("address", R.string.address);
        fieldNameMapping.put("qualification", R.string.qualification);
        fieldNameMapping.put("specialist", R.string.specialist);
    }
}
