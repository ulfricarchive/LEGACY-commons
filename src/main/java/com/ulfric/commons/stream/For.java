package com.ulfric.commons.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class For<T> {

	public static <T> For<T> each(T[] values)
	{
		Objects.requireNonNull(values);

		return For.each(Stream.of(values));
	}

	public static <T> For<T> each(Collection<T> collection)
	{
		Objects.requireNonNull(collection);

		return For.each(collection.stream());
	}

	public static <T> For<T> each(BaseStream<T, ?> stream)
	{
		Objects.requireNonNull(stream);

		return new For<>(StreamUtils.erase(stream));
	}

	public static <T> For<T> each(Stream<T> stream)
	{
		Objects.requireNonNull(stream);

		return new For<>(stream);
	}

	private final Stream<T> stream;

	private For(Stream<T> stream)
	{
		this.stream = stream;
	}

	public <R> For<R> collect(Function<T, R> function)
	{
		List<R> values = new ArrayList<>();

		this.stream.forEach(value ->
				values.add(function.apply(value))
		);

		return new For<>(values.stream());
	}

	public <R> For<R> arrayCollect(ArrayFunction<T, R> function)
	{
		List<R> values = new ArrayList<>();

		this.stream.forEach(value ->
				values.addAll(Arrays.asList(function.apply(value)))
		);

		return new For<>(values.stream());
	}

	public <R> For<R> streamCollect(StreamedFunction<T, R> function)
	{
		List<R> values = new ArrayList<>();

		this.stream.forEach(value ->
				values.addAll(function.apply(value).collect(Collectors.toList()))
		);

		return new For<>(values.stream());
	}

	public <R> For<R> baseStreamCollect(BaseStreamedFunction<T, R> function)
	{
		List<R> values = new ArrayList<>();

		this.stream.forEach(value ->
				values.addAll(
						StreamUtils.erase(function.apply(value))
								.collect(Collectors.toList())
				)
		);

		return new For<>(values.stream());
	}

	public <R> For<R> multiCollect(CollectedFunction<T, R> function)
	{
		List<R> values = new ArrayList<>();

		this.stream.forEach(value ->
				values.addAll(function.apply(value))
		);

		return new For<>(values.stream());
	}

	public void each(Consumer<T> consumer)
	{
		this.stream.forEach(consumer);
	}

	@FunctionalInterface
	public interface ArrayFunction<T, R>
	{
		R[] apply(T t);
	}

	@FunctionalInterface
	public interface StreamedFunction<T, R>
	{
		Stream<R> apply(T t);
	}

	@FunctionalInterface
	public interface BaseStreamedFunction<T, R>
	{
		BaseStream<R, ?> apply(T t);
	}

	@FunctionalInterface
	public interface CollectedFunction<T, R>
	{
		Collection<R> apply(T t);
	}

}
