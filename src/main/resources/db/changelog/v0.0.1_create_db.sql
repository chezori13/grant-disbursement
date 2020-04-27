-- in terminal type psql to login to postgres
CREATE DATABASE gov_grant
  WITH OWNER postgres;

-- create user
create user govuser with PASSWORD 'g0vus3r';
grant all privileges on DATABASE gov_grant to govuser;