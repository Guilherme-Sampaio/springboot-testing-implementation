INSERT INTO salesmen (id, name, email, salary, commission)
VALUES (1, 'Carl', 'carl@gmail.com', 2000, 5);

INSERT INTO salesmen (id, name, email, salary, commission)
VALUES (2, 'Sophia', 'sophia@gmail.com', 2000, 10);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (1, 1, 'table', 1, 1200);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (2, 1, 'chair', 4, 250);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (3, 2, 'bed', 1, 2500);

INSERT INTO sales (id, salesmen_id, product, quantity, value)
VALUES (4, 2, 'wardrobe', 1, 800);