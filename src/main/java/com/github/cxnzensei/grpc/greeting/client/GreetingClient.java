package com.github.cxnzensei.grpc.greeting.client;

import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm a gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // remove in prod
                .build();

        System.out.println("Creating stub");

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Unary
        //        Greeting greeting = Greeting.newBuilder().setFirstName("Nishit").setLastName("Patil").build();
        //        GreetRequest greetRequest = GreetRequest.newBuilder().setGreeting(greeting).build();
        //        GreetResponse response = greetClient.greet(greetRequest);
        //
        //        System.out.println(response.getResult());

        // Server Streaming
        // Prepare the request
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                        .setGreeting(Greeting.newBuilder().setFirstName("Nishit"))
                        .build();

        // stream the responses (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> System.out.println(greetManyTimesResponse.getResult()));

        // Do something here

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}