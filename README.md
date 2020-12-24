[中文版本](README_zh.md)  
## gRPC Dubbo Proxy
gRPC Dubbo Proxy, a gRPC gateway of Dubbo, switch from gRPC request to Dubbo protocol，then invoke Dubbo service and return to the result. 
relying on gRPC high performance and message compression, the message body is more smaller than Dubbo payload.

### instructions

```
SPRING_PROFILES_ACTIVE=product mvn clean spring-boot:run
```
 
### Status

This library is considered production ready.

### gRPC Protobuf: 

```proto
syntax = "proto3";

option java_multiple_files = true;
option java_package = "org.apache.dubbo.proxy.proto";
option optimize_for = SPEED;

package dubbo;

service ProxyService {
    rpc InvokeProxy (ProxyRequest) returns (ProxyReply) {}
}
  
message ProxyRequest {
	 string application = 1; //can be null
	 string interfaceName=2; // the dubbo service to invoke
	 string version = 3;     // the dubbo service version
	 string group = 4;       // group ,can be null
	 string method=5;        // method
	 repeated string types=6;//method parameter array
	 string values=7; //method value json array
}
  
message ProxyReply {
	string result=1;  //result json string
}

```

### Get Started

#### NodeJS gRPC Client
https://github.com/Jijun/grpc-dubbo-proxy/wiki/Get-Started#grpc-node-client

#### Java gRPC Client 
https://github.com/Jijun/grpc-dubbo-proxy/wiki/Get-Started#grpc-java-client

### Go gRPC Client
https://github.com/Jijun/grpc-dubbo-proxy/wiki/Get-Started#grpc-go-client

### Python gRPC Client
https://github.com/Jijun/grpc-dubbo-proxy/wiki/Get-Started#grpc-python-client



