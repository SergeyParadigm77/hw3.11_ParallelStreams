package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Value("${server.port}")
    private int port;
    @GetMapping("/getPort")
    public int getPort(){
        return port;
    }
    @GetMapping("/get-sum-v1")
    public String getSumV1() {
        long startTime = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a +1)
                .limit(100_000_000)
                .reduce(0, (a, b) -> a + b );

        long finishTime = System.currentTimeMillis();
        return "Calculating takes" + (finishTime - startTime) + "ms, sum = " + sum;
    }
    @GetMapping("/get-sum-v2")
    public String getSumV2() {
        long startTime = System.currentTimeMillis();
        int sum = IntStream
                .iterate(1, a -> a +1)
                .limit(100_000_000)
                .sum();

        long finishTime = System.currentTimeMillis();
        return "Calculating takes" + (finishTime - startTime) + "ms, sum = " + sum;
    }
    @GetMapping("/get-sum-v3")
    public String getSumV3() {
        long startTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i <= 100_000_000; i++) {
            sum += i;
        }

        long finishTime = System.currentTimeMillis();
        return "Calculating takes" + (finishTime - startTime) + "ms, sum = " + sum;
    }
    @GetMapping("/get-sum-v4")
    public String getSumV4() {
        long startTime = System.currentTimeMillis();

        int sum = IntStream
                .iterate(1, a -> a + 1)
                .limit(100_000_000)
                .parallel()
                .reduce(0, Integer::sum);

        long finishTime = System.currentTimeMillis();
        return "Calculating takes" + (finishTime - startTime) + "ms, sum = " + sum;
    }
}
