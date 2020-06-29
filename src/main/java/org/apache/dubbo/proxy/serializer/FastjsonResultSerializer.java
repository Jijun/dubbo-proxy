package org.apache.dubbo.proxy.serializer;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component
public class FastjsonResultSerializer implements ResultSerializer {

	@Override
	public String serialize(Object from) {
		return JSON.toJSONString(from);
	}

}
