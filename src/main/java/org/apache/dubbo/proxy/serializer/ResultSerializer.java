package org.apache.dubbo.proxy.serializer;

/**
 * 结果序列化方式
 * 
 * @author jijun
 *
 */
public interface ResultSerializer {

	String serialize(Object from);

}
