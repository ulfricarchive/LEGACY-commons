package com.ulfric.commons.convert;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import com.ulfric.commons.collect.CollectionUtils;
import com.ulfric.commons.collect.ImmutableIterator;
import com.ulfric.commons.collect.SingletonIterator;
import com.ulfric.commons.reflect.PrimitiveUtils;

abstract class MultiType implements Type, Iterable<Class<?>> {

	private static final Map<Class<?>, MultiType> CACHED_SINGLE_TYPES = new IdentityHashMap<>();

	static MultiType of(Class<?> type)
	{
		Class<?> boxed = PrimitiveUtils.box(type);
		return MultiType.CACHED_SINGLE_TYPES.computeIfAbsent(boxed, MultiTypeOne::new);
	}

	static MultiType of(Class<?>... types)
	{
		int length = types.length;
		if (length == 1)
		{
			return MultiType.of(types[0]);
		}

		Class<?>[] typesClone = types.clone();
		for (int x = 0; x < length; x++)
		{
			typesClone[x] = PrimitiveUtils.box(typesClone[x]);
		}
		return new MultiTypeMany(Arrays.asList(typesClone));
	}

	static MultiType of(Iterable<Class<?>> types)
	{
		return new MultiTypeMany(CollectionUtils.copyToList(types));
	}

	public abstract boolean isInstance(Object value);

	public abstract boolean isAssignableFrom(MultiType type);

	public abstract Class<?> getSharedType();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract String toString();

	private static final class MultiTypeOne extends MultiType
	{
		MultiTypeOne(Class<?> type)
		{
			this.type = type;
		}

		final Class<?> type;

		@Override
		public boolean isInstance(Object value)
		{
			return this.type.isInstance(value);
		}

		@Override
		public boolean isAssignableFrom(MultiType type)
		{
			if (type == this)
			{
				return true;
			}

			if (type instanceof MultiTypeOne)
			{
				MultiTypeOne that = (MultiTypeOne) type;

				return this.type.isAssignableFrom(that.type);
			}
			else if (type == null)
			{
				return false;
			}

			Class<?> thisType = this.type;
			for (Class<?> thatType : type)
			{
				if (!thisType.isAssignableFrom(thatType))
				{
					return false;
				}
			}

			return true;
		}
	
		@Override
		public Iterator<Class<?>> iterator()
		{
			return new SingletonIterator<>(this.type);
		}

		@Override
		public Class<?> getSharedType()
		{
			return this.type;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(this.type);
		}

		@Override
		public boolean equals(Object object)
		{
			if (object == this)
			{
				return true;
			}

			if (!(object instanceof MultiTypeOne))
			{
				return false;
			}

			MultiTypeOne that = (MultiTypeOne) object;

			return this.type == that.type;
		}

		@Override
		public String toString()
		{
			return this.type.getSimpleName();
		}
	}

	private static final class MultiTypeMany extends MultiType
	{
		MultiTypeMany(Iterable<Class<?>> types)
		{
			this.types = types;
		}

		private final Iterable<Class<?>> types;

		@Override
		public boolean isInstance(Object value)
		{
			for (Class<?> type : this.types)
			{
				if (!type.isInstance(value))
				{
					return false;
				}
			}

			return true;
		}

		@Override
		public boolean isAssignableFrom(MultiType type)
		{
			if (type == this)
			{
				return true;
			}

			if (type instanceof MultiTypeOne)
			{
				MultiTypeOne o = (MultiTypeOne) type;
				Class<?> other = o.type;

				for (Class<?> thisType : this.types)
				{
					if (!thisType.isAssignableFrom(other))
					{
						return false;
					}
				}
				return true;
			}
			else if (type == null)
			{
				return false;
			}

			for (Class<?> thisType : this.types)
			{
				for (Class<?> o : type)
				{
					if (!thisType.isAssignableFrom(o))
					{
						return false;
					}
				}
			}

			return true;
		}
	
		@Override
		public Iterator<Class<?>> iterator()
		{
			return ImmutableIterator.of(this.types);
		}

		@Override
		public Class<?> getSharedType()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(this.types);
		}

		@Override
		public boolean equals(Object object)
		{
			if (object == this)
			{
				return true;
			}

			if (!(object instanceof MultiTypeMany))
			{
				return false;
			}

			MultiTypeMany that = (MultiTypeMany) object;

			return this.types.equals(that.types);
		}

		@Override
		public String toString()
		{
			StringJoiner joiner = new StringJoiner(", ");
			this.types.forEach(type -> joiner.add(type.getSimpleName()));
			return joiner.toString();
		}
	}

}