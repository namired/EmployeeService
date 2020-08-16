DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS ADDRESS;

CREATE TABLE EMPLOYEE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  date_of_birth DATE DEFAULT NULL,
  address_id INT
);
CREATE TABLE ADDRESS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  line1 VARCHAR(250) NOT NULL,
  line2 VARCHAR(250) DEFAULT NULL,
  city VARCHAR(25) NOT NULL,
  state VARCHAR(25) NOT NULL,
  country VARCHAR(25) NOT NULL,
  zip INT NOT NULL
);

alter table EMPLOYEE add constraint employee_address_mapping_key foreign key (address_id) references address(id);



