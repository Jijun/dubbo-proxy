package org.apache.dubbo.proxy.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: proto/clazz_method_param.proto")
public final class ProxyServiceGrpc {

  private ProxyServiceGrpc() {}

  public static final String SERVICE_NAME = "dubbo.ProxyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.apache.dubbo.proxy.proto.ProxyRequest,
      org.apache.dubbo.proxy.proto.ProxyReply> getInvokeProxyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InvokeProxy",
      requestType = org.apache.dubbo.proxy.proto.ProxyRequest.class,
      responseType = org.apache.dubbo.proxy.proto.ProxyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.apache.dubbo.proxy.proto.ProxyRequest,
      org.apache.dubbo.proxy.proto.ProxyReply> getInvokeProxyMethod() {
    io.grpc.MethodDescriptor<org.apache.dubbo.proxy.proto.ProxyRequest, org.apache.dubbo.proxy.proto.ProxyReply> getInvokeProxyMethod;
    if ((getInvokeProxyMethod = ProxyServiceGrpc.getInvokeProxyMethod) == null) {
      synchronized (ProxyServiceGrpc.class) {
        if ((getInvokeProxyMethod = ProxyServiceGrpc.getInvokeProxyMethod) == null) {
          ProxyServiceGrpc.getInvokeProxyMethod = getInvokeProxyMethod =
              io.grpc.MethodDescriptor.<org.apache.dubbo.proxy.proto.ProxyRequest, org.apache.dubbo.proxy.proto.ProxyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InvokeProxy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.proxy.proto.ProxyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.apache.dubbo.proxy.proto.ProxyReply.getDefaultInstance()))
              .setSchemaDescriptor(new ProxyServiceMethodDescriptorSupplier("InvokeProxy"))
              .build();
        }
      }
    }
    return getInvokeProxyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProxyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProxyServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProxyServiceStub>() {
        @java.lang.Override
        public ProxyServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProxyServiceStub(channel, callOptions);
        }
      };
    return ProxyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProxyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProxyServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProxyServiceBlockingStub>() {
        @java.lang.Override
        public ProxyServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProxyServiceBlockingStub(channel, callOptions);
        }
      };
    return ProxyServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProxyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProxyServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProxyServiceFutureStub>() {
        @java.lang.Override
        public ProxyServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProxyServiceFutureStub(channel, callOptions);
        }
      };
    return ProxyServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProxyServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void invokeProxy(org.apache.dubbo.proxy.proto.ProxyRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.proxy.proto.ProxyReply> responseObserver) {
      asyncUnimplementedUnaryCall(getInvokeProxyMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getInvokeProxyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.apache.dubbo.proxy.proto.ProxyRequest,
                org.apache.dubbo.proxy.proto.ProxyReply>(
                  this, METHODID_INVOKE_PROXY)))
          .build();
    }
  }

  /**
   */
  public static final class ProxyServiceStub extends io.grpc.stub.AbstractAsyncStub<ProxyServiceStub> {
    private ProxyServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProxyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProxyServiceStub(channel, callOptions);
    }

    /**
     */
    public void invokeProxy(org.apache.dubbo.proxy.proto.ProxyRequest request,
        io.grpc.stub.StreamObserver<org.apache.dubbo.proxy.proto.ProxyReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInvokeProxyMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProxyServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProxyServiceBlockingStub> {
    private ProxyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProxyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProxyServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.apache.dubbo.proxy.proto.ProxyReply invokeProxy(org.apache.dubbo.proxy.proto.ProxyRequest request) {
      return blockingUnaryCall(
          getChannel(), getInvokeProxyMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProxyServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ProxyServiceFutureStub> {
    private ProxyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProxyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProxyServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.apache.dubbo.proxy.proto.ProxyReply> invokeProxy(
        org.apache.dubbo.proxy.proto.ProxyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getInvokeProxyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INVOKE_PROXY = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProxyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProxyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INVOKE_PROXY:
          serviceImpl.invokeProxy((org.apache.dubbo.proxy.proto.ProxyRequest) request,
              (io.grpc.stub.StreamObserver<org.apache.dubbo.proxy.proto.ProxyReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ProxyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProxyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.apache.dubbo.proxy.proto.ClazzMethodParam.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProxyService");
    }
  }

  private static final class ProxyServiceFileDescriptorSupplier
      extends ProxyServiceBaseDescriptorSupplier {
    ProxyServiceFileDescriptorSupplier() {}
  }

  private static final class ProxyServiceMethodDescriptorSupplier
      extends ProxyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProxyServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProxyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProxyServiceFileDescriptorSupplier())
              .addMethod(getInvokeProxyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
