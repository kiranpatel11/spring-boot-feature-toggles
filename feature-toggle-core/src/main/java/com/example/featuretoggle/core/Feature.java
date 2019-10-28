package com.example.featuretoggle.core;
/**
 * Feature whose activation status is controlled externally,
 * may be changed dynamically at runtime, depending on the applicable {@link ActivationStrategy}.
 * 
 * @see ActivationStrategy
 * @see FeatureManager
 * @author kp7466
 *
 */
public class Feature {	
	/**
	 * Unique Key identifying the feature, to be used by the system
	 * 
	 * @return
	 */
	private String key;
	/**
	 * Human friendly name of the feature.
	 * 
	 * @return
	 */
	private String name;
	
	public Feature(String key) {
		this(key, "");
	}
	
	public Feature(String key, String name) {
		super();
		this.key = key;
		this.name = name;
	}

	public String key() {
		return this.key;
	}

	public String name() {
		return this.name;
	}
		
}
