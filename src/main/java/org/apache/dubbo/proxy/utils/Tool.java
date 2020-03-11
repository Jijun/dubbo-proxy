package org.apache.dubbo.proxy.utils;

import org.apache.dubbo.metadata.definition.model.MethodDefinition;

public class Tool {

	public static boolean sameMethod(MethodDefinition m, String methodName, int paramLen) {
		return (m.getName().equals(methodName) && m.getParameterTypes().length == paramLen);
	}
}
