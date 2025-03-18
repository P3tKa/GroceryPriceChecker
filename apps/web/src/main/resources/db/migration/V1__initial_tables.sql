CREATE table IF NOT EXISTS groceries (
    id varchar PRIMARY KEY,
    name varchar NOT NULL,
    country_of_origin varchar,
    supplier varchar,
    category varchar,
    sub_category varchar,
    sub_sub_category varchar,
    barcodes bigint[],
    quantity DECIMAL(10, 2) NOT NULL CHECK (quantity >= 0),
    unit varchar NOT NULL,
    image_url varchar,
    search_vector tsvector GENERATED ALWAYS AS (to_tsvector('lithuanian', name)) STORED
);

CREATE table IF NOT EXISTS vendors (
    id varchar PRIMARY KEY,
    image_url varchar,
    country_of_origin varchar
);

CREATE table IF NOT EXISTS groceries_vendors (
    id varchar PRIMARY KEY,
    grocery_id varchar REFERENCES groceries(id) ON DELETE CASCADE,
    vendor_id varchar REFERENCES vendors(id) ON DELETE CASCADE,
    grocery_code varchar NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    price_with_discount DECIMAL(10, 2) CHECK (price_with_discount >= 0),
    price_with_loyalty_card DECIMAL(10, 2) CHECK (price_with_loyalty_card >= 0),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    approved boolean NOT NULL DEFAULT FALSE,
    UNIQUE (vendor_id, grocery_code)
);

CREATE TABLE IF NOT EXISTS grocery_price_history (
    id SERIAL PRIMARY KEY,
    grocery_vendor_id varchar REFERENCES groceries_vendors(id) ON DELETE CASCADE,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    price_with_discount DECIMAL(10, 2) CHECK (price_with_discount >= 0),
    price_with_loyalty_card DECIMAL(10, 2) CHECK (price_with_loyalty_card >= 0),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_search_vector ON groceries USING GIN(search_vector);
CREATE INDEX IF NOT EXISTS idx_groceries_vendors_grocery_id ON groceries_vendors(grocery_id);
CREATE INDEX IF NOT EXISTS idx_groceries_vendors_vendor_id ON groceries_vendors(vendor_id);
CREATE INDEX IF NOT EXISTS idx_grocery_price_history_grocery_vendor_id ON grocery_price_history(grocery_vendor_id);