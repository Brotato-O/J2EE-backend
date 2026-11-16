ALTER TABLE `cart`
    ADD CONSTRAINT `uk_cart_customer` UNIQUE (`kh_id`);
