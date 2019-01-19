package io;

@FunctionalInterface
public interface Use<T,R,X extends Throwable>{
	R apply(T t) throws X;
}