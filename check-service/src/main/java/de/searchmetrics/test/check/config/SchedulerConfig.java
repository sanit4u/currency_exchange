package de.searchmetrics.test.check.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * Scheduler config file
 *
 */
@Configuration
@EnableScheduling
@ComponentScan("de.searchmetrics.test.check.scheduler")
public class SchedulerConfig {

}
