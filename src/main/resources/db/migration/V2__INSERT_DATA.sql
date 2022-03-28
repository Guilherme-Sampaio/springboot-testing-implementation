INSERT INTO salesmen (id, name, email, salary, commission)
VALUES (nextval('salesmen_sequence'), 'Carl', 'carl@gmail.com', 2000, 5);

INSERT INTO salesmen (id, name, email, salary, commission)
VALUES (nextval('salesmen_sequence'), 'Sophia', 'sophia@gmail.com', 2000, 10);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (nextval('sales_sequence'), 1, 'table', 1, 1200);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (nextval('sales_sequence'), 1, 'chair', 4, 250);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (nextval('sales_sequence'), 2, 'bed', 1, 2500);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (nextval('sales_sequence'), 2, 'wardrobe', 1, 800);