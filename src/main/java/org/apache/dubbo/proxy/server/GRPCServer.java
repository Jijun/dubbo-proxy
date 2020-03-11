package org.apache.dubbo.proxy.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
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
		ServerBuilder<?> serverBuilder = ServerBuilder.forPort(grpcServerPort);
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
