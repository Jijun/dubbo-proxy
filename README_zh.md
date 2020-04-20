[English Version](README.md)  
### Dubbo Proxy
gRPC Dubbo Proxy是一个Dubbo网关，可以将gRPC请求转换成Dubbo的协议，调用Dubbo服务并且返回结果，后续还会集成熔断，限流，api管理等功能。

### 用法

```
SPRING_PROFILES_ACTIVE=product mvn clean spring-boot:run
```

### gRPC ProtoBuf


```json
syntax = "proto3";

option java_multiple_files = true;
option java_package = "org.apache.dubbo.proxy.proto";
option optimize_for = SPEED;

package dubbo;

service ProxyService {
    rpc InvokeProxy (ProxyRequest) returns (ProxyReply) {}
}
  
message ProxyRequest {
	 string application = 1;
	 string interfaceName=2;
	 string version = 3;
	 string group = 4;
	 string method=5;
	 repeated string types=6;
	 string values=7;
}
  
message ProxyReply {
	string result=1;
}
```
### 开始使用

 [开始使用](https://github.com/Jijun/grpc-dubbo-proxy/wiki/get-started)

* 在Dubbo 2.7及后续版本中，paramTypes为可选，如果不填写，Dubbo Proxy会在元数据中心获取对应的参数类型。
* 可以在`application.yml`中指定注册中心和元数据中心的地址
```
proxy.registry.address: zookeeper://127.0.0.1:2181   #注册中心地址，和Dubbo服务的注册中心相同
proxy.metadata-report.address: zookeeper://127.0.0.1:2181  #元数据中心的地址，未指定paramTypes时查找使用，支持Dubbo 2.7及以后版本
```
