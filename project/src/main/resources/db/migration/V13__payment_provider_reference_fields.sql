ALTER TABLE payments
    ADD COLUMN provider_transaction_reference VARCHAR(100) NULL,
    ADD COLUMN provider_transaction_no VARCHAR(150) NULL;

CREATE INDEX idx_payments_provider_transaction_no
    ON payments(provider_transaction_no);
