package de.searchmetrics.test.check.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.searchmetrics.test.check.exception.SchedulerException;
import de.searchmetrics.test.check.service.ForexDataService;

/**
 * 
 * It periodically checks the {@code ForexDataService} and retrieves the latest
 * BTC2USD conversion rate
 */
@Component
public class CheckServiceScheduler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private ForexDataService forexService;

	@Autowired
	public CheckServiceScheduler(ForexDataService forexService) {
		this.forexService = forexService;
	}

	/**
	 * 
	 */
	@Scheduled(fixedRateString = "${check.period.in.milliseconds}")
	public void checkNUpdateRates() {
		log.info("Scheduler for latest BTC2USD currency rate is started.");

		try {

			forexService.checkNUpdateForexServiceForLatestBTC2USDRate();

		} catch (Exception e) {
			log.error("Error while Scheduling latest BTC2USD currency rate.", e);
			throw new SchedulerException("Error while Scheduling latest BTC2USD currency rate.", e);
		}

		log.info("Scheduler for latest BTC2USD currency rate is completed.");
	}

}
