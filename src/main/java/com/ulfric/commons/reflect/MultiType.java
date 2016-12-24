package com.ulfric.commons.reflect;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import com.ulfric.commons.collect.ImmutableIterator;
import com.ulfric.commons.collect.SingletonIterator;

public abstract class MultiType implements Type, Iterable<Class<?>> {

	private static final Map<Class<?>, MultiType> CACHED_SINGLE_TYPES = new IdentityHashMap<>();

	public static MultiType empty()
	{
		return MultiTypeEmpty.INSTANCE;
	}

	public static MultiType of(Class<?> type)
	{
		Class<?> boxed = ClassUtils.box(type);
		return MultiType.CACHED_SINGLE_TYPES.computeIfAbsent(boxed, MultiTypeOne::new);
	}

	public static MultiType of(Class<?>... types)
	{
		int length = types.length;
		if (length == 1)
		{
			return MultiType.of(types[0]);
		}

		Class<?>[] typesClone = types.clone();
		for (int x = 0; x < length; x++)
		{
			typesClone[x] = ClassUtils.box(typesClone[x]);
		}
		return new MultiTypeMany(Arrays.asList(typesClone));
	}

	public static MultiType of(Collection<Class<?>> types)
	{
		if (types.size() == 1)
		{
			return MultiType.of(types.iterator().next());
		}

		return new MultiTypeMany(new ArrayList<>(types));
	}

	public abstract boolean isInstance(Object value);

	public abstract boolean isAssignableFrom(MultiType type);

	public abstract Set<Class<?>> getCommonTypes();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract String toString();

	private static final class MultiTypeEmpty extends MultiType
	{
		static final MultiTypeEmpty INSTANCE = new MultiTypeEmpty();

		private MultiTypeEmpty() { }

		@Override
		public Iterator<Class<?>> iterator()
		{
			return Collections.emptyIterator();
		}

		@Override
		public boolean isInstance(Object value)
		{
			return false;
		}

		@Override
		public boolean isAssignableFrom(MultiType type)
		{
			return this.equals(type);
		}

		@Override
		public Set<Class<?>> getCommonTypes()
		{
			return Collections.emptySet();
		}

		@Override
		public int hashCode()
		{
			return 0;
		}

		@Override
		public boolean equals(Object object)
		{
			return object == this;
		}

		@Override
		public String toString()
		{
			return void.class.getSimpleName();
		}
	}

	private static final class MultiTypeOne extends MultiType
	{
		MultiTypeOne(Class<?> type)
		{
			this.type = type;
		}

		final Class<?> type;
		private Set<Class<?>> common;

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
		public Set<Class<?>> getCommonTypes()
		{
			if (this.common != null)
			{
				return this.common;
			}

			return this.common = Collections.singleton(this.type);
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
		private Set<Class<?>> sharedTypes;

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
		public Set<Class<?>> getCommonTypes()
		{
			if (this.sharedTypes != null)
			{
				return this.sharedTypes;
			}

			return this.sharedTypes = ClassUtils.getCommonClasses(this.types);
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