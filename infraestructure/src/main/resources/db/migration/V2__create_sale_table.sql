CREATE TABLE sales (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    total_price FLOAT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

CREATE TABLE sale_products(
    product_id CHAR(36) NOT NULL,
    sale_id CHAR(36) NOT NULL,
    CONSTRAINT idx_sale_product UNIQUE(product_id, sale_id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    CONSTRAINT fk_sales_id FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE
);