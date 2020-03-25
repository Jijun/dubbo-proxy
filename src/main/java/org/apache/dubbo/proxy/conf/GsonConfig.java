package org.apache.dubbo.proxy.conf;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@Configuration
public class GsonConfig {
	private static final TypeAdapter<String> ADAPTER = new TypeAdapter<String>() {
		@Override
		public String read(JsonReader in) throws IOException {
			throw new UnsupportedOperationException("not implement");
		}

		@Override
		public void write(JsonWriter out, String value) throws IOException {
			out.jsonValue(value);
		}
	};

	@Bean
	public Gson gson() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping()
				.registerTypeAdapter(String.class, ADAPTER).create();
	}
}
