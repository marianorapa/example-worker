package com.example.worker;

import com.example.worker.dto.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class WorkerApplication {

	@Autowired
	StreamBridge bridge;

	Logger logger = LoggerFactory.getLogger(WorkerApplication.class);

	@Autowired
	RedisHandler redis;

	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

	@Bean
	public Function<Task, String> sink() {
		return task -> {
			logger.info("Received task to process: {}", task);
			// Do some processing

			BigInteger factorial = calculateFactorial(task.getNumber());
			logger.info("Result of calculus {}", factorial);
			Task result = new Task(task.getUuid(), task.getTaskNumber(), 0, factorial);
			redis.save(result);

			// Output result
			return task.getUuid();
		};

	}

	private BigInteger calculateFactorial(int number) {
		BigInteger result = BigInteger.valueOf(number);
		for (int i = 1; i <= number; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}

		return result;
	}


}
