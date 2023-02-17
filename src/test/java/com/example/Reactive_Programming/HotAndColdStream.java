package com.example.Reactive_Programming;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdStream {

    @Test
    public void coldStream(){
        var fluxStream= Flux.range(1,10);
        fluxStream.subscribe(i-> System.out.println("subscribe1 = " + i));
        fluxStream.subscribe(i-> System.out.println("subscribe2 = " + i));
    }

    @Test
    public void hoatStream() throws InterruptedException {
        var fluxStream= Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1));
        ConnectableFlux<Integer> publish = fluxStream.publish();
        publish.connect();
        publish.subscribe(i-> System.out.println("subscribe1 = " + i));
        Thread.sleep(4000);
        publish.subscribe(i-> System.out.println("subscribe2 = " + i));
        Thread.sleep(10000);
    }
}
