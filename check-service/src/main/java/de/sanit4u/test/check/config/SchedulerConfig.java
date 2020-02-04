package de.sanit4u.test.check.config;

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
@ComponentScan("de.sanit4u.test.check.scheduler")
public class SchedulerConfig {

}
