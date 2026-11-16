ALTER TABLE `payments`
    MODIFY COLUMN `receiving_account` VARCHAR(100) NULL;

ALTER TABLE `payment_transactions`
    MODIFY COLUMN `receiving_account` VARCHAR(100) NULL,
    ADD COLUMN `provider` VARCHAR(30) NOT NULL DEFAULT 'VIETQR' AFTER `payment_id`;

ALTER TABLE `payment_transactions`
    DROP INDEX `uk_payment_provider_transaction`,
    ADD CONSTRAINT `uk_payment_provider_transaction`
        UNIQUE (`provider`, `provider_transaction_id`);
