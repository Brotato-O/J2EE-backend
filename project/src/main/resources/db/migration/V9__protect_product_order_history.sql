ALTER TABLE `order_chitiet`
    DROP FOREIGN KEY `lk_orderchitiet_pro`,
    ADD CONSTRAINT `lk_orderchitiet_pro`
        FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `pro_chitiet`
    DROP FOREIGN KEY `lk_proctiet_pro`,
    ADD CONSTRAINT `lk_proctiet_pro`
        FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE;
