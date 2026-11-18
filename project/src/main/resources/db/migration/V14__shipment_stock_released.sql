ALTER TABLE shipments
    ADD COLUMN stock_released BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE shipments
SET stock_released = TRUE
WHERE status = 'CANCELLED';
