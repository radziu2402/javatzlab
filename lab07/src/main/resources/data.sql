INSERT INTO Price_list (service_type, price) VALUES
                                                     ('Internet Radiowy 5Mb/s', 50),
                                                     ('Internet Radiowy 10Mb/s', 60),
                                                     ('Internet Radiowy 15Mb/s', 70),
                                                     ('Światłowód 100Mb/s', 80),
                                                     ('Światłowód 300Mb/s', 100),
                                                     ('Światłowód 600Mb/s', 120);



INSERT INTO Address (street, city, postal_code, country) VALUES
                                                                 ('Kwiatowa 10', 'Warszawa', '00-001', 'Polska'),
                                                                 ('Aleje Jerozolimskie 100', 'Warszawa', '00-002', 'Polska'),
                                                                 ('Morska 30', 'Gdynia', '81-002', 'Polska'),
                                                                 ('Główna 12', 'Gdynia', '81-003', 'Polska'),
                                                                 ('Portowa 4', 'Gdynia', '81-004', 'Polska'),
                                                                 ('Słoneczna 15', 'Kraków', '30-001', 'Polska'),
                                                                 ('Wawelska 8', 'Kraków', '30-002', 'Polska');

INSERT INTO Customer (first_name, last_name, customer_number) VALUES
                                                                      ('Jan', 'Kowalski', 'CUST001'),
                                                                      ('Anna', 'Nowak', 'CUST002'),
                                                                      ('Piotr', 'Wiśniewski', 'CUST003'),
                                                                      ('Katarzyna', 'Wójcik', 'CUST004'),
                                                                      ('Marcin', 'Kowalczyk', 'CUST005');

INSERT INTO installation (address_id, customer_id, router_number, service_type_id) VALUES
                                                                                           (1, 1, 'ROUT0001', 1),
                                                                                           (2, 2, 'ROUT0002', 2),
                                                                                           (3, 3, 'ROUT0003', 3),
                                                                                           (4, 4, 'ROUT0004', 4),
                                                                                           (5, 5, 'ROUT0005', 5),
                                                                                           (6, 1, 'ROUT0003', 6),
                                                                                           (7, 2, 'ROUT0004', 1);
