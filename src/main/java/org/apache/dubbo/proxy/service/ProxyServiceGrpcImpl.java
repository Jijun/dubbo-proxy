package org.apache.dubbo.proxy.service;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.proxy.proto.ProxyReply;
import org.apache.dubbo.proxy.proto.ProxyRequest;
import org.apache.dubbo.proxy.proto.ProxyServiceGrpc.ProxyServiceImplBase;
import org.apache.dubbo.proxy.serializer.ResultSerializer;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ProtocolStringList;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

@Component
public final class ProxyServiceGrpcImpl extends ProxyServiceImplBase {

	private final ApplicationConfig appConfig;
	private final RegistryConfig registry;
	private final ResultSerializer serializer;
//	private final Gson gson;

	public ProxyServiceGrpcImpl(ApplicationConfig appConfig, RegistryConfig registry, ResultSerializer serializer) {
		this.appConfig = appConfig;
		this.registry = registry;
		this.serializer = serializer;
//		this.gson = gson;
	}

	private final Logger logger = LoggerFactory.getLogger(ProxyServiceGrpcImpl.class);

	@Override
	public void invokeProxy(ProxyRequest request, StreamObserver<ProxyReply> responseObserver) {
		String interfaceName = request.getInterfaceName();
		String group = request.getGroup();
		String methodName = request.getMethod();
		String version = request.getVersion();
		ProtocolStringList types = request.getTypesList();
		String typeValues = request.getValues();

		Object result = null;

		try {
			String[] paramTypes = types.toArray(new String[0]);
			Object[] paramValues = JSON.parseObject(typeValues, Object[].class);
			ReferenceConfig<GenericService> reference = getReferencenConfig(interfaceName, group, version);
			ReferenceConfigCache refConfigCache = ReferenceConfigCache.getCache();
			GenericService genericService = refConfigCache.get(reference);
			result = genericService.$invoke(methodName, paramTypes, paramValues);
		} catch (Exception e) {
			logger.error("Generic invoke failed", e);
			responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).withCause(e).asRuntimeException());
			return;
		}
		
		ProxyReply reply = ProxyReply.newBuilder().setResult(serializer.serialize(result)).build();
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

}
