package com.company;

import org.json.simple.JSONObject;

public interface Deserializer<T> {

    T deserialize(JSONObject jsonObject);
}
