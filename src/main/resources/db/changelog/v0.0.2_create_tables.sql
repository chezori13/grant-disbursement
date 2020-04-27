-- to connect database created psql cmd
-- \c gov_grant 

CREATE TABLE household (
	id serial PRIMARY KEY,
	housing_type varchar(50) NOT NULL
);

CREATE TABLE family_member (
	id serial PRIMARY KEY,
	annual_income bigint,
	dob date,
	gender varchar(50),
	marital_status varchar(50),
	name varchar(255) NOT NULL,
    occupation_type varchar(50),
    household_id bigint REFERENCES household (id)
);

CREATE TABLE family_relationship (
	id serial PRIMARY KEY,
	family_member_id bigint NOT NULL REFERENCES family_member (id),
	family_member_1_id bigint REFERENCES family_member (id),
	relationship_type varchar(50) NOT NULL,
	spouse_name_place_holder varchar(255)
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO govuser;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO govuser;