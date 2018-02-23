/*
 * CORSFilter.java
 * 
 * Copyright (c) 2015 by General Electric Company. All rights reserved.
 * 
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */
package com.ge.cab.corsfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * CORSFilter class.
 */
@Component
public class CORSFilter implements Filter {

	private static final String OPTIONS = "OPTIONS";
	/** The Constant logger. */
	private final Logger logger = LoggerFactory.getLogger(CORSFilter.class);


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse)res;
		HttpServletRequest request = (HttpServletRequest)req;
		setHeaderIfNotPresent( response, "Access-Control-Allow-Origin", "*" );
		if (  OPTIONS.equalsIgnoreCase(request.getMethod())) {
			logger.debug( "Request [{}] : [{}]"+ request.getMethod() +" "+ request.getRequestURI() );
			setHeaderIfNotPresent( response, "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH,OPTIONS" );
			setHeaderIfNotPresent( response, "Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept" );
			setHeaderIfNotPresent( response, "Access-Control-Max-Age", "10" );

		}
		chain.doFilter( req, response );

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init( FilterConfig filterConfig ) throws ServletException {
		logger.info( "Initializaing the CORSFilter" );
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		logger.info( "Destroying the CORSFilter" );
	}

	/**
	 * Sets the header if not present.
	 *
	 * @param response the response
	 * @param key the key
	 * @param value the value
	 */
	private void setHeaderIfNotPresent( HttpServletResponse response, String key, String value ) {
		logger.debug( "Checking for header [{}]:[{}] key is " +key +"value is " +value);
		if ( !response.containsHeader( key ) ) {
			logger.debug( "Header not present [{}]:[{}] : Setting Now " +key +"value is " +value);
			response.setHeader( key, value );
		}
	}
}
