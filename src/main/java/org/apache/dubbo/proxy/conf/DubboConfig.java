package org.apache.dubbo.proxy.conf;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.proxy.metadata.MetadataCollector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {

	@Value("${proxy.registry.address}")
	private String registryAddress;

	@Value("${proxy.registry.group}")
	private String group;

	@Value("${proxy.metadata-report.address}")
	private String metadataAddress;

	public DubboConfig() {
		System.setProperty("dubbo.application.logger", "slf4j");
	}

	@Bean
	public ApplicationConfig appConfig() {
		ApplicationConfig application = new ApplicationConfig();
		application.setName("grpc-dubbo-proxy");
		return application;
	}

	@Bean
	public RegistryConfig registry() {

		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(registryAddress);
		return registry;
	}

	@Bean
	MetadataCollector getMetadataCollector() {
		MetadataCollector metaDataCollector = null;
		if (StringUtils.isNotEmpty(metadataAddress)) {
			URL metadataUrl = URL.valueOf(metadataAddress);
			metaDataCollector = ExtensionLoader.getExtensionLoader(MetadataCollector.class)
					.getExtension(metadataUrl.getProtocol());
		}
		return metaDataCollector;
	}

}
