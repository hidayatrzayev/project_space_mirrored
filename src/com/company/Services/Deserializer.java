package com.company.Services;

import org.json.simple.JSONObject;

public interface Deserializer<T> {

    T deserialize(JSONObject jsonObject);
}
