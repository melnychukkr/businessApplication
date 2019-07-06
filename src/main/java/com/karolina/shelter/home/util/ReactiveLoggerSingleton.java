package com.karolina.shelter.home.util;

import org.slf4j.MDC;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ReactiveLoggerSingleton {

    private static ReactiveLoggerSingleton loggerSingleton = null;

    public static ReactiveLoggerSingleton getReactiveLogger()
    {
        if (loggerSingleton == null)
            loggerSingleton = new ReactiveLoggerSingleton();

        return loggerSingleton;
    }

    public <T> Consumer<Signal<T>> logOnNext(
            Consumer<T> log) {
        return signal -> {
            if (signal.getType() != SignalType.ON_NEXT) return;

            Optional<Map<String, String>> maybeContextMap
                    = signal.getContext().getOrEmpty("context-map");

            if (!maybeContextMap.isPresent()) {
                log.accept(signal.get());
            } else {
                MDC.setContextMap(maybeContextMap.get());
                try {
                    log.accept(signal.get());
                } finally {
                    MDC.clear();
                }
            }
        };
    }
}
