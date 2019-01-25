package com.flowingsun.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
    public static byte[] serialize(Object object)throws Exception {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        // 序列化
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static Object unserialize( byte[] bytes)throws Exception {
        ByteArrayInputStream bais = null;
        // 反序列化
        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
}
