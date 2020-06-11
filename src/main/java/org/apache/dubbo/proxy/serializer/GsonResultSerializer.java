package org.apache.dubbo.proxy.serializer;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class GsonResultSerializer implements ResultSerializer {
	
	private final Gson gson;

	public GsonResultSerializer(Gson gson) {
		this.gson = gson;
	}
	

	@Override
	public String serialize(Object from) {
		return gson.toJson(from);
	}

}
