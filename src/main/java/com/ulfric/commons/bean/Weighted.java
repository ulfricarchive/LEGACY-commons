package com.ulfric.commons.bean;

public interface Weighted<T extends Weighted<T>> extends Comparable<T> {

	int getWeight();

	@Override
	default int compareTo(T that)
	{
		return Integer.compare(this.getWeight(), that.getWeight());
	}

}