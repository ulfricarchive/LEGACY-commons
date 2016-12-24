package com.ulfric.commons.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

abstract class MultiObject {

	static MultiObject empty()
	{
		return MultiObjectEmpty.INSTANCE;
	}

	static MultiObject of(Object object)
	{
		Objects.requireNonNull(object);
		return new MultiObjectSingle(object);
	}

	static MultiObject of(Object... objects)
	{
		Objects.requireNonNull(objects);
		for (Object object : objects)
		{
			Objects.requireNonNull(object);
		}
		return new MultiObjectMany(Arrays.asList(objects));
	}

	public abstract MultiType toType();

	public abstract Optional<?> firstMatch(MultiType type);

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();

	static final class MultiObjectEmpty extends MultiObject
	{
		static final MultiObjectEmpty INSTANCE = new MultiObjectEmpty();

		private MultiObjectEmpty() { }

		private final Optional<?> optionalOfThis = Optional.of(this);

		@Override
		public MultiType toType()
		{
			return MultiType.empty();
		}

		@Override
		public Optional<?> firstMatch(MultiType type)
		{
			return type == MultiType.empty() ? this.optionalOfThis : Optional.empty();
		}

		@Override
		public boolean equals(Object object)
		{
			return object == this;
		}

		@Override
		public int hashCode()
		{
			return 0;
		}

		@Override
		public String toString()
		{
			return "";
		}
	}

	static final class MultiObjectSingle extends MultiObject
	{
		MultiObjectSingle(Object value)
		{
			this.value = value;
		}

		final Object value;
		private MultiType type;

		@Override
		public MultiType toType()
		{
			if (this.type != null)
			{
				return this.type;
			}

			return this.type = MultiType.of(this.value.getClass());
		}

		@Override
		public Optional<?> firstMatch(MultiType type)
		{
			Object value = this.value;
			if (type.isInstance(value))
			{
				return Optional.of(value);
			}

			return Optional.empty();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(this.value);
		}

		@Override
		public boolean equals(Object object)
		{
			if (object == this)
			{
				return true;
			}

			if (!(object instanceof MultiObjectSingle))
			{
				return false;
			}

			MultiObjectSingle that = (MultiObjectSingle) object;

			return this.value.equals(that.value);
		}

		@Override
		public String toString()
		{
			return this.value.toString();
		}
	}

	static final class MultiObjectMany extends MultiObject
	{
		MultiObjectMany(Iterable<?> values)
		{
			this.values = values;
		}

		private final Iterable<?> values;
		private MultiType type;

		@Override
		public MultiType toType()
		{
			if (this.type != null)
			{
				return this.type;
			}

			List<Class<?>> classes = new ArrayList<>();
			for (Object value : this.values)
			{
				classes.add(value.getClass());
			}
			return this.type = MultiType.of(classes);
		}

		@Override
		public Optional<?> firstMatch(MultiType type)
		{
			for (Object value : this.values)
			{
				if (type.isInstance(value))
				{
					return Optional.of(value);
				}
			}

			return Optional.empty();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(this.values);
		}

		@Override
		public boolean equals(Object object)
		{
			if (object == this)
			{
				return true;
			}

			if (!(object instanceof MultiObjectMany))
			{
				return false;
			}

			MultiObjectMany that = (MultiObjectMany) object;

			return this.values.equals(that.values);
		}

		@Override
		public String toString()
		{
			return this.values.toString();
		}
	}

}