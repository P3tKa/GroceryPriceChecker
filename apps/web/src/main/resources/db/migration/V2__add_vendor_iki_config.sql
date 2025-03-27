INSERT INTO vendors (id, image_url, country_of_origin)
SELECT 'IKI', null, 'LT'
WHERE NOT EXISTS (SELECT 1 FROM vendors WHERE id = 'IKI');

INSERT INTO vendors (id, image_url, country_of_origin)
SELECT 'RIMI', null, 'LT'
WHERE NOT EXISTS (SELECT 1 FROM vendors WHERE id = 'RIMI');

INSERT INTO vendors (id, image_url, country_of_origin)
SELECT 'BARBORA', null, 'LT'
WHERE NOT EXISTS (SELECT 1 FROM vendors WHERE id = 'BARBORA');