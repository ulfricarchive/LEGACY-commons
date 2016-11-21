package com.ulfric.commons.reflect;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.object.Serializability;

public class MemberUtils implements Util {

	public static Serializability getSerializability(Member member)
	{
		if (member == null)
		{
			return Serializability.NULL;
		}

		if (member.isSynthetic())
		{
			return Serializability.NOT_SERIALIZABLE;
		}

		return MemberUtils.getModifierSerializability(member.getModifiers());
	}

	private static Serializability getModifierSerializability(int modifier)
	{
		if (Modifier.isStatic(modifier) || Modifier.isTransient(modifier))
		{
			return Serializability.NOT_SERIALIZABLE;
		}

		return Serializability.SERIALIZABLE;
	}

	private MemberUtils()
	{
		Util.onConstruct();
	}

}