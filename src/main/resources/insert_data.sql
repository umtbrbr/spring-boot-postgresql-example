-- Delete tables --
DELETE FROM demo.product;
DELETE FROM demo.category;
DELETE FROM demo.campaign;

-- Insert categories --
INSERT INTO demo.category (name) VALUES ('T-SHIRT'), ('GÖMLEK'), ('KAZAK'), ('KOZMETİK'), ('ELEKTRONİK');

-- Insert products --
INSERT INTO demo.product (name, price, categoryId) VALUES
  ('U.S. Polo Assn. Mavi T-shirt', 59.95, (SELECT id from demo.category WHERE name = 'T-SHIRT')),
  ('Pierre Cardin Bordo T-shirt', 59.95, (SELECT id from demo.category WHERE name = 'T-SHIRT')),
  ('DeFacto Slim Fit Gömlek', 19.99, (SELECT id from demo.category WHERE name = 'GÖMLEK')),
  ('Kenzo Kırmızı Kazak', 429.99, (SELECT id from demo.category WHERE name = 'KAZAK')),
  ('Y-London Lacivert Kazak', 19.99, (SELECT id from demo.category WHERE name = 'KAZAK')),
  ('Versace Eros Parfüm 200 ml', 254.99, (SELECT id from demo.category WHERE name = 'KOZMETİK')),
  ('Apple Iphone 7 64 GB', 3095.00, (SELECT id from demo.category WHERE name = 'ELEKTRONİK'));

-- Insert campaigns
INSERT INTO demo.campaign (name, campaign_type, campaign_type_id, campaign_type_name, discount_type, discount, max_discount) VALUES
  ('Ucuz Iphone', 'PRODUCT', (SELECT id FROM demo.product WHERE demo.product.name = 'Apple Iphone 7 64 GB'), 'Apple Iphone 7 64 GB', 'RATE', 5, 100),
  ('Mavi T-shirt İndirimde', 'PRODUCT', (SELECT id FROM demo.product WHERE demo.product.name = 'U.S. Polo Assn. Mavi T-shirt'), 'U.S. Polo Assn. Mavi T-shirt', 'PRICE', 50, 0),
  ('Kazaklar Daha Ucuz', 'CATEGORY', (SELECT id from demo.category WHERE name = 'KAZAK'), 'Kazak', 'RATE', 10, 30),
  ('İndirimli Gömlekler', 'CATEGORY', (SELECT id from demo.category WHERE name = 'GÖMLEK'), 'Gömlek', 'PRICE', 20, 0);