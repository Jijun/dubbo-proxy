package org.apache.dubbo.proxy.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.BindableService;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class GRPCServer {

	private final Logger logger = LoggerFactory.getLogger(GRPCServer.class);

	private Server server;

	@Value("${grpc.server.port}")
	private int grpcServerPort;

	private final ServerInterceptor compressionServerInterceptor;

	private final BindableService grpcService;

	public GRPCServer(BindableService grpcService, ServerInterceptor compressionServerInterceptor) {
		this.grpcService = grpcService;
		this.compressionServerInterceptor = compressionServerInterceptor;
	}

	@PostConstruct
	public void start() {
		EventLoopGroup boss = null;
		EventLoopGroup worker = null;
		Class<? extends ServerChannel> channelType = null;

		try {
			// These classes are only available on linux.performance tune
			Class<?> groupClass = Class.forName("io.netty.channel.epoll.EpollEventLoopGroup");
			@SuppressWarnings("unchecked")
			Class<? extends ServerChannel> channelClass = (Class<? extends ServerChannel>) Class
					.forName("io.netty.channel.epoll.EpollServerSocketChannel");
			boss = (EventLoopGroup) groupClass.newInstance();
			worker = (EventLoopGroup) groupClass.newInstance();
			channelType = channelClass;
			logger.info("start grpc server using EpollEventLoopGroup");

		} catch (Throwable e) {
			logger.info("start grpc server using NioEventLoopGroup");
			boss = new NioEventLoopGroup();
			worker = new NioEventLoopGroup();
			channelType = NioServerSocketChannel.class;
		}

		NettyServerBuilder serverBuilder = NettyServerBuilder.forPort(grpcServerPort)
				.intercept(compressionServerInterceptor)
				.bossEventLoopGroup(boss)
				.workerEventLoopGroup(worker)
				.channelType(channelType).directExecutor();
		
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

	public static void main(String[] args) {
		System.out.println(System.getProperties());

	}
}
