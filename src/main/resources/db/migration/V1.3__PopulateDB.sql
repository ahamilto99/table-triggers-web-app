-- POPULATE PRODUCTS
INSERT INTO PRODUCTS VALUES (SEQ_PRODUCTS_ID.NEXTVAL, 'Widget', 1000, 1.25);
INSERT INTO PRODUCTS VALUES (SEQ_PRODUCTS_ID.NEXTVAL, 'Samsung 970 Pro SSD', 500, 219.95);
INSERT INTO PRODUCTS VALUES (SEQ_PRODUCTS_ID.NEXTVAL, 'Intel Xeon Processor', 200, 499.99);

ALTER TRIGGER BUSINESS_HOURS_TRIGGER DISABLE;

INSERT INTO ORDERS (ID, CUSTOMER_NAME, PRODUCT_ID, QUANTITY) VALUES (SEQ_ORDERS_ID.NEXTVAL, 'John Wayne', 3, 75);

ALTER TRIGGER BUSINESS_HOURS_TRIGGER ENABLE;

COMMIT;