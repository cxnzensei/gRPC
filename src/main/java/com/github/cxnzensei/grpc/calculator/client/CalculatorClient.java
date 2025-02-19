package com.github.cxnzensei.grpc.calculator.client;

import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        // doUnaryCall(channel);
        // doServerStreamingCall(channel);
        doClientStreamingCall(channel);

        channel.shutdown();
    }

    public void doUnaryCall(ManagedChannel channel) {
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest request = SumRequest.newBuilder().setFirstNumber(10).setSecondNumber(25).build();
        SumResponse response = stub.sum(request);
        System.out.println(request.getFirstNumber() + " + " + request.getSecondNumber() + " = " + response.getSumResult());
    }

    public void doServerStreamingCall(ManagedChannel channel) {
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        int number = 12345;
        stub.primeNumberDecomposition(PrimeNumberDecompositionRequest
                        .newBuilder().setNumber(number).build())
                        .forEachRemaining(PrimeNumberDecompositionResponse -> System.out.println(PrimeNumberDecompositionResponse.getPrimeFactor()));
    }

    public void doClientStreamingCall(ManagedChannel channel) {

    }

    public static void main(String[] args) {

        CalculatorClient client = new CalculatorClient();
        client.run();
    }
}
