-- drop everything

DROP SEQUENCE IF EXISTS transaction_seq;
DROP SEQUENCE IF EXISTS message_seq;
DROP SEQUENCE IF EXISTS city_seq;
DROP SEQUENCE IF EXISTS country_seq;
DROP SEQUENCE IF EXISTS gender_seq;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS user_balance;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS gender;

-- gender
CREATE SEQUENCE gender_seq;
CREATE TABLE gender (
	gender_id BIGINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	CONSTRAINT gender_pk PRIMARY KEY (gender_id)
);
-- country
CREATE SEQUENCE country_seq;
CREATE TABLE country (
	country_id BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	CONSTRAINT country_pk PRIMARY KEY (country_id)
);

-- city
CREATE SEQUENCE city_seq;
CREATE TABLE city (
	city_id BIGINT NOT NULL,
	country_id BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	CONSTRAINT city_pk PRIMARY KEY (city_id),
	CONSTRAINT city_fk1 FOREIGN KEY (country_id) REFERENCES country(country_id)
);

-- user_balance
CREATE TABLE user_balance(
	username VARCHAR(20) NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	password_hash VARCHAR(100) NOT NULL,
	date_of_birth DATE NOT NULL,
	city_id BIGINT NOT NULL,
	gender_id BIGINT NOT NULL,
	total_balance BIGINT NOT NULL,
	change_date TIMESTAMP NOT NULL,
	intro_text VARCHAR(100) NOT NULL,
	CONSTRAINT user_balance_pk PRIMARY KEY (username),
	CONSTRAINT user_balance_fk2 FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
);

-- message
CREATE SEQUENCE message_seq;
CREATE TABLE message (
	message_id BIGINT NOT NULL,
	username_from VARCHAR(20) NOT NULL,
	username_to VARCHAR(20) NOT NULL,
	date_and_time TIMESTAMP NOT NULL,
	message_text TEXT NOT NULL,
	CONSTRAINT message_pk PRIMARY KEY (message_id),
	CONSTRAINT message_fk1 FOREIGN KEY (username_from) REFERENCES user_balance(username),
	CONSTRAINT message_fk2 FOREIGN KEY (username_to) REFERENCES user_balance(username)
);

-- transaction
CREATE SEQUENCE transaction_seq;
CREATE TABLE transaction (
	transaction_id BIGINT NOT NULL,
	username_from VARCHAR(20) NOT NULL,
	username_to VARCHAR(20) NOT NULL,
	date_and_time TIMESTAMP NOT NULL,
	transferred_amount BIGINT NOT NULL,
	transfer_comment VARCHAR(100),
	CONSTRAINT transaction_pk PRIMARY KEY (transaction_id),
	CONSTRAINT transaction_fk1 FOREIGN KEY (username_from) REFERENCES user_balance(username),
	CONSTRAINT transaction_fk2 FOREIGN KEY (username_to) REFERENCES user_balance(username)
);
