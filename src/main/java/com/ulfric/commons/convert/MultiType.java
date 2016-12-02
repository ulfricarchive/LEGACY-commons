package com.ulfric.commons.convert;

import java.lang.reflect.Type;
import java.util.Objects;

abstract class MultiType implements Type {

	static MultiType of(Class<?> type)
	{
		return new TypeHashOne(type);
	}

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object object);

	public abstract boolean isInstance(Object value);

	private static final class TypeHashOne extends MultiType
	{
		TypeHashOne(Class<?> type)
		{
			this.type = type;
		}

		private final Class<?> type;

		@Override
		public boolean isInstance(Object value)
		{
			return this.type.isInstance(value);
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

			if (!(object instanceof TypeHashOne))
			{
				return false;
			}

			TypeHashOne that = (TypeHashOne) object;

			return this.type == that.type;
		}
	}

}