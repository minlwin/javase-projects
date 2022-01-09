package com.jdc.bookshohp.utils;

import com.jdc.bookshop.utils.LoadEventPublisher.Event;

public interface LoadEventSucriber {

	void fire(Event event);
}