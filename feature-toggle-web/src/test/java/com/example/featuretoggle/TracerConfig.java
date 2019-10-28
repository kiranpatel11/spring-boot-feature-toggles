package com.example.featuretoggle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.uber.jaeger.propagation.TextMapCodec;
import com.uber.jaeger.reporters.LoggingReporter;
import com.uber.jaeger.reporters.Reporter;
import com.uber.jaeger.samplers.ConstSampler;
import com.uber.jaeger.samplers.Sampler;

import io.opentracing.Tracer;
import io.opentracing.propagation.Format;

@Profile("test")
@Configuration
public class TracerConfig {
	
	@Bean
    public Tracer tracer() {
    	//Reporter reporter = new InMemoryReporter();
    	Reporter reporter = new LoggingReporter();
    	Sampler sampler = new ConstSampler(false);
    	
    	//Customize for proprietary headers
    	@SuppressWarnings("static-access")
		TextMapCodec myCodec = new TextMapCodec(true).builder()
    			.withSpanContextKey("my-trace-id")
    			.withBaggagePrefix("myctx-").build();    	
    	
    	Tracer tracer = new com.uber.jaeger.Tracer.Builder("myServiceName", reporter, sampler)
    			.registerInjector(Format.Builtin.HTTP_HEADERS, myCodec)
    			.registerExtractor(Format.Builtin.HTTP_HEADERS, myCodec)
    			.build();  
    	
    	//GlobalTracer.register(tracer);
    	
    	return tracer;
    }  
}

