/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.qianyri.facepay.baidu.parser;

import com.qianyri.facepay.baidu.exception.FaceError;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadParser implements Parser<Integer> {
    @Override
    public Integer parse(String json) throws FaceError {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int ret = jsonObject.optInt("ret");

            return ret;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
