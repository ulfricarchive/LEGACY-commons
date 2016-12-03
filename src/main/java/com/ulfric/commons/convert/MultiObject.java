package com.ulfric.commons.convert;

import java.util.Objects;
import java.util.Optional;

public abstract class MultiObject {

	public static MultiObject of(Object object)
	{
		Objects.requireNonNull(object);
		return new MultiObjectSingle(object);
	}

	public abstract MultiType toType();

	public abstract Optional<?> firstMatch(MultiType type);

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	private static final class MultiObjectSingle extends MultiObject
	{
		MultiObjectSingle(Object value)
		{
			this.value = value;
		}

		private final Object value;

		@Override
		public MultiType toType()
		{
			return MultiType.of(this.value.getClass());
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
		public Optional<?> firstMatch(MultiType type)
		{
			if (type.isInstance(this.value))
			{
				return Optional.of(this.value);
			}

			return Optional.empty();
		}
	}

}