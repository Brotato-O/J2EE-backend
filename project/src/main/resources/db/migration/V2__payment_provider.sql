ALTER TABLE `payments`
    ADD COLUMN `provider` VARCHAR(20) NOT NULL DEFAULT 'VIETQR' AFTER `receiving_account`;
