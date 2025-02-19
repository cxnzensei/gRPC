package com.github.cxnzensei.grpc.greeting.client;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm a gRPC client");

        GreetingClient main = new GreetingClient();
        main.run();
    }

    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // remove in prod
                .build();

        // doUnaryCall(channel);
        doServerStreamingCall(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private void doServerStreamingCall(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Prepare the request
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Nishit"))
                .build();

        // stream the responses (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> System.out.println(greetManyTimesResponse.getResult()));
    }

    private void doUnaryCall(ManagedChannel channel) {

        // Greet Service client (blocking - synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder().setFirstName("Nishit").setLastName("Patil").build();
        GreetRequest greetRequest = GreetRequest.newBuilder().setGreeting(greeting).build();
        GreetResponse response = greetClient.greet(greetRequest);

        System.out.println(response.getResult());
    }
}