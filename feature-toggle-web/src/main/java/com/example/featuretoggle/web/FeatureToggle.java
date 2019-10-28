package com.example.featuretoggle.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.featuretoggle.core.Feature;
/**
 * Indicates that a component (controller or method) is only enabled if the feature state is enabled.
 * 
 * Feature state is controlled by various strategies.
 * 
 * @see Feature
 * @author kp7466
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeatureToggle {
	/**
	 * The name of the property to test for this feature. 
	 * If a prefix has been defined, it is applied to compute the full key 
	 * of each property. For instance if the prefix is
	 * {@code app.config} and one value is {@code my-value}, the fully key would be
	 * {@code app.config.my-value}
	 * <p>
	 * Use the dashed notation to specify each property, that is all lower case with a "-"
	 * to separate words (e.g. {@code my-long-property}).
	 * @return the names
	 */
	String feature();
	/**
	 * The string representation of the expected value for the properties. If not
	 * specified, the property must <strong>not</strong> be equals to {@code false}.
	 * @return the expected value
	 */
	String havingValue() default "";
	
	/**
	 * Specify if the condition should match if the property is not set. Defaults to
	 * {@code false}.
	 * @return if should match if the property is missing
	 */
	boolean matchIfMissing() default false;
}
