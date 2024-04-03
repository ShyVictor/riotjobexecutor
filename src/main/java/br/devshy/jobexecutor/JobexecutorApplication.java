package br.devshy.jobexecutor;

import br.devshy.jobexecutor.service.RiotGiftJobExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JobexecutorApplication {

	@Autowired
	private RiotGiftJobExecutor jobExecutor;

	public static void main(String[] args) {
		System.out.println("Spring Boot Thread: " + Thread.currentThread().getName());
		SpringApplication.run(JobexecutorApplication.class, args);
	}

	@Bean
	public CommandLineRunner schedulingRunner() {
		return args -> {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(() -> {
				try {
					System.out.println("Scheduler thread: " + Thread.currentThread().getName());
					System.out.println("Iniciando a thread do Executor!");
					jobExecutor.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			executor.shutdown();
		};
	}

}
