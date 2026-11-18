ALTER TABLE `order`
    ADD COLUMN recipient_province VARCHAR(100) NULL,
    ADD COLUMN recipient_district VARCHAR(100) NULL,
    ADD COLUMN recipient_ward VARCHAR(100) NULL;

ALTER TABLE shipping_webhook_events
    ADD COLUMN status_code VARCHAR(50) NULL;
