# CẤU TRÚC DATABASE - MODULE SẢN PHẨM

Dưới đây là DDL (Data Definition Language) của 7 bảng liên quan đến quản lý sản phẩm. 
Yêu cầu AI khi tạo Entity JPA phải map chính xác tên bảng, tên cột (bằng `@Table`, `@Column`) và thiết lập các quan hệ `@ManyToOne`, `@OneToMany` dựa trên các khóa ngoại (FOREIGN KEY) trong script này.

```sql
-- 1. Bảng Danh mục
CREATE TABLE `category` (
  `cate_id` int NOT NULL AUTO_INCREMENT,
  `cate_name` varchar(55) NOT NULL,
  `trangthai` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`cate_id`)
);

-- 2. Bảng Thương hiệu
CREATE TABLE `thuong_hieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten_thuong_hieu` varchar(255) NOT NULL,
  `mo_ta` text,
  PRIMARY KEY (`id`)
);

-- 3. Bảng Nhà cung cấp
CREATE TABLE `nhacungcap` (
  `ncc_id` int NOT NULL AUTO_INCREMENT,
  `ncc_name` varchar(255) NOT NULL,
  `ncc_diachi` varchar(255) NOT NULL,
  `ncc_sdt` varchar(20) NOT NULL,
  `ncc_email` varchar(255) NOT NULL,
  `ncc_trangthai` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`ncc_id`)
);

-- 4. Bảng Màu sắc
CREATE TABLE `color` (
  `color_id` int NOT NULL AUTO_INCREMENT,
  `color_name` varchar(50) NOT NULL,
  `color_ma` varchar(50) NOT NULL,
  PRIMARY KEY (`color_id`)
);

-- 5. Bảng Kích thước (Size)
CREATE TABLE `size` (
  `size_id` int NOT NULL AUTO_INCREMENT,
  `size_name` varchar(50) NOT NULL,
  PRIMARY KEY (`size_id`)
);

-- 6. Bảng Sản phẩm (Sản phẩm gốc)
CREATE TABLE `products` (
  `pro_id` int NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(255) NOT NULL,
  `pro_img` varchar(255) NOT NULL,
  `pro_price` float NOT NULL,
  `pro_desc` text NOT NULL,
  `pro_brand` varchar(55) NOT NULL,
  `pro_stock` int NOT NULL DEFAULT '0',
  `cate_id` int NOT NULL,
  `ncc_id` int NOT NULL DEFAULT '1',
  `trangthai` int NOT NULL DEFAULT '0',
  `pro_viewer` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`pro_id`),
  CONSTRAINT `fk_products_category` FOREIGN KEY (`cate_id`) REFERENCES `category` (`cate_id`),
  CONSTRAINT `fk_products_nhacungcap` FOREIGN KEY (`ncc_id`) REFERENCES `nhacungcap` (`ncc_id`)
);

-- 7. Bảng Chi tiết Sản phẩm (Biến thể)
CREATE TABLE `pro_chitiet` (
  `ctiet_pro_id` int NOT NULL AUTO_INCREMENT,
  `pro_id` int NOT NULL,
  `color_id` int NOT NULL,
  `size_id` int NOT NULL,
  `soluong` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`ctiet_pro_id`),
  UNIQUE KEY `uq_pro_color_size` (`pro_id`,`color_id`,`size_id`),
  CONSTRAINT `lk_pro_color` FOREIGN KEY (`color_id`) REFERENCES `color` (`color_id`),
  CONSTRAINT `lk_pro_size` FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`),
  CONSTRAINT `lk_proctiet_pro` FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`)
);