INSERT INTO `CUSTOMER` (`ID`, `FIRST_NAME`, `LAST_NAME`)
VALUES (1, 'Yuandong', 'Hu');

INSERT INTO `CUSTOMER` (`ID`, `FIRST_NAME`, `LAST_NAME`)
VALUES (2, 'Xinwei', 'Wang');

INSERT INTO `CUSTOMER` (`ID`, `FIRST_NAME`, `LAST_NAME`)
VALUES (3, 'Xintong', 'Hu');


INSERT INTO VEHICLE(`ID`, `BRAND`, `MODEL`, `MODEL_YEAR`, `VEHICLE_NUMBER`, `PRICE`)
VALUES (1, 'BMW', 'X3', 2022, 'X123456', 46350.00);

INSERT INTO VEHICLE(`ID`, `BRAND`, `MODEL`, `MODEL_YEAR`, `VEHICLE_NUMBER`, `PRICE`)
VALUES (2, 'BMW', '330i', 2022, null, 47350.00);

INSERT INTO LEASING_CONTRACT(`CONTRACT_NUMBER`, `MONTHLY_RATE`, `CUSTOMER_ID`, `VEHICLE_ID`)
VALUES (123567, 350.00, 1, 1);

INSERT INTO LEASING_CONTRACT(`CONTRACT_NUMBER`, `MONTHLY_RATE`, `CUSTOMER_ID`, `VEHICLE_ID`)
VALUES (123568, 365.00, 2, 2);