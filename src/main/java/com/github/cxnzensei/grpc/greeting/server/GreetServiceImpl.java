package com.github.cxnzensei.grpc.greeting.server;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {

        // extract required data
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();

        // create response
        String result = "Hello " + firstName;
        GreetResponse response = GreetResponse.newBuilder().setResult(result).build();

        // send response
        responseObserver.onNext(response);

        // complete the RPC call
        responseObserver.onCompleted();

    }
}
