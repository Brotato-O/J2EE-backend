UPDATE `order`
SET order_trangthai = 'NEW'
WHERE UPPER(TRIM(order_trangthai)) IN ('PENDING', 'CHỜ XÁC NHẬN');

UPDATE `order`
SET order_trangthai = 'CONFIRMED'
WHERE UPPER(TRIM(order_trangthai)) IN ('ĐÃ XÁC NHẬN');

UPDATE `order`
SET order_trangthai = 'PACKING'
WHERE UPPER(TRIM(order_trangthai)) IN ('ĐANG ĐÓNG GÓI');

UPDATE `order`
SET order_trangthai = 'SHIPPING'
WHERE UPPER(TRIM(order_trangthai)) IN ('ĐANG GIAO');

UPDATE `order`
SET order_trangthai = 'DELIVERED'
WHERE UPPER(TRIM(order_trangthai)) IN ('ĐÃ GIAO HÀNG');

UPDATE `order`
SET order_trangthai = 'DELIVERY_FAILED'
WHERE UPPER(TRIM(order_trangthai)) IN ('GIAO HÀNG THẤT BẠI');

UPDATE `order`
SET order_trangthai = 'RETURNED'
WHERE UPPER(TRIM(order_trangthai)) IN ('ĐÃ HOÀN HÀNG');

UPDATE `order`
SET order_trangthai = 'CANCELLED'
WHERE UPPER(TRIM(order_trangthai)) IN ('ĐÃ HỦY');

UPDATE order_status_histories
SET from_status = CASE UPPER(TRIM(from_status))
    WHEN 'CHỜ XÁC NHẬN' THEN 'NEW'
    WHEN 'ĐÃ XÁC NHẬN' THEN 'CONFIRMED'
    WHEN 'ĐANG ĐÓNG GÓI' THEN 'PACKING'
    WHEN 'ĐANG GIAO' THEN 'SHIPPING'
    WHEN 'ĐÃ GIAO HÀNG' THEN 'DELIVERED'
    WHEN 'GIAO HÀNG THẤT BẠI' THEN 'DELIVERY_FAILED'
    WHEN 'ĐÃ HOÀN HÀNG' THEN 'RETURNED'
    WHEN 'ĐÃ HỦY' THEN 'CANCELLED'
    ELSE from_status
END,
to_status = CASE UPPER(TRIM(to_status))
    WHEN 'CHỜ XÁC NHẬN' THEN 'NEW'
    WHEN 'ĐÃ XÁC NHẬN' THEN 'CONFIRMED'
    WHEN 'ĐANG ĐÓNG GÓI' THEN 'PACKING'
    WHEN 'ĐANG GIAO' THEN 'SHIPPING'
    WHEN 'ĐÃ GIAO HÀNG' THEN 'DELIVERED'
    WHEN 'GIAO HÀNG THẤT BẠI' THEN 'DELIVERY_FAILED'
    WHEN 'ĐÃ HOÀN HÀNG' THEN 'RETURNED'
    WHEN 'ĐÃ HỦY' THEN 'CANCELLED'
    ELSE to_status
END;
