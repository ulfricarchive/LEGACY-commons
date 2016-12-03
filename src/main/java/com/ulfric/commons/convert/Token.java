package com.ulfric.commons.convert;

import java.util.Objects;
import java.util.Optional;

public abstract class Token {

	public static Token of(Object object)
	{
		Objects.requireNonNull(object);
		return new TokenOne(object);
	}

	public abstract MultiType toTypeHash();

	public abstract Optional<?> firstMatch(MultiType type);

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	private static final class TokenOne extends Token
	{
		TokenOne(Object value)
		{
			this.value = value;
		}

		private final Object value;

		@Override
		public MultiType toTypeHash()
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

			if (!(object instanceof TokenOne))
			{
				return false;
			}

			TokenOne that = (TokenOne) object;

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