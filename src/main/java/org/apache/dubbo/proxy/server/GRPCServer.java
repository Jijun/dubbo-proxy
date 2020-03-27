package org.apache.dubbo.proxy.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.BindableService;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.protobuf.services.ProtoReflectionService;

@Component
public class GRPCServer {

	private final Logger logger = LoggerFactory.getLogger(GRPCServer.class);

	private Server server;

	@Value("${grpc.server.port}")
	private int grpcServerPort;
	@Autowired
	private BindableService grpcService;

	@PostConstruct
	public void start() {
		ServerBuilder<?> serverBuilder = ServerBuilder.forPort(grpcServerPort)
				.intercept(new ServerInterceptor() {
			@Override
			public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
					ServerCallHandler<ReqT, RespT> next) {
				call.setCompression("gzip");
				return next.startCall(call, headers);
			}
		});
		serverBuilder.addService(grpcService);
		// for grpc_cli to list service
		serverBuilder.addService(ProtoReflectionService.newInstance());

		try {
			server = serverBuilder.build().start();
			logger.info("grpc server started at " + grpcServerPort);
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					logger.info("shutdown grpc server started at " + grpcServerPort);

					server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}));

			server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
