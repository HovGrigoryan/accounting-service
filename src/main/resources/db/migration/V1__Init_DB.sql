CREATE SCHEMA IF NOT EXISTS account_service;
ALTER SCHEMA account_service OWNER TO postgres;

CREATE TABLE IF NOT EXISTS account_service.salary_report (
id bigserial PRIMARY KEY,
employee_name VARCHAR,
salary INTEGER,
month VARCHAR
);

--CREATE TABLE IF NOT EXISTS account_service.salary (
--id bigserial PRIMARY KEY,
--currency_type VARCHAR,
--month VARCHAR,
--salary_amount INTEGER,
--pay_date date,
--employee_id BIGINT
--);

