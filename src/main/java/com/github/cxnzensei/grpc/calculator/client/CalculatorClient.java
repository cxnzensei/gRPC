package com.github.cxnzensei.grpc.calculator.client;

import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        // Unary
        //        SumRequest request = SumRequest.newBuilder()
        //                .setFirstNumber(10)
        //                .setSecondNumber(25)
        //                .build();
        //
        //        SumResponse response = stub.sum(request);
        //
        //        System.out.println(request.getFirstNumber() + " + " + request.getSecondNumber() + " = " + response.getSumResult());

        // Streaming Server
        int number = 12344545;
        stub.primeNumberDecomposition(PrimeNumberDecompositionRequest
                .newBuilder().setNumber(number).build())
                .forEachRemaining(PrimeNumberDecompositionResponse -> System.out.println(PrimeNumberDecompositionResponse.getPrimeFactor()));

        channel.shutdown();
    }
}
