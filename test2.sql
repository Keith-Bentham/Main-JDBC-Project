CREATE DATABASE IF NOT EXISTS test2;
USE test2;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS customer;
SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE customer (
    cust_id INTEGER AUTO_INCREMENT NOT NULL,
    room VARCHAR(2) NOT NULL,
    firstName VARCHAR(15) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    address1 VARCHAR(20) NOT NULL,
    town VARCHAR(20),
    contactNo VARCHAR(15) NOT NULL,
    PRIMARY KEY (cust_id)
);


SELECT 'INSERTING DATA INTO DATABASE' as 'INFO';

INSERT INTO customer VALUES ( null, '2', 'Keith', 'Bentham', 'Tara', 'Navan', '0838008899');
INSERT INTO customer VALUES ( null, '4','John', 'Castles', 'Athboy', 'Dublin','0486848798');
INSERT INTO customer VALUES ( null, '6','Emer', 'Monaghan', 'Kells', 'Meath', '0856507789'); 



select 
    *
from
    customer;
   
   
   
DROP TABLE IF EXISTS service;
SET FOREIGN_KEY_CHECKS=0;
CREATE TABLE service (
    service_id INTEGER AUTO_INCREMENT,
    Taxi VARCHAR(3) NOT NULL,
    Iron VARCHAR(3) NOT NULL,
    WakeUpCall VARCHAR(3) NOT NULL,
    Breakfast VARCHAR(3) NOT NULL,
    room VARCHAR(2) NOT NULL,
    PRIMARY KEY (service_id)
);

INSERT INTO service VALUES ( null, 'no', 'no','yes','yes','1');
INSERT INTO service VALUES ( null, 'yes', 'no','no','yes','3');
INSERT INTO service VALUES ( null, 'yes', 'no','no','yes','5');
INSERT INTO service VALUES ( null, 'yes', 'yes','no','yes','2');
INSERT INTO service VALUES ( null, 'yes', 'no','yes','yes','8');
INSERT INTO service VALUES ( null, 'no', 'no','no','yes','13');

SELECT 
    *
FROM
    service;

DROP TABLE IF EXISTS room;
SET FOREIGN_KEY_CHECKS=0;
CREATE TABLE room (
    room_id INTEGER AUTO_INCREMENT,
    roomNo VARCHAR(2) NOT NULL,
    roomType VARCHAR(20) NOT NULL,
    roomPrice DECIMAL(7 , 2 ) NOT NULL,
    roomVacant VARCHAR(3) NOT NULL,
    cust_id INTEGER NULL, 
    service_id INTEGER NULL,
    FOREIGN KEY (cust_id)
        REFERENCES customer (cust_id), 
    FOREIGN KEY (service_id)
        REFERENCES service (service_id),
    PRIMARY KEY (room_id)
);

INSERT INTO room VALUES ( null, '1', 'Single',59.99,'no',1,1);
INSERT INTO room VALUES ( null, '2', 'Double',89.99,'no',4,4);
INSERT INTO room VALUES ( null, '3', 'Master',99.99,'no',2,2);
INSERT INTO room VALUES ( null, '4', 'Single',59.99,'yes',null,null);
INSERT INTO room VALUES ( null, '5', 'Single',59.99,'no',3,3);
INSERT INTO room VALUES ( null, '6', 'Master',99.99,'yes',null,null);
INSERT INTO room VALUES ( null, '7', 'Single',59.99,'yes',null,null);
INSERT INTO room VALUES ( null, '8', 'Double',79.99,'no',5,5);
INSERT INTO room VALUES ( null, '9', 'Double',79.99,'yes',null,null);
INSERT INTO room VALUES ( null, '10', 'Double',79.99,'yes',null,null);
INSERT INTO room VALUES ( null, '11', 'Double',79.99,'yes',null,null);
INSERT INTO room VALUES ( null, '12', 'Single',59.99,'yes',null,null);
INSERT INTO room VALUES ( null, '13', 'Double',79.99,'no',6,6);
INSERT INTO room VALUES ( null, '14', 'Single',59.99,'yes',null,null);
INSERT INTO room VALUES ( null, '15', 'Double',79.99,'yes',null,null);

SELECT 
    *
FROM
    room; 