/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.qianyri.facepay.baidu.parser;

import com.qianyri.facepay.baidu.exception.FaceError;

/**
 * JSON解析
 * @param <T>
 */
public interface Parser<T> {
    T parse(String json) throws FaceError, FaceError;
}
