package br.devshy.jobexecutor.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RiotGiftJob implements Comparable<RiotGiftJob> {
    private final String name;
    private long timestamp;
    private final int repeat;
    //jwt
    //quem vai receber
    //presente
    //quantidade
    //repeat

    public RiotGiftJob(String name, long jobDelay, int repeat){
        this.name = name;
        this.timestamp = jobDelay;
        this.repeat = repeat;
    }
    public void execute() {
        System.out.println("Executando job: " + name);
    }

    public boolean canExecute() {
        return System.currentTimeMillis() >= timestamp;
    }

    @Override
    public int compareTo(RiotGiftJob o) {
        return Long.compare(timestamp, o.timestamp);
    }
}
