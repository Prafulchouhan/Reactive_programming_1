package com.example.Reactive_Programming;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BackPressureTest {
    @Test
    public void backPressureTest(){
        var number= Flux.range(1,100).log();
        number.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if(value==3)cancel();
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Complete!!");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }
        });
    }

    @Test
    public void backPressureDropTest(){
        var number= Flux.range(1,100).log();
        number.onBackpressureDrop(integer -> {
                    System.out.println("integer = " + integer);
                })
                .subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if(value==3)hookOnCancel();
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Complete!!");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }
        });
    }

    @Test
    public void backPressureBufferTest(){
        var number= Flux.range(1,100).log();
        number
                .onBackpressureBuffer(10,i-> System.out.println("i = " + i))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if(value==3)hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Complete!!");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }
                });
    }

    @Test
    public void backPressureErrorTest(){
        var number= Flux.range(1,100).log();
        number
                .onBackpressureError()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if(value==3)hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Complete!!");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        System.out.println("throwable = " + throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }
                });
    }
}
