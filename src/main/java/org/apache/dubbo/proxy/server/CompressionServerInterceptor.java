package org.apache.dubbo.proxy.server;

import org.springframework.stereotype.Component;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
@Component
public class CompressionServerInterceptor implements ServerInterceptor {

	private static final String COMPRESSION = "gzip";

	@Override
	public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {
		call.setCompression(COMPRESSION);
		return next.startCall(call, headers);
	}

}
