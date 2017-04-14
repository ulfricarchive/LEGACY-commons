package com.ulfric.commons.data;

public interface Persistent {

	void save();

	void markForWrite();

	void unmarkForWrite();

}