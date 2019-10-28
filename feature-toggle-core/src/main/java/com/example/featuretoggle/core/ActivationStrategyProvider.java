package com.example.featuretoggle.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivationStrategyProvider {
	
	
	private static ActivationStrategyProvider instance;
	
	/**
	 * Singleton
	 */
	private ActivationStrategyProvider() {}

	@Autowired
	private ActivationStrategyProvider(List<ActivationStrategy> strategies) {
		super();
		this.strategies = strategies;
	}
	
	/**
	 * Initialize static instance
	 */
	@PostConstruct
	private void initialize() {
		instance = this;
	}	
	
	private List<ActivationStrategy> strategies;
	
	/***
	 * Package default scope to make it only visible to this package.
	 * 
	 * @param feature
	 * @return
	 */
	static ActivationStrategy getApplicableStrategy(Feature feature) {
		
		ActivationStrategy applicable = null;
		
		for(ActivationStrategy strategy : instance.strategies) {
			if(strategy.isApplicable(feature)) {
				applicable = strategy;
				break;
			}
		}
		return applicable;
	}
}
