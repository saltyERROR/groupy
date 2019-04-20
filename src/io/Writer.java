package io;

@FunctionalInterface
public interface Writer<T,X extends Throwable>{
    void consume(T t) throws X;
}
