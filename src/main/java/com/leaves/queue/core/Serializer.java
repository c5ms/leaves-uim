package com.leaves.queue.core;

/**
 * 平台序列化类,用于平台级别的功能序列化和反序列化.
 * 注意,这个类不能在platform意外的包使用
 */
public interface Serializer {
    /**
     * 序列化对象到字符串
     *
     * @param object 对象
     * @return 对象表示的字符串
     */
    byte[] serialize(Object object);

    /**
     * 反序列化数据到对象
     *
     * @param data   要反序列化的数据
     * @param tClass 对象类型
     * @param <T>    对象类型
     * @return 对象实例
     */
    <T> T deserialize(byte[] data, Class<T> tClass);

    /**
     * 对象转换为JSON
     *
     * @param object 对象
     * @return 字符串
     */
    String obj2Json(Object object);

    /**
     * json转换为对象
     *
     * @param json  json字符串
     * @param clazz 类型
     * @return 对象
     */
    <T> T json2Obj(String json, Class<T> clazz);

}
