-- ROW-LEVEL TRIGGER ENSURES THAT THERE IS ENOUGH OF A PRODUCT IN STOCK
-- BEFORE PLACING OR UPDATING AN ORDER; IT ALSO INPUTS/UPDATES THE TOTAL PRICE
-- FOR THE ORDER
CREATE OR REPLACE TRIGGER INVENTORY_MANAGEMENT_TRIGGER
  BEFORE
  INSERT OR UPDATE OR DELETE
  ON ORDERS
  FOR EACH ROW
DECLARE
  -- TRIGGER VARIABLES
  STOCK NUMBER;
  PRICE NUMBER(20,2);
  -- EXCEPTION
  NOT_ENOUGH_STOCK EXCEPTION;
  PRAGMA EXCEPTION_INIT(NOT_ENOUGH_STOCK, -20002); -- PRAGMA MEANS COMPILER DIRECTIVE AND IS USED IN THIS CASE TO BIND NEWLY CREATED 
  												   -- NOT_ENOUGH_STOCK EXCEPTION T0 THE ERROR CODE -20002; CODE MUST BE BTW. -20001 AND -20999	
BEGIN
  -- CASE STATEMENT
  CASE 
    WHEN INSERTING THEN
      -- CHECK INVENTORY
      IF STOCK <= :NEW.QUANTITY THEN
        RAISE_APPLICATION_ERROR(-20002, 'Not enough product in stock'); 
      END IF;
      -- INITIALIZE VARIABLES
      SELECT INVENTORY_COUNT, UNIT_PRICE
      INTO STOCK, PRICE 
      FROM PRODUCTS
      WHERE ID = :NEW.PRODUCT_ID;  -- MUST BE THE NEW VALUE IN ORDER FOR IT TO WORK WITH INSERTS
      -- SET THE TOTAL PRICE OF THE ORDER
      :NEW.ORDER_PRICE := :NEW.QUANTITY * PRICE;
      -- SET THE DATE/TIME FOR THE ORDER
      :NEW.TSTMP := CURRENT_TIMESTAMP;
      -- UPDATE THE INVENTORY
      UPDATE PRODUCTS SET INVENTORY_COUNT = (INVENTORY_COUNT - :NEW.QUANTITY) WHERE ID = :NEW.PRODUCT_ID;
    WHEN UPDATING THEN
      -- CHECK INVENTORY
      IF STOCK <= :NEW.QUANTITY - :OLD.QUANTITY THEN 
        RAISE_APPLICATION_ERROR(-20002, 'Not enough product in stock');
      END IF;
      -- INITIALIZE VARIABLES
      SELECT INVENTORY_COUNT, UNIT_PRICE
      INTO STOCK, PRICE 
      FROM PRODUCTS
      WHERE ID = :OLD.PRODUCT_ID;  -- CAN BE EITHER THE NEW OR OLD VALUE SINCE A CUSTOMER CANNOT CHANGE THE PRODUCT BEING ORDERED
      -- SET THE TOTAL PRICE OF THE ORDER
      :NEW.ORDER_PRICE := :NEW.QUANTITY * PRICE;
      -- SET THE DATE/TIME FOR THE ORDER
      :NEW.TSTMP := CURRENT_TIMESTAMP;
      -- UPDATE THE INVENTORY
      UPDATE PRODUCTS SET INVENTORY_COUNT = INVENTORY_COUNT - (:NEW.QUANTITY - :OLD.QUANTITY) WHERE ID = :OLD.PRODUCT_ID;
    WHEN DELETING THEN
      -- REPLENISH INVENTORY
      UPDATE PRODUCTS SET INVENTORY_COUNT = INVENTORY_COUNT + :OLD.QUANTITY WHERE ID = :OLD.PRODUCT_ID;
  END CASE;
END;
/