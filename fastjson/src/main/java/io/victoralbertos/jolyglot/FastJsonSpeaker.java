/*
 * Copyright 2016 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.victoralbertos.jolyglot;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * FastJson implementation of Jolyglot
 */
public class FastJsonSpeaker implements JolyglotGenerics {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJson(Object src) {
        return JSON.toJSONString(src);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T fromJson(String json, Class<T> classOfT) throws RuntimeException {
        return JSON.parseObject(json, classOfT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T fromJson(File file, Class<T> classOfT) throws RuntimeException {
        return JSON.parseObject(readFile2String(file), classOfT);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return JSON.toJSONString(src);
    }

    @Override
    public <T> T fromJson(String json, Type type) throws RuntimeException {
        return JSON.parseObject(json, type);
    }

    @Override
    public <T> T fromJson(File file, Type typeOfT) throws RuntimeException {
        return JSON.parseObject(readFile2String(file), typeOfT);
    }

    @Override
    public GenericArrayType arrayOf(Type componentType) {
        return Types.arrayOf(componentType);
    }

    @Override
    public ParameterizedType newParameterizedType(Type rawType, Type... typeArguments) {
        return Types.newParameterizedType(rawType, typeArguments);
    }

    private static String readFile2String(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
