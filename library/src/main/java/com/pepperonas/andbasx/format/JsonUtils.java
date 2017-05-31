/*
 * Copyright (c) 2017 Martin Pfeffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pepperonas.andbasx.format;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Json utils.
 */
public class JsonUtils {


    /**
     * Byte array to json json object.
     *
     * @param responseBody the response body
     * @return the json object
     */
    public static JSONObject byteArrayToJson(byte[] responseBody) {
        String responseString;
        try {
            responseString = new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseString = "";
        }
        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObject;
    }

    /**
     * Byte array to json array json array.
     *
     * @param responseBody the response body
     * @return the json array
     */
    public static JSONArray byteArrayToJsonArray(byte[] responseBody) {
        String responseString;
        try {
            responseString = new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseString = "";
        }
        JSONArray mainObject = null;
        try {
            mainObject = new JSONArray(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObject;
    }

    /**
     * Json object to hash map hash map.
     *
     * @param jsonObject the json object
     * @return the hash map
     * @throws JSONException the json exception
     */
    public static HashMap<String, String> jsonObjectToHashMap(JSONObject jsonObject) throws JSONException {
        HashMap<String, String> hashMap = new HashMap<>(0);
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof String) {
                hashMap.put(key, String.valueOf(value));
            }
        }
        return hashMap;
    }


    /**
     * Convert linked hash map to json object json object.
     *
     * @param map the map
     * @return the json object
     */
    public static JSONObject convertLinkedHashMapToJsonObject(LinkedHashMap<String, String> map) {
        // map object to send - aka 'mapping'
        JSONObject jsonObjectMapping = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jsonObjectMapping.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjectMapping;
    }


}
