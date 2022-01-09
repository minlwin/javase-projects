package com.jdc.bookshohp.utils;

public interface LoadEventPublisher {

	void subcribe(LoadEventSuscriber suscriber);

	public enum Event {
		START, END
	}
}