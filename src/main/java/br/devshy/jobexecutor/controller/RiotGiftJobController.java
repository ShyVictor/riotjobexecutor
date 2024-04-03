package br.devshy.jobexecutor.controller;

import br.devshy.jobexecutor.domain.RiotGiftJob;
import br.devshy.jobexecutor.service.RiotGiftJobExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.IntStream;

@Controller
public class RiotGiftJobController {
    @Autowired
    private RiotGiftJobExecutor riotGiftJobExecutor;
    @PostMapping("/api/schedule")
    public ResponseEntity<String> scheduleJob(@RequestBody RiotGiftJob job) {
        IntStream.rangeClosed(1, job.getRepeat())
                .mapToObj(i -> {
                    RiotGiftJob executeJob = new RiotGiftJob(job.getName(), 0, job.getRepeat());
                    executeJob.setTimestamp(job.getTimestamp() * 1000 * i + System.currentTimeMillis());
                    return executeJob;
                })
                .forEach(riotGiftJobExecutor::addJob);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
