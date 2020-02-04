CREATE TABLE currency_exchange_rate(
	time_stamp timestamp without time zone NOT NULL DEFAULT now(),
	rate_id TEXT NOT NULL,
	from_currency TEXT NOT NULL,
	to_currency TEXT NOT NULL,
	rate DECIMAL NOT NULL,
	PRIMARY KEY (rate_id)
);


