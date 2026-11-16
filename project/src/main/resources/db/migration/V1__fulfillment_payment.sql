ALTER TABLE `order`
    ADD COLUMN `version` BIGINT NOT NULL DEFAULT 0;

CREATE TABLE `order_status_histories` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `from_status` VARCHAR(40) NULL,
    `to_status` VARCHAR(40) NOT NULL,
    `reason` VARCHAR(255) NULL,
    `changed_by` VARCHAR(255) NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_order_status_history_order` (`order_id`, `created_at`),
    CONSTRAINT `fk_order_status_history_order`
        FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `fulfillments` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `status` VARCHAR(40) NOT NULL,
    `version` BIGINT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_fulfillment_order` (`order_id`),
    CONSTRAINT `fk_fulfillment_order`
        FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shipments` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `fulfillment_id` BIGINT NOT NULL,
    `provider` VARCHAR(30) NOT NULL,
    `tracking_code` VARCHAR(100) NULL,
    `provider_order_code` VARCHAR(100) NULL,
    `status` VARCHAR(40) NOT NULL,
    `last_error` VARCHAR(500) NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_shipment_provider_tracking` (`provider`, `tracking_code`),
    KEY `idx_shipment_fulfillment` (`fulfillment_id`),
    CONSTRAINT `fk_shipment_fulfillment`
        FOREIGN KEY (`fulfillment_id`) REFERENCES `fulfillments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shipping_webhook_events` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `provider` VARCHAR(30) NOT NULL,
    `event_key` VARCHAR(255) NOT NULL,
    `tracking_code` VARCHAR(100) NOT NULL,
    `status_id` INT NOT NULL,
    `payload` JSON NOT NULL,
    `processed` BOOLEAN NOT NULL DEFAULT FALSE,
    `received_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `processed_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_shipping_webhook_event` (`provider`, `event_key`),
    KEY `idx_shipping_webhook_tracking` (`tracking_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `payments` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `transaction_reference` VARCHAR(100) NOT NULL,
    `amount` DECIMAL(19,2) NOT NULL,
    `receiving_account` VARCHAR(100) NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    `expires_at` TIMESTAMP NOT NULL,
    `version` BIGINT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_reference` (`transaction_reference`),
    UNIQUE KEY `uk_payment_order` (`order_id`),
    CONSTRAINT `fk_payment_order`
        FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `payment_transactions` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `payment_id` BIGINT NOT NULL,
    `provider_transaction_id` VARCHAR(150) NOT NULL,
    `amount` DECIMAL(19,2) NOT NULL,
    `receiving_account` VARCHAR(100) NOT NULL,
    `transaction_time` TIMESTAMP NULL,
    `payload` JSON NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_provider_transaction` (`provider_transaction_id`),
    CONSTRAINT `fk_payment_transaction_payment`
        FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `payment_webhook_events` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `provider` VARCHAR(30) NOT NULL,
    `notification_id` VARCHAR(150) NOT NULL,
    `payload` JSON NOT NULL,
    `processed` BOOLEAN NOT NULL DEFAULT FALSE,
    `received_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `processed_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_webhook_notification` (`provider`, `notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
