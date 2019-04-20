package io;

@FunctionalInterface
public interface Reader<T,R,X extends Throwable>{
	R apply(T t) throws X;
}