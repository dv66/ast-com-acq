package com.cokreates.grp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;


public abstract class BaseService<A, B> {

	@Autowired
	protected ApplicationEventPublisher publisher;
    
    @Autowired
    protected ApplicationContext ctx;

	public abstract B perform(AuthUser user, String url, String version, A request, B response) throws AppException;


}
