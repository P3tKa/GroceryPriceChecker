CREATE table IF NOT EXISTS groceries (
    id varchar PRIMARY KEY,
    name varchar NOT NULL,
    country_of_origin varchar,
    supplier varchar,
    category varchar,
    sub_category varchar,
    barcodes TEXT[],
    quantity float NOT NULL,
    unit varchar NOT NULL,
    image_url varchar
);

CREATE table IF NOT EXISTS vendors (
    id varchar PRIMARY KEY,
    name varchar NOT NULL UNIQUE,
    image_url varchar,
    country_of_origin varchar
);

CREATE table IF NOT EXISTS groceries_vendors (
    id varchar PRIMARY KEY,
    grocery_id varchar REFERENCES groceries(id),
    vendor_id varchar REFERENCES vendors(id),
    price float NOT NULL,
    price_with_discount float,
    price_with_loyalty_card float,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approved boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS grocery_price_history (
    id SERIAL PRIMARY KEY,
    grocery_vendor_id varchar REFERENCES groceries_vendors(id),
    price float NOT NULL,
    price_with_discount float,
    price_with_loyalty_card float,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_groceries_vendors_grocery_id ON groceries_vendors(grocery_id);
CREATE INDEX idx_groceries_vendors_vendor_id ON groceries_vendors(vendor_id);
CREATE INDEX idx_grocery_price_history_grocery_vendor_id ON grocery_price_history(grocery_id, vendor_id);