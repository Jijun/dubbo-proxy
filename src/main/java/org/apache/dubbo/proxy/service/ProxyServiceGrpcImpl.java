package org.apache.dubbo.proxy.service;

import java.util.List;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.identifier.MetadataIdentifier;
import org.apache.dubbo.proxy.metadata.MetadataCollector;
import org.apache.dubbo.proxy.proto.ProxyReply;
import org.apache.dubbo.proxy.proto.ProxyRequest;
import org.apache.dubbo.proxy.proto.ProxyServiceGrpc.ProxyServiceImplBase;
import org.apache.dubbo.proxy.utils.Constants;
import org.apache.dubbo.proxy.utils.Tool;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.protobuf.ProtocolStringList;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

@Component
public final class ProxyServiceGrpcImpl extends ProxyServiceImplBase {
	@Autowired
	private Gson gson;
	@Autowired
	private MetadataCollector metadataCollector;
	@Autowired
	private ApplicationConfig appConfig;
	@Autowired
	private RegistryConfig registry;

	private final Logger logger = LoggerFactory.getLogger(ProxyServiceGrpcImpl.class);

	@Override
	public void invokeProxy(ProxyRequest request, StreamObserver<ProxyReply> responseObserver) {
		String application = request.getApplication();
		String interfaceName = request.getInterfaceName();
		String group = request.getGroup();
		String methodName = request.getMethod();
		String version = request.getVersion();
		ProtocolStringList types = request.getTypesList();
		String typeValues = request.getValues();
		String[] paramTypes = types.toArray(new String[0]);
		Object[] paramValues = gson.fromJson(typeValues, Object[].class);
		// 如果没有参数类型，从meta获取
		if ((paramTypes == null || paramTypes.length == 0) && paramValues != null && paramValues.length > 0) {
			paramTypes = getTypesFromMetadata(application, interfaceName, group, version, methodName,
					paramValues.length);
		}
		ReferenceConfig<GenericService> reference = getReferencenConfig(interfaceName, group, version);
		ReferenceConfigCache refConfigCache = ReferenceConfigCache.getCache();
		GenericService genericService = refConfigCache.get(reference);
		Object result = null;
		try {
			result = genericService.$invoke(methodName, paramTypes, paramValues);
		} catch (Exception e) {
			logger.error("Generic invoke failed", e);
			responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).withCause(e).asRuntimeException());
			return;
		}
		ProxyReply reply = ProxyReply.newBuilder().setResult(gson.toJson(result)).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	private ReferenceConfig<GenericService> getReferencenConfig(String interfaceName, String group, String version) {

		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
		reference.setApplication(appConfig);
		reference.setRegistry(registry);
		reference.setProtocol("dubbo");
		reference.setGroup(group);
		reference.setInterface(interfaceName);
		reference.setVersion(version);
		reference.setGeneric(true);
		return reference;
	}

	private String[] getTypesFromMetadata(String application, String interfaze, String group, String version,
			String methodName, int paramLen) {
		MetadataIdentifier identifier = new MetadataIdentifier(interfaze, version, group, Constants.PROVIDER_SIDE,
				application);
		String metadata = metadataCollector.getProviderMetaData(identifier);
		FullServiceDefinition serviceDefinition = gson.fromJson(metadata, FullServiceDefinition.class);
		List<MethodDefinition> methods = serviceDefinition.getMethods();
		if (methods != null) {
			for (MethodDefinition m : methods) {
				if (Tool.sameMethod(m, methodName, paramLen)) {
					return m.getParameterTypes();
				}
			}
		}
		return null;
	}
}
