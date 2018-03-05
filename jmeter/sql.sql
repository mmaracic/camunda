truncate table dta_token_statistic cascade;
truncate table dta_token_texts cascade;
truncate table dta_token cascade;
truncate table dta_text cascade;

--Oracle
CREATE USER camunda IDENTIFIED BY camunda;
GRANT CONNECT, RESOURCE, DBA TO camunda;
