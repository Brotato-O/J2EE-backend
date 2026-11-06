-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 06, 2026 at 08:09 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dacn`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cart_id` int(10) NOT NULL COMMENT 'ID giỏ hàng',
  `kh_id` int(10) NOT NULL COMMENT 'ID khách hàng',
  `tongtien` int(11) NOT NULL DEFAULT 0 COMMENT 'Tổng tiền'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cart_id`, `kh_id`, `tongtien`) VALUES
(1, 42, 594),
(2, 51, 600),
(3, 46, 390),
(4, 53, 650),
(5, 47, 600),
(6, 52, 630),
(7, 11, 650),
(8, 49, 1190),
(9, 59, 0);

-- --------------------------------------------------------

--
-- Table structure for table `cart_chitiet`
--

CREATE TABLE `cart_chitiet` (
  `cart_chitiet_id` int(10) NOT NULL COMMENT 'ID chi tiết giỏ hàng',
  `cart_id` int(10) NOT NULL COMMENT 'ID giỏ hàng',
  `ctiet_pro_id` int(10) NOT NULL COMMENT 'ID biến thể sản phẩm',
  `price` decimal(12,2) NOT NULL COMMENT 'Đơn giá tại thời điểm thêm vào giỏ',
  `soluong` int(11) NOT NULL COMMENT 'Số lượng',
  `total_price` decimal(12,2) NOT NULL COMMENT 'Thành tiền',
  `color_id` int(11) NOT NULL,
  `pro_id` int(11) NOT NULL,
  `size_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `cart_chitiet`
--

INSERT INTO `cart_chitiet` (`cart_chitiet_id`, `cart_id`, `ctiet_pro_id`, `price`, `soluong`, `total_price`, `color_id`, `pro_id`, `size_id`) VALUES
(1, 1, 135, 130.00, 2, 260.00, 0, 0, 0),
(2, 1, 98, 100.00, 2, 200.00, 0, 0, 0),
(3, 1, 158, 134.00, 1, 134.00, 0, 0, 0),
(4, 2, 156, 300.00, 2, 600.00, 0, 0, 0),
(5, 3, 27, 50.00, 1, 50.00, 0, 0, 0),
(6, 3, 44, 50.00, 2, 100.00, 0, 0, 0),
(7, 3, 92, 120.00, 2, 240.00, 0, 0, 0),
(8, 4, 95, 120.00, 1, 120.00, 0, 0, 0),
(9, 4, 126, 130.00, 1, 130.00, 0, 0, 0),
(10, 4, 14, 200.00, 2, 400.00, 0, 0, 0),
(11, 5, 37, 150.00, 2, 300.00, 0, 0, 0),
(12, 5, 77, 300.00, 1, 300.00, 0, 0, 0),
(13, 6, 24, 50.00, 2, 100.00, 0, 0, 0),
(14, 6, 19, 200.00, 2, 400.00, 0, 0, 0),
(15, 6, 89, 130.00, 1, 130.00, 0, 0, 0),
(16, 7, 27, 50.00, 1, 50.00, 0, 0, 0),
(17, 7, 10, 300.00, 2, 600.00, 0, 0, 0),
(18, 8, 117, 145.00, 2, 290.00, 0, 0, 0),
(19, 8, 156, 300.00, 1, 300.00, 0, 0, 0),
(20, 8, 82, 300.00, 2, 600.00, 0, 0, 0),
(84, 9, 158, 134.00, 2, 268.00, 3, 41, 1),
(86, 9, 46, 50.00, 1, 50.00, 1, 51, 3);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `cate_id` int(10) NOT NULL,
  `cate_name` varchar(55) NOT NULL,
  `trangthai` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`cate_id`, `cate_name`, `trangthai`) VALUES
(9, 'blazer', 0),
(10, 'hoodies', 0),
(11, 'caps', 0),
(12, 'trouser', 0),
(13, 't-shirt', 0),
(14, 'shorts', 0),
(15, 'sweater', 0);

-- --------------------------------------------------------

--
-- Table structure for table `chi_tiet_nhom_quyen`
--

CREATE TABLE `chi_tiet_nhom_quyen` (
  `role_id` int(10) NOT NULL,
  `permission_id` varchar(10) NOT NULL,
  `hanh_dong` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `chi_tiet_nhom_quyen`
--

INSERT INTO `chi_tiet_nhom_quyen` (`role_id`, `permission_id`, `hanh_dong`) VALUES
(1, 'Q1', NULL),
(1, 'Q10', NULL),
(1, 'Q11', NULL),
(1, 'Q12', NULL),
(1, 'Q13', NULL),
(1, 'Q14', NULL),
(1, 'Q15', NULL),
(1, 'Q2', NULL),
(1, 'Q3', NULL),
(1, 'Q4', NULL),
(1, 'Q5', NULL),
(1, 'Q6', NULL),
(1, 'Q7', NULL),
(1, 'Q8', NULL),
(1, 'Q9', NULL),
(3, 'Q1', NULL),
(3, 'Q12', NULL),
(3, 'Q13', NULL),
(3, 'Q2', NULL),
(3, 'Q3', NULL),
(3, 'Q5', NULL),
(3, 'Q7', NULL),
(3, 'Q9', NULL),
(4, 'Q1', NULL),
(4, 'Q11', NULL),
(4, 'Q3', NULL),
(4, 'Q8', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `color`
--

CREATE TABLE `color` (
  `color_id` int(10) NOT NULL COMMENT 'ID màu',
  `color_name` varchar(50) NOT NULL COMMENT 'Tên màu',
  `color_ma` varchar(50) NOT NULL COMMENT 'Mã màu css'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `color`
--

INSERT INTO `color` (`color_id`, `color_name`, `color_ma`) VALUES
(1, 'Xanh', '#7FFFD4'),
(2, 'Vàng', '#FFD700'),
(3, 'Trắng', '#FFFFFF'),
(4, 'Đen', '#000000'),
(5, 'Đỏ', '#E53935'),
(6, 'Xám', '#9E9E9E'),
(7, 'Be', '#D8CBB3');

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `kh_id` int(11) NOT NULL,
  `kh_name` varchar(255) NOT NULL,
  `kh_pass` varchar(255) NOT NULL,
  `kh_mail` varchar(255) NOT NULL,
  `kh_tel` varchar(255) DEFAULT NULL,
  `kh_address` varchar(255) NOT NULL,
  `vaitro_id` int(11) NOT NULL DEFAULT 2,
  `trangthai` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`kh_id`, `kh_name`, `kh_pass`, `kh_mail`, `kh_tel`, `kh_address`, `vaitro_id`, `trangthai`) VALUES
(11, 'Abc1235', 'Abc@123', 'Abc@gmail.com', '1234567891', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1, 0),
(12, 'Abc1234', 'Abc@1234', 'Abc1234@gmail.com', '1234567891', '9 Trường Chinh, Đống Đa, Hà Nội', 3, 0),
(36, 'admin', 'Phonglb2004@', 'luongbaphong20041@gmail.com', '0398748313', 'Hà Nội', 1, 0),
(39, 'phonglb_admin', 'Phonglb2004@', 'admin@gmail.com', '0398748313', 'Hà Nội', 2, 0),
(41, 'nguyendanhquan', 'Quan123@', 'quan23566888@gmail.com', '0967016683', 'Hà Nội', 1, 0),
(42, 'Danhquan', 'Quan123@', 'trung@gmail.com', '0967016683', 'Hà Nội', 2, 0),
(43, 'Abcc', 'Abc@123', 'onepiecekkzz@gmail.com', '0906840111', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 2, 0),
(44, 'tranminhanh', 'Anh@2024', 'tranminhanh@gmail.com', '0912345678', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 2, 0),
(45, 'lethuhang_updated', 'Hang@2024', 'lethuhang@gmail.com', '0987654321', '100 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 2, 1),
(46, 'phamquochuy', 'Huy@2024', 'phamquochuy@gmail.com', '0978123456', '88 Hùng Vương, Hải Châu, Đà Nẵng', 2, 0),
(47, 'nguyenthimai', 'Mai@2024', 'nguyenthimai@gmail.com', '0961234789', '23 Trần Phú, Ngô Quyền, Hải Phòng', 2, 0),
(48, 'dovanlong', 'Long@2024', 'dovanlong@gmail.com', '0908765432', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 2, 0),
(49, 'buithanhtung', 'Tung@2024', 'buithanhtung@gmail.com', '0945678123', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 2, 0),
(50, 'hoangthuylinh', 'Linh@2024', 'hoangthuylinh@gmail.com', '0923456781', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 2, 0),
(51, 'vukhanhchi', 'Chi@2024', 'vukhanhchi@gmail.com', '0967891234', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 2, 0),
(52, 'dangquocbao', 'Bao@2024', 'dangquocbao@gmail.com', '0932178945', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 2, 0),
(53, 'ngothikimngan', 'Ngan@2024', 'ngothikimngan@gmail.com', '0919283746', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 2, 0),
(54, 'nvbanhang01', 'Nv@2024', 'nvbanhang01@shop.com', '0977001122', 'Cửa hàng số 1, Hà Nội', 3, 0),
(55, 'nvbanhang02', 'Nv@2024', 'nvbanhang02@shop.com', '0977003344', 'Cửa hàng số 2, TP. Hồ Chí Minh', 3, 0),
(56, 'nvkho01', 'Kho@2024', 'nvkho01@shop.com', '0977005566', 'Kho trung tâm, Hà Nội', 4, 0),
(57, 'nvkho02', 'Kho@2024', 'nvkho02@shop.com', '0977007788', 'Kho miền Nam, TP. Hồ Chí Minh', 4, 0),
(58, 'nguyenvana', '123456', 'nguyenvana@gmail.com', '0901234567', '123 Nguyễn Trãi, Quận 1, TP. Hồ Chí Minh', 2, 1),
(59, 'ADMIN', '$2a$10$AooAhDnfwI11XCoZU7efwOap4T2UcKfgpJiFba045iab3LbH1RWWC', 'nguyenvanadmin@gmail.com', '0901234567', 'Dong Nai', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `ncc_id` int(10) NOT NULL COMMENT 'ID nhà cung cấp',
  `ncc_name` varchar(255) NOT NULL COMMENT 'Tên nhà cung cấp',
  `ncc_diachi` varchar(255) NOT NULL COMMENT 'Địa chỉ',
  `ncc_sdt` varchar(20) NOT NULL COMMENT 'Số điện thoại',
  `ncc_email` varchar(255) NOT NULL COMMENT 'Email',
  `ncc_trangthai` int(1) NOT NULL DEFAULT 0 COMMENT '0: hoạt động, 1: ngừng hoạt động'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`ncc_id`, `ncc_name`, `ncc_diachi`, `ncc_sdt`, `ncc_email`, `ncc_trangthai`) VALUES
(1, 'Công ty Thời trang Việt Nam', 'Số 1, Đường Lê Duẩn, Hà Nội', '0241234567', 'contact@thoitrangvn.com', 0),
(2, 'Công ty May mặc Hải Phòng', 'Số 45, Đường Trần Phú, Hải Phòng', '0225123456', 'info@maymachp.com', 0),
(3, 'Công ty Dệt may Đà Nẵng', 'Số 78, Đường Nguyễn Văn Linh, Đà Nẵng', '0236123456', 'contact@detmaydn.com', 0),
(4, 'Công ty Thời trang Sài Gòn', 'Số 123, Đường Nguyễn Huệ, TP.HCM', '0281234567', 'info@thoitrangsg.com', 0),
(5, 'Công ty Vải sợi Bắc Ninh', 'Số 56, Đường Nguyễn Trãi, Bắc Ninh', '0222123456', 'contact@vaisoi.com', 0),
(6, 'Công ty Phụ liệu may mặc', 'Số 89, Đường Lê Lợi, Hải Dương', '0220123456', 'info@phulieu.com', 0),
(7, 'Công ty Thời trang cao cấp', 'Số 34, Đường Tràng Tiền, Hà Nội', '0241234568', 'contact@caocap.com', 0),
(8, 'Công ty Vải vóc nhập khẩu', 'Số 67, Đường Hai Bà Trưng, TP.HCM', '0281234568', 'info@vainhapkhau.com', 0),
(9, 'Công ty Phụ kiện thời trang', 'Số 12, Đường Lý Thường Kiệt, Đà Nẵng', '0236123457', 'contact@phukien.com', 0),
(10, 'Công ty Thời trang trẻ em', 'Số 45, Đường Lê Văn Sỹ, TP.HCM', '0281234569', 'info@treem.com', 0);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(10) NOT NULL COMMENT 'ID hóa đơn',
  `kh_id` int(10) NOT NULL COMMENT 'ID khách hàng',
  `order_date` varchar(20) NOT NULL COMMENT 'Ngày đặt hàng (dd-mm-yy)',
  `order_trangthai` varchar(50) NOT NULL COMMENT 'Trạng thái đơn hàng',
  `order_adress` varchar(250) NOT NULL DEFAULT '' COMMENT 'Địa chỉ giao hàng',
  `order_totalprice` int(11) NOT NULL DEFAULT 0 COMMENT 'Tổng tiền'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `kh_id`, `order_date`, `order_trangthai`, `order_adress`, `order_totalprice`) VALUES
(1, 46, '21-01-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 310),
(2, 44, '15-01-22', 'Đã hủy', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 130),
(3, 51, '14-01-22', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 730),
(4, 45, '25-01-22', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 300),
(5, 12, '12-01-22', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 320),
(6, 46, '26-01-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 500),
(7, 45, '17-02-22', 'Đã hủy', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 100),
(8, 11, '08-02-22', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 260),
(9, 52, '27-02-22', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 260),
(10, 50, '09-02-22', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 180),
(11, 45, '25-02-22', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 1160),
(12, 46, '23-02-22', 'Đã hủy', '88 Hùng Vương, Hải Châu, Đà Nẵng', 260),
(13, 42, '10-03-22', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 510),
(14, 44, '18-03-22', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 960),
(15, 50, '08-03-22', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 300),
(16, 49, '06-03-22', 'Đã hủy', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 50),
(17, 53, '04-03-22', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 420),
(18, 11, '27-04-22', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 770),
(19, 43, '19-04-22', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 560),
(20, 49, '05-04-22', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 500),
(21, 43, '07-04-22', 'Đã hủy', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 200),
(22, 52, '01-04-22', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 100),
(23, 52, '08-04-22', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 650),
(24, 44, '20-05-22', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 50),
(25, 42, '13-05-22', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 1320),
(26, 46, '17-05-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 975),
(27, 47, '28-05-22', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 500),
(28, 12, '26-05-22', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 300),
(29, 50, '03-05-22', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 260),
(30, 52, '16-06-22', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 150),
(31, 51, '17-06-22', 'Đã hủy', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 800),
(32, 47, '11-06-22', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 1130),
(33, 44, '06-06-22', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 400),
(34, 43, '03-06-22', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 730),
(35, 51, '21-07-22', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 600),
(36, 50, '13-07-22', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 700),
(37, 47, '19-07-22', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 410),
(38, 50, '10-07-22', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 795),
(39, 48, '14-07-22', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 50),
(40, 47, '15-07-22', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 230),
(41, 44, '24-08-22', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 150),
(42, 51, '16-08-22', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 100),
(43, 46, '13-08-22', 'Đã hủy', '88 Hùng Vương, Hải Châu, Đà Nẵng', 1840),
(44, 46, '07-08-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 200),
(45, 43, '03-08-22', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 740),
(46, 48, '01-09-22', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 300),
(47, 51, '28-09-22', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 1030),
(48, 46, '22-09-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 750),
(49, 53, '06-09-22', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 300),
(50, 46, '03-09-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 400),
(51, 46, '18-10-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 600),
(52, 53, '04-10-22', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 310),
(53, 41, '18-10-22', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 390),
(54, 11, '10-11-22', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 120),
(55, 11, '26-11-22', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 760),
(56, 43, '10-11-22', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 300),
(57, 47, '09-11-22', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 390),
(58, 46, '10-11-22', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 350),
(59, 45, '28-11-22', 'Đã hủy', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 910),
(60, 49, '10-11-22', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 300),
(61, 47, '20-11-22', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 700),
(62, 52, '14-11-22', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 200),
(63, 50, '14-12-22', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 600),
(64, 45, '16-12-22', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 500),
(65, 48, '27-12-22', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 430),
(66, 11, '20-12-22', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 120),
(67, 41, '01-12-22', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 705),
(68, 43, '02-12-22', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 600),
(69, 52, '22-12-22', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 100),
(70, 45, '06-12-22', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 100),
(71, 45, '03-01-23', 'Đã hủy', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 300),
(72, 49, '28-01-23', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 150),
(73, 48, '02-01-23', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 325),
(74, 46, '09-01-23', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 830),
(75, 46, '21-01-23', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 100),
(76, 12, '25-01-23', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 690),
(77, 45, '11-01-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 50),
(78, 50, '24-01-23', 'Đã hủy', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 1360),
(79, 47, '02-02-23', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 130),
(80, 46, '01-02-23', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 300),
(81, 51, '10-02-23', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 750),
(82, 50, '27-02-23', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 480),
(83, 53, '13-02-23', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 400),
(84, 48, '13-02-23', 'Đã hủy', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 300),
(85, 49, '01-02-23', 'Đã hủy', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 690),
(86, 47, '25-03-23', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 130),
(87, 41, '22-03-23', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 750),
(88, 51, '21-03-23', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 700),
(89, 43, '28-03-23', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 650),
(90, 47, '05-03-23', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 410),
(91, 49, '17-03-23', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 600),
(92, 46, '04-03-23', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 300),
(93, 45, '18-04-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 700),
(94, 49, '17-04-23', 'Đã hủy', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 260),
(95, 46, '08-04-23', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 1020),
(96, 41, '07-04-23', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 100),
(97, 42, '24-05-23', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 400),
(98, 46, '09-05-23', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 300),
(99, 52, '01-05-23', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 600),
(100, 11, '06-05-23', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1230),
(101, 41, '27-06-23', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 150),
(102, 45, '23-06-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 100),
(103, 53, '16-06-23', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 300),
(104, 42, '24-06-23', 'Đã hủy', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 600),
(105, 50, '10-06-23', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 960),
(106, 11, '11-06-23', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1030),
(107, 48, '18-06-23', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 300),
(108, 12, '14-06-23', 'Đã hủy', '9 Trường Chinh, Đống Đa, Hà Nội', 1060),
(109, 44, '09-07-23', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 600),
(110, 51, '17-07-23', 'Đã hủy', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 990),
(111, 50, '20-07-23', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 1800),
(112, 49, '23-07-23', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 1045),
(113, 51, '20-07-23', 'Đã hủy', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 1030),
(114, 41, '15-07-23', 'Đã hủy', '15 Kim Mã, Ba Đình, Hà Nội', 200),
(115, 45, '17-08-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 50),
(116, 45, '16-08-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 330),
(117, 43, '04-08-23', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 170),
(118, 43, '24-08-23', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 100),
(119, 50, '16-08-23', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 610),
(120, 49, '18-09-23', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 150),
(121, 51, '06-09-23', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 130),
(122, 11, '10-09-23', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 480),
(123, 47, '21-09-23', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 370),
(124, 49, '20-09-23', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 750),
(125, 52, '19-09-23', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 450),
(126, 48, '19-10-23', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 560),
(127, 45, '25-10-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 560),
(128, 51, '03-10-23', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 200),
(129, 43, '27-10-23', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 350),
(130, 52, '26-10-23', 'Đã hủy', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 130),
(131, 44, '10-10-23', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 1030),
(132, 41, '10-10-23', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 130),
(133, 50, '19-11-23', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 400),
(134, 50, '07-11-23', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 630),
(135, 41, '21-11-23', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 130),
(136, 44, '06-11-23', 'Đã hủy', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 430),
(137, 48, '25-11-23', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 290),
(138, 45, '23-11-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 100),
(139, 47, '01-11-23', 'Đã hủy', '23 Trần Phú, Ngô Quyền, Hải Phòng', 600),
(140, 48, '02-11-23', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 130),
(141, 51, '03-12-23', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 50),
(142, 45, '28-12-23', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 300),
(143, 47, '01-12-23', 'Đã hủy', '23 Trần Phú, Ngô Quyền, Hải Phòng', 300),
(144, 44, '14-12-23', 'Đã hủy', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 890),
(145, 42, '25-12-23', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 450),
(146, 52, '22-12-23', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 50),
(147, 41, '01-12-23', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 900),
(148, 12, '15-12-23', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 600),
(149, 41, '06-01-24', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 260),
(150, 47, '24-01-24', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 145),
(151, 45, '22-01-24', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 600),
(152, 44, '02-01-24', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 720),
(153, 50, '01-01-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 300),
(154, 45, '12-01-24', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 890),
(155, 42, '08-01-24', 'Đã hủy', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 460),
(156, 50, '09-01-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 700),
(157, 50, '12-01-24', 'Đã hủy', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 500),
(158, 12, '03-01-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 160),
(159, 53, '28-01-24', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 970),
(160, 51, '07-02-24', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 580),
(161, 50, '11-02-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 100),
(162, 50, '08-02-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 100),
(163, 48, '02-02-24', 'Đã hủy', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 960),
(164, 41, '22-02-24', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 430),
(165, 45, '15-02-24', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 730),
(166, 12, '03-02-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 300),
(167, 50, '12-03-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 520),
(168, 43, '17-03-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 130),
(169, 51, '03-03-24', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 100),
(170, 53, '23-03-24', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 1000),
(171, 48, '12-03-24', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 1130),
(172, 46, '16-04-24', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 390),
(173, 46, '17-04-24', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 100),
(174, 47, '15-04-24', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 850),
(175, 50, '05-04-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 150),
(176, 42, '23-04-24', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 480),
(177, 43, '15-04-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 130),
(178, 45, '09-05-24', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 130),
(179, 49, '21-05-24', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 1040),
(180, 41, '28-05-24', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 830),
(181, 12, '24-05-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 760),
(182, 52, '17-05-24', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 640),
(183, 45, '25-06-24', 'Đã hủy', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 900),
(184, 52, '22-06-24', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 895),
(185, 41, '10-06-24', 'Đã hủy', '15 Kim Mã, Ba Đình, Hà Nội', 300),
(186, 46, '14-06-24', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 400),
(187, 46, '03-06-24', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 300),
(188, 43, '27-06-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 460),
(189, 44, '14-06-24', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 120),
(190, 47, '13-06-24', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 430),
(191, 50, '15-06-24', 'Đã hủy', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 160),
(192, 44, '08-07-24', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 810),
(193, 11, '16-07-24', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1080),
(194, 12, '06-07-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 390),
(195, 42, '18-07-24', 'Đã hủy', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 340),
(196, 51, '16-07-24', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 130),
(197, 50, '27-07-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 330),
(198, 12, '27-07-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 730),
(199, 11, '11-07-24', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1090),
(200, 41, '26-07-24', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 345),
(201, 44, '05-08-24', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 555),
(202, 43, '01-08-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 300),
(203, 47, '09-08-24', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 745),
(204, 50, '15-08-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 570),
(205, 50, '20-08-24', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 480),
(206, 11, '02-08-24', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 150),
(207, 49, '10-08-24', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 600),
(208, 11, '23-09-24', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 450),
(209, 11, '19-09-24', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 50),
(210, 12, '23-09-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 275),
(211, 53, '20-09-24', 'Đã hủy', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 430),
(212, 48, '17-09-24', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 130),
(213, 51, '20-09-24', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 650),
(214, 48, '27-10-24', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 735),
(215, 44, '11-10-24', 'Đã hủy', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 100),
(216, 41, '19-10-24', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 435),
(217, 52, '25-10-24', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 160),
(218, 43, '17-10-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 490),
(219, 12, '17-10-24', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 580),
(220, 45, '01-10-24', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 300),
(221, 11, '20-10-24', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1280),
(222, 48, '25-11-24', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 150),
(223, 46, '15-11-24', 'Đã hủy', '88 Hùng Vương, Hải Châu, Đà Nẵng', 330),
(224, 53, '12-11-24', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 400),
(225, 53, '15-11-24', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 580),
(226, 46, '21-11-24', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 840),
(227, 45, '20-11-24', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 350),
(228, 41, '17-11-24', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 300),
(229, 47, '23-11-24', 'Đã hủy', '23 Trần Phú, Ngô Quyền, Hải Phòng', 300),
(230, 43, '11-11-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 210),
(231, 43, '03-12-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 350),
(232, 53, '02-12-24', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 480),
(233, 51, '27-12-24', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 800),
(234, 48, '25-12-24', 'Đã hủy', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 345),
(235, 46, '27-12-24', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 600),
(236, 51, '28-12-24', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 430),
(237, 43, '12-12-24', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 1175),
(238, 42, '27-12-24', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 1260),
(239, 44, '20-01-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 200),
(240, 42, '27-01-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 1135),
(241, 11, '09-01-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 145),
(242, 43, '12-01-25', 'Đã hủy', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 630),
(243, 11, '24-01-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 560),
(244, 45, '12-01-25', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 510),
(245, 44, '01-01-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 360),
(246, 12, '26-01-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 700),
(247, 52, '13-01-25', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 145),
(248, 53, '25-01-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 450),
(249, 49, '23-01-25', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 650),
(250, 44, '18-02-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 450),
(251, 52, '14-02-25', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 1200),
(252, 44, '02-02-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 100),
(253, 51, '09-02-25', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 300),
(254, 45, '22-02-25', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 200),
(255, 43, '01-02-25', 'Đã hủy', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 910),
(256, 46, '05-02-25', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 600),
(257, 42, '05-02-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 150),
(258, 46, '13-02-25', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 960),
(259, 43, '20-03-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 100),
(260, 47, '19-03-25', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 430),
(261, 44, '07-03-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 685),
(262, 53, '18-03-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 50),
(263, 53, '17-03-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 350),
(264, 41, '27-03-25', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 130),
(265, 43, '04-03-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 860),
(266, 11, '06-03-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 1500),
(267, 53, '19-04-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 100),
(268, 43, '06-04-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 650),
(269, 47, '19-04-25', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 600),
(270, 11, '08-04-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 230),
(271, 49, '13-04-25', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 260),
(272, 46, '11-04-25', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 865),
(273, 43, '26-04-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 50),
(274, 43, '24-04-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 400),
(275, 42, '03-04-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 390),
(276, 12, '02-05-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 100),
(277, 53, '12-05-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 50),
(278, 45, '15-05-25', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 670),
(279, 53, '08-05-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 725),
(280, 12, '17-05-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 610),
(281, 53, '07-05-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 500),
(282, 50, '23-05-25', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 530),
(283, 11, '25-05-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 145),
(284, 45, '13-06-25', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 460),
(285, 44, '15-06-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 365),
(286, 53, '09-06-25', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 560),
(287, 44, '22-06-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 450),
(288, 42, '06-06-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 900),
(289, 43, '28-06-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 1215),
(290, 45, '11-06-25', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 1100),
(291, 42, '11-06-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 520),
(292, 42, '04-07-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 700),
(293, 43, '27-07-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 730),
(294, 50, '19-07-25', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 895),
(295, 12, '07-07-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 290),
(296, 52, '12-07-25', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 250),
(297, 43, '03-07-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 300),
(298, 11, '11-07-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 600),
(299, 51, '08-08-25', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 150),
(300, 48, '05-08-25', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 800),
(301, 45, '18-08-25', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 300),
(302, 11, '02-08-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 50),
(303, 47, '14-08-25', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 610),
(304, 48, '11-08-25', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 550),
(305, 12, '10-08-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 50),
(306, 12, '16-08-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 130),
(307, 47, '02-09-25', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 100),
(308, 42, '01-09-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 650),
(309, 52, '04-09-25', 'Đã hủy', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 760),
(310, 51, '05-09-25', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 320),
(311, 12, '22-09-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 400),
(312, 50, '10-09-25', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 350),
(313, 11, '11-09-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 300),
(314, 42, '24-09-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 590),
(315, 11, '22-10-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 50),
(316, 48, '08-10-25', 'Đã hủy', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 720),
(317, 48, '20-10-25', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 230),
(318, 11, '24-10-25', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 400),
(319, 46, '01-10-25', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 1630),
(320, 53, '17-10-25', 'Đã hủy', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 550),
(321, 46, '18-10-25', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 100),
(322, 48, '06-11-25', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 990),
(323, 47, '25-11-25', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 145),
(324, 12, '12-11-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 130),
(325, 52, '14-11-25', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 780),
(326, 41, '20-11-25', 'Đã hủy', '15 Kim Mã, Ba Đình, Hà Nội', 1030),
(327, 41, '17-11-25', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 650),
(328, 44, '18-11-25', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 740),
(329, 42, '27-11-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 400),
(330, 42, '16-11-25', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 1040),
(331, 52, '06-12-25', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 100),
(332, 49, '03-12-25', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 400),
(333, 42, '14-12-25', 'Đã hủy', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 300),
(334, 43, '27-12-25', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 300),
(335, 48, '21-12-25', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 1290),
(336, 12, '17-12-25', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 300),
(337, 49, '15-12-25', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 700),
(338, 50, '26-12-25', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 470),
(339, 48, '24-12-25', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 50),
(340, 52, '23-01-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 200),
(341, 46, '23-01-26', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 180),
(342, 53, '27-01-26', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 160),
(343, 45, '15-01-26', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 650),
(344, 43, '04-01-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 300),
(345, 11, '20-01-26', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 430),
(346, 50, '08-01-26', 'Đã hủy', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 480),
(347, 49, '18-01-26', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 430),
(348, 42, '07-01-26', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 600),
(349, 47, '02-01-26', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 280),
(350, 45, '22-02-26', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 130),
(351, 49, '01-02-26', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 1060),
(352, 49, '13-02-26', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 260),
(353, 43, '01-02-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 700),
(354, 42, '26-02-26', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 600),
(355, 52, '17-02-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 690),
(356, 41, '09-02-26', 'Đã hủy', '15 Kim Mã, Ba Đình, Hà Nội', 250),
(357, 44, '02-02-26', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 680),
(358, 44, '05-02-26', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 260),
(359, 12, '10-02-26', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 50),
(360, 43, '28-03-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 350),
(361, 43, '07-03-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 660),
(362, 52, '25-03-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 560),
(363, 50, '11-03-26', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 1330),
(364, 43, '17-03-26', 'Đã hủy', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 745),
(365, 48, '23-03-26', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 100),
(366, 12, '24-03-26', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 460),
(367, 47, '21-03-26', 'Đã giao hàng', '23 Trần Phú, Ngô Quyền, Hải Phòng', 150),
(368, 53, '19-04-26', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 430),
(369, 52, '18-04-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 1250),
(370, 11, '21-04-26', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 730),
(371, 41, '15-04-26', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 270),
(372, 53, '09-04-26', 'Đã hủy', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 830),
(373, 53, '02-04-26', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 130),
(374, 52, '24-04-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 500),
(375, 11, '16-04-26', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 860),
(376, 45, '18-04-26', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 460),
(377, 43, '26-04-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 1325),
(378, 44, '01-05-26', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 745),
(379, 53, '19-05-26', 'Đã giao hàng', '30 Điện Biên Phủ, Bình Thạnh, TP. Hồ Chí Minh', 290),
(380, 48, '25-05-26', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 845),
(381, 46, '10-05-26', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 420),
(382, 49, '22-05-26', 'Đã giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 130),
(383, 51, '28-05-26', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 440),
(384, 12, '02-05-26', 'Đã giao hàng', '9 Trường Chinh, Đống Đa, Hà Nội', 240),
(385, 44, '27-05-26', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 130),
(386, 45, '12-05-26', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 130),
(387, 48, '07-06-26', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 280),
(388, 11, '04-06-26', 'Đã giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 600),
(389, 52, '25-06-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 820),
(390, 45, '06-06-26', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 700),
(391, 51, '12-06-26', 'Đã giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 390),
(392, 11, '25-06-26', 'Đã hủy', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 300),
(393, 46, '26-06-26', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 300),
(394, 42, '27-06-26', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 180),
(395, 43, '11-06-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 150),
(396, 42, '16-06-26', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 720),
(397, 45, '01-06-26', 'Đã giao hàng', '45 Lê Lợi, Quận 1, TP. Hồ Chí Minh', 300),
(398, 43, '17-07-26', 'Đã giao hàng', '210 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 100),
(399, 48, '17-07-26', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 1100),
(400, 48, '03-07-26', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 300),
(401, 42, '26-07-26', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 940),
(402, 41, '06-07-26', 'Đã hủy', '15 Kim Mã, Ba Đình, Hà Nội', 1200),
(403, 49, '03-07-26', 'Đang giao hàng', '56 Cầu Giấy, Cầu Giấy, Hà Nội', 180),
(404, 44, '28-07-26', 'Đã giao hàng', '12 Nguyễn Trãi, Thanh Xuân, Hà Nội', 480),
(405, 48, '16-07-26', 'Đã giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 130),
(406, 46, '14-08-26', 'Đã giao hàng', '88 Hùng Vương, Hải Châu, Đà Nẵng', 120),
(407, 50, '20-08-26', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 450),
(408, 42, '01-08-26', 'Đã giao hàng', '78 Giải Phóng, Hai Bà Trưng, Hà Nội', 50),
(409, 50, '19-08-26', 'Đã giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 320),
(410, 41, '27-08-26', 'Đang giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 300),
(411, 41, '28-08-26', 'Đang giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 390),
(412, 51, '10-08-26', 'Đã hủy', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 600),
(413, 52, '18-08-26', 'Đã giao hàng', '64 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 390),
(414, 41, '13-08-26', 'Đã giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 100),
(415, 41, '09-09-26', 'Đang giao hàng', '15 Kim Mã, Ba Đình, Hà Nội', 400),
(416, 50, '16-09-26', 'Đang giao hàng', '19 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 560),
(417, 48, '18-09-26', 'Đang giao hàng', '7 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 300),
(418, 51, '07-09-26', 'Đang giao hàng', '101 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', 600),
(419, 11, '16-09-26', 'Đang giao hàng', '125 Cộng Hòa, Tân Bình, TP. Hồ Chí Minh', 580);

-- --------------------------------------------------------

--
-- Table structure for table `order_chitiet`
--

CREATE TABLE `order_chitiet` (
  `order_chitiet_id` int(10) NOT NULL COMMENT 'ID chi tiết hóa đơn',
  `order_id` int(10) NOT NULL COMMENT 'ID hóa đơn',
  `pro_id` int(10) NOT NULL COMMENT 'ID sản phẩm',
  `color_id` int(10) NOT NULL COMMENT 'ID màu',
  `size_id` int(10) NOT NULL COMMENT 'ID size',
  `pro_price` int(11) NOT NULL COMMENT 'Đơn giá',
  `soluong` int(11) NOT NULL COMMENT 'Số lượng',
  `total_price` int(11) NOT NULL COMMENT 'Thành tiền'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `order_chitiet`
--

INSERT INTO `order_chitiet` (`order_chitiet_id`, `order_id`, `pro_id`, `color_id`, `size_id`, `pro_price`, `soluong`, `total_price`) VALUES
(1, 1, 51, 7, 3, 50, 1, 50),
(2, 1, 52, 3, 1, 130, 2, 260),
(3, 2, 63, 2, 3, 130, 1, 130),
(4, 3, 53, 5, 3, 300, 1, 300),
(5, 3, 62, 3, 5, 130, 1, 130),
(6, 3, 43, 7, 3, 300, 1, 300),
(7, 4, 47, 1, 1, 300, 1, 300),
(8, 5, 64, 5, 4, 160, 2, 320),
(9, 6, 44, 2, 2, 200, 1, 200),
(10, 6, 55, 7, 3, 300, 1, 300),
(11, 7, 59, 7, 1, 100, 1, 100),
(12, 8, 62, 1, 3, 130, 2, 260),
(13, 9, 57, 6, 3, 130, 2, 260),
(14, 10, 62, 5, 3, 130, 1, 130),
(15, 10, 54, 1, 3, 50, 1, 50),
(16, 11, 63, 2, 3, 130, 2, 260),
(17, 11, 65, 5, 1, 300, 2, 600),
(18, 11, 47, 5, 3, 300, 1, 300),
(19, 12, 52, 3, 1, 130, 1, 130),
(20, 12, 63, 1, 3, 130, 1, 130),
(21, 13, 41, 5, 4, 130, 2, 260),
(22, 13, 54, 1, 4, 50, 3, 150),
(23, 13, 49, 2, 1, 100, 1, 100),
(24, 14, 49, 2, 1, 100, 1, 100),
(25, 14, 53, 7, 5, 300, 2, 600),
(26, 14, 52, 1, 2, 130, 2, 260),
(27, 15, 65, 5, 1, 300, 1, 300),
(28, 16, 46, 4, 1, 50, 1, 50),
(29, 17, 58, 5, 4, 120, 1, 120),
(30, 17, 53, 5, 1, 300, 1, 300),
(31, 18, 48, 3, 5, 150, 1, 150),
(32, 18, 64, 1, 4, 160, 2, 320),
(33, 18, 55, 7, 1, 300, 1, 300),
(34, 19, 49, 3, 1, 100, 1, 100),
(35, 19, 56, 5, 3, 300, 1, 300),
(36, 19, 64, 1, 4, 160, 1, 160),
(37, 20, 41, 5, 1, 130, 2, 260),
(38, 20, 58, 5, 4, 120, 2, 240),
(39, 21, 44, 3, 2, 200, 1, 200),
(40, 22, 46, 4, 1, 50, 1, 50),
(41, 22, 51, 7, 3, 50, 1, 50),
(42, 23, 54, 4, 4, 50, 1, 50),
(43, 23, 47, 1, 3, 300, 1, 300),
(44, 23, 55, 4, 3, 300, 1, 300),
(45, 24, 46, 4, 1, 50, 1, 50),
(46, 25, 66, 5, 2, 300, 2, 600),
(47, 25, 43, 5, 3, 300, 1, 300),
(48, 25, 58, 5, 4, 120, 1, 120),
(49, 25, 56, 5, 3, 300, 1, 300),
(50, 26, 44, 7, 5, 200, 2, 400),
(51, 26, 57, 2, 5, 130, 1, 130),
(52, 26, 55, 7, 3, 300, 1, 300),
(53, 26, 61, 4, 3, 145, 1, 145),
(54, 27, 47, 1, 1, 300, 1, 300),
(55, 27, 44, 3, 2, 200, 1, 200),
(56, 28, 55, 2, 4, 300, 1, 300),
(57, 29, 41, 2, 4, 130, 2, 260),
(58, 30, 45, 1, 1, 50, 1, 50),
(59, 30, 54, 4, 3, 50, 2, 100),
(60, 31, 43, 5, 4, 300, 2, 600),
(61, 31, 49, 3, 1, 100, 2, 200),
(62, 32, 63, 3, 3, 130, 1, 130),
(63, 32, 59, 7, 3, 100, 1, 100),
(64, 32, 60, 1, 4, 300, 1, 300),
(65, 32, 53, 1, 5, 300, 2, 600),
(66, 33, 49, 2, 1, 100, 1, 100),
(67, 33, 53, 1, 3, 300, 1, 300),
(68, 34, 56, 7, 5, 300, 1, 300),
(69, 34, 43, 7, 3, 300, 1, 300),
(70, 34, 63, 3, 3, 130, 1, 130),
(71, 35, 66, 5, 5, 300, 1, 300),
(72, 35, 48, 2, 3, 150, 2, 300),
(73, 36, 66, 1, 2, 300, 1, 300),
(74, 36, 56, 3, 5, 300, 1, 300),
(75, 36, 45, 1, 1, 50, 2, 100),
(76, 37, 48, 2, 3, 150, 1, 150),
(77, 37, 41, 5, 4, 130, 2, 260),
(78, 38, 54, 4, 3, 50, 1, 50),
(79, 38, 55, 1, 1, 300, 2, 600),
(80, 38, 61, 1, 1, 145, 1, 145),
(81, 39, 51, 7, 3, 50, 1, 50),
(82, 40, 49, 3, 1, 100, 1, 100),
(83, 40, 52, 1, 3, 130, 1, 130),
(84, 41, 48, 6, 3, 150, 1, 150),
(85, 42, 45, 7, 1, 50, 2, 100),
(86, 43, 53, 7, 3, 300, 3, 900),
(87, 43, 55, 2, 4, 300, 2, 600),
(88, 43, 58, 4, 5, 120, 2, 240),
(89, 43, 45, 7, 3, 50, 2, 100),
(90, 44, 59, 7, 1, 100, 1, 100),
(91, 44, 51, 3, 3, 50, 2, 100),
(92, 45, 48, 6, 3, 150, 1, 150),
(93, 45, 61, 4, 1, 145, 2, 290),
(94, 45, 60, 6, 4, 300, 1, 300),
(95, 46, 43, 5, 3, 300, 1, 300),
(96, 47, 53, 5, 1, 300, 1, 300),
(97, 47, 56, 4, 3, 300, 1, 300),
(98, 47, 55, 4, 3, 300, 1, 300),
(99, 47, 41, 5, 1, 130, 1, 130),
(100, 48, 47, 3, 4, 300, 2, 600),
(101, 48, 48, 2, 3, 150, 1, 150),
(102, 49, 55, 7, 3, 300, 1, 300),
(103, 50, 59, 3, 3, 100, 1, 100),
(104, 50, 48, 2, 3, 150, 2, 300),
(105, 51, 55, 2, 1, 300, 2, 600),
(106, 52, 45, 7, 1, 50, 1, 50),
(107, 52, 49, 2, 1, 100, 1, 100),
(108, 52, 64, 1, 4, 160, 1, 160),
(109, 53, 59, 3, 3, 100, 1, 100),
(110, 53, 61, 1, 4, 145, 2, 290),
(111, 54, 58, 6, 4, 120, 1, 120),
(112, 55, 64, 4, 1, 160, 1, 160),
(113, 55, 55, 2, 3, 300, 2, 600),
(114, 56, 48, 6, 3, 150, 2, 300),
(115, 57, 52, 1, 1, 130, 3, 390),
(116, 58, 58, 5, 4, 120, 1, 120),
(117, 58, 62, 1, 2, 130, 1, 130),
(118, 58, 45, 7, 3, 50, 2, 100),
(119, 59, 51, 1, 3, 50, 3, 150),
(120, 59, 52, 1, 2, 130, 2, 260),
(121, 59, 65, 2, 3, 300, 1, 300),
(122, 59, 49, 2, 1, 100, 2, 200),
(123, 60, 60, 2, 5, 300, 1, 300),
(124, 61, 56, 3, 5, 300, 1, 300),
(125, 61, 44, 2, 2, 200, 2, 400),
(126, 62, 49, 3, 1, 100, 2, 200),
(127, 63, 53, 7, 5, 300, 2, 600),
(128, 64, 48, 3, 5, 150, 2, 300),
(129, 64, 45, 1, 3, 50, 1, 50),
(130, 64, 54, 4, 3, 50, 1, 50),
(131, 64, 59, 6, 3, 100, 1, 100),
(132, 65, 55, 2, 4, 300, 1, 300),
(133, 65, 62, 5, 3, 130, 1, 130),
(134, 66, 58, 5, 4, 120, 1, 120),
(135, 67, 52, 1, 1, 130, 1, 130),
(136, 67, 61, 1, 4, 145, 1, 145),
(137, 67, 57, 2, 3, 130, 1, 130),
(138, 67, 55, 2, 4, 300, 1, 300),
(139, 68, 55, 1, 1, 300, 1, 300),
(140, 68, 47, 1, 3, 300, 1, 300),
(141, 69, 54, 1, 4, 50, 2, 100),
(142, 70, 49, 6, 5, 100, 1, 100),
(143, 71, 43, 5, 3, 300, 1, 300),
(144, 72, 48, 3, 5, 150, 1, 150),
(145, 73, 57, 2, 3, 130, 1, 130),
(146, 73, 51, 1, 3, 50, 1, 50),
(147, 73, 61, 4, 4, 145, 1, 145),
(148, 74, 54, 4, 3, 50, 2, 100),
(149, 74, 55, 7, 1, 300, 2, 600),
(150, 74, 63, 2, 4, 130, 1, 130),
(151, 75, 54, 4, 4, 50, 2, 100),
(152, 76, 55, 1, 4, 300, 1, 300),
(153, 76, 57, 2, 3, 130, 3, 390),
(154, 77, 54, 4, 4, 50, 1, 50),
(155, 78, 64, 5, 4, 160, 1, 160),
(156, 78, 60, 1, 2, 300, 1, 300),
(157, 78, 65, 2, 3, 300, 3, 900),
(158, 79, 63, 1, 3, 130, 1, 130),
(159, 80, 43, 5, 4, 300, 1, 300),
(160, 81, 44, 2, 3, 200, 3, 600),
(161, 81, 51, 3, 3, 50, 3, 150),
(162, 82, 64, 1, 4, 160, 3, 480),
(163, 83, 44, 3, 3, 200, 2, 400),
(164, 84, 47, 5, 4, 300, 1, 300),
(165, 85, 60, 3, 5, 300, 1, 300),
(166, 85, 58, 5, 5, 120, 2, 240),
(167, 85, 48, 3, 5, 150, 1, 150),
(168, 86, 41, 5, 1, 130, 1, 130),
(169, 87, 45, 1, 3, 50, 1, 50),
(170, 87, 49, 2, 1, 100, 3, 300),
(171, 87, 48, 2, 3, 150, 2, 300),
(172, 87, 51, 7, 3, 50, 2, 100),
(173, 88, 49, 2, 5, 100, 1, 100),
(174, 88, 56, 5, 5, 300, 2, 600),
(175, 89, 51, 3, 3, 50, 1, 50),
(176, 89, 43, 7, 3, 300, 1, 300),
(177, 89, 56, 4, 1, 300, 1, 300),
(178, 90, 54, 1, 3, 50, 3, 150),
(179, 90, 41, 2, 5, 130, 2, 260),
(180, 91, 53, 1, 3, 300, 2, 600),
(181, 92, 55, 1, 1, 300, 1, 300),
(182, 93, 56, 7, 3, 300, 1, 300),
(183, 93, 44, 3, 2, 200, 2, 400),
(184, 94, 62, 5, 2, 130, 2, 260),
(185, 95, 66, 4, 2, 300, 1, 300),
(186, 95, 52, 1, 2, 130, 1, 130),
(187, 95, 59, 7, 3, 100, 2, 200),
(188, 95, 41, 5, 4, 130, 3, 390),
(189, 96, 59, 6, 3, 100, 1, 100),
(190, 97, 44, 3, 3, 200, 2, 400),
(191, 98, 56, 5, 3, 300, 1, 300),
(192, 99, 56, 3, 3, 300, 2, 600),
(193, 100, 49, 3, 1, 100, 2, 200),
(194, 100, 62, 7, 3, 130, 1, 130),
(195, 100, 55, 4, 3, 300, 3, 900),
(196, 101, 54, 4, 4, 50, 1, 50),
(197, 101, 49, 3, 1, 100, 1, 100),
(198, 102, 45, 1, 3, 50, 2, 100),
(199, 103, 47, 3, 1, 300, 1, 300),
(200, 104, 56, 4, 5, 300, 2, 600),
(201, 105, 54, 1, 3, 50, 1, 50),
(202, 105, 47, 1, 1, 300, 2, 600),
(203, 105, 45, 7, 1, 50, 1, 50),
(204, 105, 62, 5, 5, 130, 2, 260),
(205, 106, 56, 7, 5, 300, 3, 900),
(206, 106, 41, 2, 5, 130, 1, 130),
(207, 107, 53, 1, 5, 300, 1, 300),
(208, 108, 64, 5, 4, 160, 1, 160),
(209, 108, 43, 5, 4, 300, 3, 900),
(210, 109, 46, 5, 1, 50, 1, 50),
(211, 109, 47, 3, 4, 300, 1, 300),
(212, 109, 51, 3, 3, 50, 1, 50),
(213, 109, 44, 3, 3, 200, 1, 200),
(214, 110, 57, 6, 3, 130, 3, 390),
(215, 110, 53, 7, 5, 300, 1, 300),
(216, 110, 66, 5, 2, 300, 1, 300),
(217, 111, 53, 7, 5, 300, 3, 900),
(218, 111, 55, 1, 4, 300, 3, 900),
(219, 112, 60, 6, 5, 300, 2, 600),
(220, 112, 66, 5, 2, 300, 1, 300),
(221, 112, 61, 1, 3, 145, 1, 145),
(222, 113, 53, 7, 5, 300, 2, 600),
(223, 113, 55, 2, 1, 300, 1, 300),
(224, 113, 41, 6, 4, 130, 1, 130),
(225, 114, 54, 4, 4, 50, 1, 50),
(226, 114, 48, 6, 3, 150, 1, 150),
(227, 115, 54, 4, 4, 50, 1, 50),
(228, 116, 57, 2, 3, 130, 1, 130),
(229, 116, 59, 7, 1, 100, 2, 200),
(230, 117, 45, 1, 3, 50, 1, 50),
(231, 117, 58, 6, 4, 120, 1, 120),
(232, 118, 49, 2, 5, 100, 1, 100),
(233, 119, 55, 7, 3, 300, 1, 300),
(234, 119, 45, 1, 1, 50, 1, 50),
(235, 119, 52, 3, 1, 130, 2, 260),
(236, 120, 48, 3, 5, 150, 1, 150),
(237, 121, 63, 2, 3, 130, 1, 130),
(238, 122, 52, 3, 2, 130, 1, 130),
(239, 122, 48, 2, 3, 150, 1, 150),
(240, 122, 44, 7, 5, 200, 1, 200),
(241, 123, 62, 1, 5, 130, 1, 130),
(242, 123, 58, 4, 5, 120, 2, 240),
(243, 124, 47, 5, 4, 300, 1, 300),
(244, 124, 59, 6, 3, 100, 2, 200),
(245, 124, 44, 2, 2, 200, 1, 200),
(246, 124, 54, 4, 4, 50, 1, 50),
(247, 125, 46, 5, 2, 50, 3, 150),
(248, 125, 65, 2, 3, 300, 1, 300),
(249, 126, 64, 4, 1, 160, 1, 160),
(250, 126, 44, 7, 3, 200, 2, 400),
(251, 127, 53, 7, 1, 300, 1, 300),
(252, 127, 52, 1, 3, 130, 2, 260),
(253, 128, 54, 1, 4, 50, 1, 50),
(254, 128, 48, 3, 5, 150, 1, 150),
(255, 129, 55, 1, 1, 300, 1, 300),
(256, 129, 51, 3, 3, 50, 1, 50),
(257, 130, 63, 1, 4, 130, 1, 130),
(258, 131, 62, 5, 5, 130, 1, 130),
(259, 131, 53, 7, 3, 300, 1, 300),
(260, 131, 56, 7, 1, 300, 2, 600),
(261, 132, 57, 6, 3, 130, 1, 130),
(262, 133, 59, 3, 3, 100, 1, 100),
(263, 133, 66, 1, 1, 300, 1, 300),
(264, 134, 49, 3, 1, 100, 1, 100),
(265, 134, 61, 1, 1, 145, 2, 290),
(266, 134, 58, 1, 4, 120, 2, 240),
(267, 135, 57, 6, 5, 130, 1, 130),
(268, 136, 47, 3, 1, 300, 1, 300),
(269, 136, 52, 4, 3, 130, 1, 130),
(270, 137, 58, 1, 4, 120, 2, 240),
(271, 137, 51, 7, 3, 50, 1, 50),
(272, 138, 45, 1, 1, 50, 2, 100),
(273, 139, 47, 5, 1, 300, 1, 300),
(274, 139, 66, 4, 1, 300, 1, 300),
(275, 140, 57, 2, 3, 130, 1, 130),
(276, 141, 45, 7, 1, 50, 1, 50),
(277, 142, 60, 1, 4, 300, 1, 300),
(278, 143, 51, 1, 3, 50, 2, 100),
(279, 143, 59, 6, 3, 100, 2, 200),
(280, 144, 61, 5, 1, 145, 2, 290),
(281, 144, 55, 2, 4, 300, 1, 300),
(282, 144, 43, 5, 4, 300, 1, 300),
(283, 145, 46, 4, 5, 50, 1, 50),
(284, 145, 54, 4, 3, 50, 2, 100),
(285, 145, 56, 5, 1, 300, 1, 300),
(286, 146, 45, 1, 1, 50, 1, 50),
(287, 147, 44, 7, 5, 200, 3, 600),
(288, 147, 60, 1, 4, 300, 1, 300),
(289, 148, 66, 5, 1, 300, 2, 600),
(290, 149, 64, 1, 4, 160, 1, 160),
(291, 149, 59, 7, 3, 100, 1, 100),
(292, 150, 61, 4, 4, 145, 1, 145),
(293, 151, 60, 1, 5, 300, 2, 600),
(294, 152, 65, 5, 1, 300, 2, 600),
(295, 152, 58, 5, 4, 120, 1, 120),
(296, 153, 47, 3, 4, 300, 1, 300),
(297, 154, 47, 3, 4, 300, 1, 300),
(298, 154, 63, 1, 4, 130, 1, 130),
(299, 154, 64, 7, 4, 160, 1, 160),
(300, 154, 66, 4, 1, 300, 1, 300),
(301, 155, 64, 4, 4, 160, 1, 160),
(302, 155, 60, 6, 2, 300, 1, 300),
(303, 156, 47, 1, 1, 300, 2, 600),
(304, 156, 51, 3, 3, 50, 2, 100),
(305, 157, 59, 6, 3, 100, 2, 200),
(306, 157, 56, 5, 3, 300, 1, 300),
(307, 158, 64, 4, 1, 160, 1, 160),
(308, 159, 64, 4, 4, 160, 3, 480),
(309, 159, 58, 6, 4, 120, 3, 360),
(310, 159, 52, 1, 3, 130, 1, 130),
(311, 160, 57, 6, 5, 130, 1, 130),
(312, 160, 51, 1, 3, 50, 3, 150),
(313, 160, 48, 3, 5, 150, 2, 300),
(314, 161, 59, 6, 3, 100, 1, 100),
(315, 162, 49, 3, 1, 100, 1, 100),
(316, 163, 60, 6, 5, 300, 2, 600),
(317, 163, 64, 7, 4, 160, 1, 160),
(318, 163, 44, 2, 3, 200, 1, 200),
(319, 164, 43, 5, 3, 300, 1, 300),
(320, 164, 41, 6, 5, 130, 1, 130),
(321, 165, 62, 3, 3, 130, 1, 130),
(322, 165, 46, 5, 2, 50, 3, 150),
(323, 165, 60, 1, 2, 300, 1, 300),
(324, 165, 54, 4, 4, 50, 3, 150),
(325, 166, 44, 2, 3, 200, 1, 200),
(326, 166, 59, 7, 1, 100, 1, 100),
(327, 167, 63, 3, 3, 130, 2, 260),
(328, 167, 41, 2, 5, 130, 2, 260),
(329, 168, 41, 5, 5, 130, 1, 130),
(330, 169, 59, 5, 1, 100, 1, 100),
(331, 170, 43, 7, 3, 300, 1, 300),
(332, 170, 46, 4, 5, 50, 2, 100),
(333, 170, 65, 7, 3, 300, 1, 300),
(334, 170, 47, 5, 1, 300, 1, 300),
(335, 171, 41, 5, 4, 130, 1, 130),
(336, 171, 65, 2, 1, 300, 2, 600),
(337, 171, 44, 2, 3, 200, 2, 400),
(338, 172, 62, 1, 2, 130, 1, 130),
(339, 172, 52, 1, 2, 130, 2, 260),
(340, 173, 46, 5, 2, 50, 2, 100),
(341, 174, 55, 1, 3, 300, 2, 600),
(342, 174, 48, 3, 5, 150, 1, 150),
(343, 174, 46, 4, 5, 50, 2, 100),
(344, 175, 48, 2, 3, 150, 1, 150),
(345, 176, 57, 2, 3, 130, 1, 130),
(346, 176, 60, 6, 5, 300, 1, 300),
(347, 176, 54, 4, 4, 50, 1, 50),
(348, 177, 62, 3, 3, 130, 1, 130),
(349, 178, 63, 2, 3, 130, 1, 130),
(350, 179, 64, 1, 4, 160, 3, 480),
(351, 179, 62, 5, 5, 130, 2, 260),
(352, 179, 55, 7, 3, 300, 1, 300),
(353, 180, 45, 1, 3, 50, 1, 50),
(354, 180, 60, 2, 4, 300, 2, 600),
(355, 180, 46, 4, 5, 50, 1, 50),
(356, 180, 52, 1, 1, 130, 1, 130),
(357, 181, 41, 5, 5, 130, 2, 260),
(358, 181, 43, 5, 3, 300, 1, 300),
(359, 181, 59, 7, 1, 100, 2, 200),
(360, 182, 56, 3, 3, 300, 1, 300),
(361, 182, 45, 7, 1, 50, 1, 50),
(362, 182, 61, 4, 3, 145, 2, 290),
(363, 183, 55, 2, 1, 300, 1, 300),
(364, 183, 53, 7, 1, 300, 1, 300),
(365, 183, 47, 1, 1, 300, 1, 300),
(366, 184, 61, 4, 1, 145, 1, 145),
(367, 184, 48, 3, 5, 150, 1, 150),
(368, 184, 60, 2, 5, 300, 2, 600),
(369, 185, 56, 5, 5, 300, 1, 300),
(370, 186, 53, 7, 5, 300, 1, 300),
(371, 186, 59, 6, 3, 100, 1, 100),
(372, 187, 53, 5, 3, 300, 1, 300),
(373, 188, 64, 5, 1, 160, 1, 160),
(374, 188, 65, 5, 1, 300, 1, 300),
(375, 189, 58, 5, 5, 120, 1, 120),
(376, 190, 41, 2, 5, 130, 1, 130),
(377, 190, 43, 7, 3, 300, 1, 300),
(378, 191, 64, 4, 4, 160, 1, 160),
(379, 192, 53, 7, 5, 300, 1, 300),
(380, 192, 64, 4, 1, 160, 1, 160),
(381, 192, 46, 4, 1, 50, 1, 50),
(382, 192, 60, 3, 5, 300, 1, 300),
(383, 193, 65, 2, 3, 300, 1, 300),
(384, 193, 54, 4, 3, 50, 1, 50),
(385, 193, 47, 5, 4, 300, 2, 600),
(386, 193, 57, 2, 5, 130, 1, 130),
(387, 194, 41, 5, 5, 130, 1, 130),
(388, 194, 57, 6, 5, 130, 2, 260),
(389, 195, 61, 5, 4, 145, 2, 290),
(390, 195, 54, 1, 4, 50, 1, 50),
(391, 196, 63, 3, 4, 130, 1, 130),
(392, 197, 64, 1, 4, 160, 1, 160),
(393, 197, 54, 4, 3, 50, 1, 50),
(394, 197, 58, 5, 4, 120, 1, 120),
(395, 198, 63, 2, 3, 130, 1, 130),
(396, 198, 65, 7, 3, 300, 2, 600),
(397, 199, 57, 7, 3, 130, 3, 390),
(398, 199, 56, 4, 3, 300, 2, 600),
(399, 199, 49, 3, 1, 100, 1, 100),
(400, 200, 59, 6, 3, 100, 1, 100),
(401, 200, 45, 1, 1, 50, 2, 100),
(402, 200, 61, 4, 3, 145, 1, 145),
(403, 201, 61, 5, 1, 145, 1, 145),
(404, 201, 48, 2, 3, 150, 1, 150),
(405, 201, 62, 3, 2, 130, 2, 260),
(406, 202, 47, 3, 1, 300, 1, 300),
(407, 203, 60, 2, 5, 300, 1, 300),
(408, 203, 61, 5, 1, 145, 1, 145),
(409, 203, 43, 5, 3, 300, 1, 300),
(410, 204, 61, 4, 4, 145, 2, 290),
(411, 204, 52, 4, 3, 130, 1, 130),
(412, 204, 48, 2, 3, 150, 1, 150),
(413, 205, 63, 1, 4, 130, 1, 130),
(414, 205, 46, 5, 1, 50, 1, 50),
(415, 205, 56, 7, 1, 300, 1, 300),
(416, 206, 48, 2, 3, 150, 1, 150),
(417, 207, 49, 2, 1, 100, 3, 300),
(418, 207, 43, 5, 4, 300, 1, 300),
(419, 208, 44, 7, 3, 200, 2, 400),
(420, 208, 51, 1, 3, 50, 1, 50),
(421, 209, 45, 7, 3, 50, 1, 50),
(422, 210, 62, 1, 3, 130, 1, 130),
(423, 210, 61, 4, 4, 145, 1, 145),
(424, 211, 56, 3, 3, 300, 1, 300),
(425, 211, 41, 3, 5, 130, 1, 130),
(426, 212, 52, 1, 1, 130, 1, 130),
(427, 213, 46, 4, 1, 50, 1, 50),
(428, 213, 60, 6, 4, 300, 2, 600),
(429, 214, 64, 1, 1, 160, 1, 160),
(430, 214, 52, 3, 2, 130, 1, 130),
(431, 214, 53, 7, 5, 300, 1, 300),
(432, 214, 61, 4, 3, 145, 1, 145),
(433, 215, 59, 5, 1, 100, 1, 100),
(434, 216, 61, 4, 1, 145, 3, 435),
(435, 217, 64, 1, 4, 160, 1, 160),
(436, 218, 41, 5, 4, 130, 1, 130),
(437, 218, 54, 1, 3, 50, 1, 50),
(438, 218, 45, 1, 3, 50, 1, 50),
(439, 218, 62, 5, 3, 130, 2, 260),
(440, 219, 51, 1, 3, 50, 1, 50),
(441, 219, 49, 6, 5, 100, 1, 100),
(442, 219, 62, 3, 5, 130, 1, 130),
(443, 219, 53, 7, 5, 300, 1, 300),
(444, 220, 60, 1, 5, 300, 1, 300),
(445, 221, 52, 3, 1, 130, 3, 390),
(446, 221, 65, 2, 1, 300, 1, 300),
(447, 221, 55, 1, 4, 300, 1, 300),
(448, 221, 61, 4, 4, 145, 2, 290),
(449, 222, 54, 1, 4, 50, 1, 50),
(450, 222, 45, 1, 1, 50, 2, 100),
(451, 223, 48, 6, 3, 150, 1, 150),
(452, 223, 41, 2, 4, 130, 1, 130),
(453, 223, 54, 1, 4, 50, 1, 50),
(454, 224, 51, 1, 3, 50, 2, 100),
(455, 224, 43, 5, 3, 300, 1, 300),
(456, 225, 64, 4, 1, 160, 2, 320),
(457, 225, 62, 1, 2, 130, 2, 260),
(458, 226, 48, 6, 3, 150, 1, 150),
(459, 226, 52, 3, 1, 130, 3, 390),
(460, 226, 66, 1, 5, 300, 1, 300),
(461, 227, 60, 1, 4, 300, 1, 300),
(462, 227, 46, 4, 5, 50, 1, 50),
(463, 228, 56, 4, 5, 300, 1, 300),
(464, 229, 60, 1, 4, 300, 1, 300),
(465, 230, 46, 4, 1, 50, 1, 50),
(466, 230, 64, 1, 4, 160, 1, 160),
(467, 231, 47, 3, 3, 300, 1, 300),
(468, 231, 54, 4, 4, 50, 1, 50),
(469, 232, 64, 1, 1, 160, 3, 480),
(470, 233, 56, 5, 5, 300, 1, 300),
(471, 233, 60, 6, 2, 300, 1, 300),
(472, 233, 44, 2, 3, 200, 1, 200),
(473, 234, 61, 5, 4, 145, 1, 145),
(474, 234, 49, 2, 1, 100, 2, 200),
(475, 235, 65, 7, 1, 300, 1, 300),
(476, 235, 44, 7, 5, 200, 1, 200),
(477, 235, 59, 7, 1, 100, 1, 100),
(478, 236, 41, 2, 5, 130, 1, 130),
(479, 236, 43, 5, 4, 300, 1, 300),
(480, 237, 65, 5, 3, 300, 3, 900),
(481, 237, 62, 7, 3, 130, 1, 130),
(482, 237, 61, 1, 4, 145, 1, 145),
(483, 238, 55, 2, 4, 300, 1, 300),
(484, 238, 58, 4, 5, 120, 3, 360),
(485, 238, 43, 5, 4, 300, 2, 600),
(486, 239, 59, 7, 1, 100, 2, 200),
(487, 240, 61, 1, 4, 145, 1, 145),
(488, 240, 56, 5, 3, 300, 1, 300),
(489, 240, 41, 2, 4, 130, 3, 390),
(490, 240, 65, 7, 1, 300, 1, 300),
(491, 241, 61, 1, 3, 145, 1, 145),
(492, 242, 44, 3, 2, 200, 1, 200),
(493, 242, 66, 4, 2, 300, 1, 300),
(494, 242, 62, 5, 5, 130, 1, 130),
(495, 243, 41, 2, 4, 130, 2, 260),
(496, 243, 60, 3, 5, 300, 1, 300),
(497, 244, 49, 2, 1, 100, 1, 100),
(498, 244, 48, 6, 3, 150, 1, 150),
(499, 244, 62, 3, 5, 130, 2, 260),
(500, 245, 58, 6, 4, 120, 3, 360),
(501, 246, 48, 2, 3, 150, 1, 150),
(502, 246, 64, 1, 4, 160, 1, 160),
(503, 246, 63, 3, 4, 130, 3, 390),
(504, 247, 61, 4, 1, 145, 1, 145),
(505, 248, 59, 3, 3, 100, 3, 300),
(506, 248, 48, 6, 3, 150, 1, 150),
(507, 249, 51, 3, 3, 50, 1, 50),
(508, 249, 65, 7, 1, 300, 1, 300),
(509, 249, 53, 1, 3, 300, 1, 300),
(510, 250, 62, 1, 5, 130, 1, 130),
(511, 250, 64, 1, 1, 160, 2, 320),
(512, 251, 55, 7, 1, 300, 1, 300),
(513, 251, 65, 5, 3, 300, 3, 900),
(514, 252, 59, 3, 3, 100, 1, 100),
(515, 253, 56, 4, 5, 300, 1, 300),
(516, 254, 51, 3, 3, 50, 3, 150),
(517, 254, 46, 5, 2, 50, 1, 50),
(518, 255, 56, 4, 5, 300, 2, 600),
(519, 255, 51, 1, 3, 50, 1, 50),
(520, 255, 63, 2, 3, 130, 2, 260),
(521, 256, 56, 3, 3, 300, 2, 600),
(522, 257, 54, 1, 4, 50, 1, 50),
(523, 257, 49, 3, 1, 100, 1, 100),
(524, 258, 58, 5, 5, 120, 3, 360),
(525, 258, 55, 2, 1, 300, 1, 300),
(526, 258, 49, 3, 1, 100, 3, 300),
(527, 259, 59, 6, 3, 100, 1, 100),
(528, 260, 41, 3, 1, 130, 1, 130),
(529, 260, 56, 3, 3, 300, 1, 300),
(530, 261, 48, 2, 3, 150, 1, 150),
(531, 261, 52, 3, 1, 130, 3, 390),
(532, 261, 61, 1, 4, 145, 1, 145),
(533, 262, 54, 4, 4, 50, 1, 50),
(534, 263, 49, 3, 1, 100, 1, 100),
(535, 263, 45, 7, 3, 50, 1, 50),
(536, 263, 44, 7, 3, 200, 1, 200),
(537, 264, 41, 3, 5, 130, 1, 130),
(538, 265, 53, 1, 5, 300, 1, 300),
(539, 265, 44, 3, 3, 200, 2, 400),
(540, 265, 64, 4, 4, 160, 1, 160),
(541, 266, 55, 2, 4, 300, 2, 600),
(542, 266, 48, 6, 3, 150, 2, 300),
(543, 266, 60, 2, 4, 300, 2, 600),
(544, 267, 59, 5, 1, 100, 1, 100),
(545, 268, 60, 2, 5, 300, 1, 300),
(546, 268, 58, 1, 4, 120, 1, 120),
(547, 268, 46, 4, 1, 50, 2, 100),
(548, 268, 62, 1, 5, 130, 1, 130),
(549, 269, 56, 7, 1, 300, 1, 300),
(550, 269, 47, 5, 4, 300, 1, 300),
(551, 270, 59, 7, 1, 100, 1, 100),
(552, 270, 63, 2, 4, 130, 1, 130),
(553, 271, 63, 1, 4, 130, 1, 130),
(554, 271, 41, 3, 5, 130, 1, 130),
(555, 272, 41, 5, 5, 130, 1, 130),
(556, 272, 61, 5, 4, 145, 3, 435),
(557, 272, 56, 7, 3, 300, 1, 300),
(558, 273, 45, 1, 1, 50, 1, 50),
(559, 274, 49, 2, 1, 100, 2, 200),
(560, 274, 44, 3, 3, 200, 1, 200),
(561, 275, 45, 7, 1, 50, 2, 100),
(562, 275, 61, 1, 3, 145, 2, 290),
(563, 276, 45, 1, 1, 50, 2, 100),
(564, 277, 54, 4, 4, 50, 1, 50),
(565, 278, 65, 2, 3, 300, 1, 300),
(566, 278, 41, 2, 5, 130, 1, 130),
(567, 278, 58, 1, 4, 120, 2, 240),
(568, 279, 48, 6, 3, 150, 3, 450),
(569, 279, 61, 1, 4, 145, 1, 145),
(570, 279, 62, 3, 2, 130, 1, 130),
(571, 280, 57, 2, 3, 130, 2, 260),
(572, 280, 60, 1, 5, 300, 1, 300),
(573, 280, 51, 7, 3, 50, 1, 50),
(574, 281, 49, 6, 5, 100, 2, 200),
(575, 281, 47, 5, 4, 300, 1, 300),
(576, 282, 59, 7, 1, 100, 1, 100),
(577, 282, 43, 5, 3, 300, 1, 300),
(578, 282, 63, 3, 3, 130, 1, 130),
(579, 283, 61, 4, 4, 145, 1, 145),
(580, 284, 64, 5, 4, 160, 1, 160),
(581, 284, 56, 4, 5, 300, 1, 300),
(582, 285, 49, 2, 5, 100, 1, 100),
(583, 285, 58, 1, 4, 120, 1, 120),
(584, 285, 61, 4, 4, 145, 1, 145),
(585, 286, 57, 6, 5, 130, 1, 130),
(586, 286, 65, 7, 1, 300, 1, 300),
(587, 286, 62, 3, 3, 130, 1, 130),
(588, 287, 60, 2, 4, 300, 1, 300),
(589, 287, 54, 4, 4, 50, 3, 150),
(590, 288, 53, 4, 1, 300, 2, 600),
(591, 288, 66, 5, 5, 300, 1, 300),
(592, 289, 52, 1, 2, 130, 2, 260),
(593, 289, 63, 2, 4, 130, 1, 130),
(594, 289, 61, 1, 1, 145, 3, 435),
(595, 289, 62, 5, 3, 130, 3, 390),
(596, 290, 54, 4, 3, 50, 1, 50),
(597, 290, 55, 4, 3, 300, 2, 600),
(598, 290, 48, 2, 3, 150, 1, 150),
(599, 290, 43, 5, 4, 300, 1, 300),
(600, 291, 62, 1, 2, 130, 2, 260),
(601, 291, 41, 5, 5, 130, 2, 260),
(602, 292, 43, 5, 3, 300, 2, 600),
(603, 292, 59, 3, 3, 100, 1, 100),
(604, 293, 63, 1, 4, 130, 1, 130),
(605, 293, 56, 4, 5, 300, 1, 300),
(606, 293, 43, 5, 4, 300, 1, 300),
(607, 294, 43, 7, 3, 300, 2, 600),
(608, 294, 45, 7, 1, 50, 2, 100),
(609, 294, 61, 4, 3, 145, 1, 145),
(610, 294, 51, 3, 3, 50, 1, 50),
(611, 295, 61, 1, 1, 145, 2, 290),
(612, 296, 51, 1, 3, 50, 1, 50),
(613, 296, 49, 6, 5, 100, 2, 200),
(614, 297, 43, 7, 3, 300, 1, 300),
(615, 298, 55, 2, 3, 300, 2, 600),
(616, 299, 48, 3, 5, 150, 1, 150),
(617, 300, 49, 6, 5, 100, 2, 200),
(618, 300, 59, 6, 3, 100, 3, 300),
(619, 300, 66, 5, 2, 300, 1, 300),
(620, 301, 43, 5, 3, 300, 1, 300),
(621, 302, 45, 7, 1, 50, 1, 50),
(622, 303, 62, 3, 2, 130, 2, 260),
(623, 303, 46, 5, 2, 50, 1, 50),
(624, 303, 43, 5, 4, 300, 1, 300),
(625, 304, 59, 7, 1, 100, 2, 200),
(626, 304, 47, 3, 3, 300, 1, 300),
(627, 304, 51, 7, 3, 50, 1, 50),
(628, 305, 45, 1, 3, 50, 1, 50),
(629, 306, 63, 1, 3, 130, 1, 130),
(630, 307, 49, 3, 1, 100, 1, 100),
(631, 308, 54, 1, 3, 50, 1, 50),
(632, 308, 53, 5, 3, 300, 1, 300),
(633, 308, 60, 6, 5, 300, 1, 300),
(634, 309, 64, 1, 1, 160, 1, 160),
(635, 309, 53, 1, 5, 300, 1, 300),
(636, 309, 47, 1, 1, 300, 1, 300),
(637, 310, 64, 4, 1, 160, 2, 320),
(638, 311, 53, 5, 3, 300, 1, 300),
(639, 311, 59, 3, 3, 100, 1, 100),
(640, 312, 45, 7, 3, 50, 1, 50),
(641, 312, 66, 4, 2, 300, 1, 300),
(642, 313, 60, 1, 2, 300, 1, 300),
(643, 314, 52, 4, 3, 130, 3, 390),
(644, 314, 44, 2, 3, 200, 1, 200),
(645, 315, 54, 4, 4, 50, 1, 50),
(646, 316, 58, 6, 4, 120, 1, 120),
(647, 316, 53, 1, 1, 300, 2, 600),
(648, 317, 49, 2, 5, 100, 1, 100),
(649, 317, 57, 6, 3, 130, 1, 130),
(650, 318, 44, 7, 5, 200, 2, 400),
(651, 319, 43, 5, 4, 300, 1, 300),
(652, 319, 55, 1, 4, 300, 3, 900),
(653, 319, 60, 3, 5, 300, 1, 300),
(654, 319, 41, 3, 5, 130, 1, 130),
(655, 320, 53, 1, 5, 300, 1, 300),
(656, 320, 58, 5, 4, 120, 1, 120),
(657, 320, 62, 5, 3, 130, 1, 130),
(658, 321, 46, 4, 1, 50, 2, 100),
(659, 322, 52, 4, 3, 130, 3, 390),
(660, 322, 44, 2, 3, 200, 3, 600),
(661, 323, 61, 4, 1, 145, 1, 145),
(662, 324, 52, 3, 1, 130, 1, 130),
(663, 325, 56, 5, 1, 300, 1, 300),
(664, 325, 45, 7, 3, 50, 1, 50),
(665, 325, 53, 7, 5, 300, 1, 300),
(666, 325, 52, 3, 3, 130, 1, 130),
(667, 326, 55, 1, 4, 300, 2, 600),
(668, 326, 41, 3, 1, 130, 1, 130),
(669, 326, 65, 5, 1, 300, 1, 300),
(670, 327, 51, 3, 3, 50, 1, 50),
(671, 327, 66, 5, 1, 300, 2, 600),
(672, 328, 48, 3, 5, 150, 1, 150),
(673, 328, 44, 2, 3, 200, 1, 200),
(674, 328, 52, 1, 2, 130, 2, 260),
(675, 328, 41, 2, 5, 130, 1, 130),
(676, 329, 49, 2, 5, 100, 1, 100),
(677, 329, 66, 4, 2, 300, 1, 300),
(678, 330, 46, 5, 1, 50, 1, 50),
(679, 330, 53, 5, 1, 300, 2, 600),
(680, 330, 57, 7, 3, 130, 2, 260),
(681, 330, 41, 5, 1, 130, 1, 130),
(682, 331, 59, 6, 3, 100, 1, 100),
(683, 332, 44, 7, 2, 200, 2, 400),
(684, 333, 60, 6, 2, 300, 1, 300),
(685, 334, 53, 1, 1, 300, 1, 300),
(686, 335, 60, 2, 4, 300, 1, 300),
(687, 335, 63, 1, 3, 130, 2, 260),
(688, 335, 53, 4, 1, 300, 2, 600),
(689, 335, 57, 2, 3, 130, 1, 130),
(690, 336, 49, 2, 5, 100, 1, 100),
(691, 336, 44, 7, 5, 200, 1, 200),
(692, 337, 66, 5, 5, 300, 1, 300),
(693, 337, 54, 1, 4, 50, 2, 100),
(694, 337, 53, 1, 3, 300, 1, 300),
(695, 338, 47, 1, 1, 300, 1, 300),
(696, 338, 58, 5, 5, 120, 1, 120),
(697, 338, 46, 4, 5, 50, 1, 50),
(698, 339, 46, 4, 5, 50, 1, 50),
(699, 340, 49, 2, 5, 100, 2, 200),
(700, 341, 51, 3, 3, 50, 1, 50),
(701, 341, 62, 3, 5, 130, 1, 130),
(702, 342, 64, 1, 1, 160, 1, 160),
(703, 343, 51, 3, 3, 50, 1, 50),
(704, 343, 66, 4, 2, 300, 2, 600),
(705, 344, 56, 7, 5, 300, 1, 300),
(706, 345, 52, 1, 1, 130, 1, 130),
(707, 345, 56, 7, 5, 300, 1, 300),
(708, 346, 49, 3, 1, 100, 2, 200),
(709, 346, 45, 7, 1, 50, 3, 150),
(710, 346, 62, 3, 3, 130, 1, 130),
(711, 347, 43, 7, 3, 300, 1, 300),
(712, 347, 52, 4, 3, 130, 1, 130),
(713, 348, 66, 1, 1, 300, 1, 300),
(714, 348, 60, 2, 5, 300, 1, 300),
(715, 349, 52, 1, 2, 130, 1, 130),
(716, 349, 45, 7, 3, 50, 3, 150),
(717, 350, 41, 3, 1, 130, 1, 130),
(718, 351, 64, 5, 4, 160, 1, 160),
(719, 351, 65, 2, 3, 300, 3, 900),
(720, 352, 41, 5, 5, 130, 2, 260),
(721, 353, 49, 2, 1, 100, 1, 100),
(722, 353, 56, 4, 3, 300, 2, 600),
(723, 354, 43, 5, 3, 300, 1, 300),
(724, 354, 55, 1, 4, 300, 1, 300),
(725, 355, 48, 2, 3, 150, 3, 450),
(726, 355, 58, 5, 5, 120, 2, 240),
(727, 356, 59, 5, 1, 100, 1, 100),
(728, 356, 48, 2, 3, 150, 1, 150),
(729, 357, 41, 2, 4, 130, 1, 130),
(730, 357, 61, 5, 4, 145, 2, 290),
(731, 357, 57, 7, 3, 130, 2, 260),
(732, 358, 62, 3, 5, 130, 2, 260),
(733, 359, 45, 7, 1, 50, 1, 50),
(734, 360, 43, 7, 3, 300, 1, 300),
(735, 360, 51, 7, 3, 50, 1, 50),
(736, 361, 57, 2, 5, 130, 2, 260),
(737, 361, 56, 3, 5, 300, 1, 300),
(738, 361, 46, 4, 1, 50, 2, 100),
(739, 362, 63, 2, 4, 130, 2, 260),
(740, 362, 65, 5, 3, 300, 1, 300),
(741, 363, 52, 3, 2, 130, 1, 130),
(742, 363, 55, 1, 4, 300, 3, 900),
(743, 363, 59, 7, 1, 100, 3, 300),
(744, 364, 51, 1, 3, 50, 1, 50),
(745, 364, 61, 4, 4, 145, 3, 435),
(746, 364, 57, 2, 5, 130, 2, 260),
(747, 365, 49, 2, 5, 100, 1, 100),
(748, 366, 64, 4, 1, 160, 1, 160),
(749, 366, 47, 1, 1, 300, 1, 300),
(750, 367, 48, 2, 3, 150, 1, 150),
(751, 368, 53, 4, 1, 300, 1, 300),
(752, 368, 57, 2, 3, 130, 1, 130),
(753, 369, 47, 5, 3, 300, 1, 300),
(754, 369, 54, 1, 3, 50, 1, 50),
(755, 369, 65, 7, 3, 300, 2, 600),
(756, 369, 66, 1, 5, 300, 1, 300),
(757, 370, 60, 1, 2, 300, 1, 300),
(758, 370, 62, 7, 3, 130, 1, 130),
(759, 370, 65, 2, 3, 300, 1, 300),
(760, 371, 48, 6, 3, 150, 1, 150),
(761, 371, 58, 6, 4, 120, 1, 120),
(762, 372, 58, 6, 4, 120, 2, 240),
(763, 372, 55, 1, 3, 300, 1, 300),
(764, 372, 61, 4, 4, 145, 2, 290),
(765, 373, 52, 1, 2, 130, 1, 130),
(766, 374, 44, 3, 2, 200, 1, 200),
(767, 374, 60, 2, 4, 300, 1, 300),
(768, 375, 52, 3, 2, 130, 2, 260),
(769, 375, 66, 1, 1, 300, 1, 300),
(770, 375, 65, 2, 1, 300, 1, 300),
(771, 376, 60, 1, 4, 300, 1, 300),
(772, 376, 64, 1, 4, 160, 1, 160),
(773, 377, 61, 1, 1, 145, 1, 145),
(774, 377, 62, 5, 3, 130, 1, 130),
(775, 377, 48, 3, 5, 150, 1, 150),
(776, 377, 66, 1, 1, 300, 3, 900),
(777, 378, 61, 5, 1, 145, 1, 145),
(778, 378, 60, 1, 5, 300, 2, 600),
(779, 379, 61, 5, 4, 145, 2, 290),
(780, 380, 44, 7, 2, 200, 2, 400),
(781, 380, 60, 2, 5, 300, 1, 300),
(782, 380, 61, 4, 1, 145, 1, 145),
(783, 381, 58, 5, 5, 120, 1, 120),
(784, 381, 65, 5, 3, 300, 1, 300),
(785, 382, 63, 3, 3, 130, 1, 130),
(786, 383, 59, 7, 1, 100, 1, 100),
(787, 383, 51, 3, 3, 50, 1, 50),
(788, 383, 61, 1, 4, 145, 2, 290),
(789, 384, 58, 5, 4, 120, 2, 240),
(790, 385, 41, 5, 5, 130, 1, 130),
(791, 386, 63, 3, 4, 130, 1, 130),
(792, 387, 45, 7, 1, 50, 1, 50),
(793, 387, 41, 2, 5, 130, 1, 130),
(794, 387, 46, 5, 1, 50, 2, 100),
(795, 388, 55, 7, 3, 300, 1, 300),
(796, 388, 65, 2, 1, 300, 1, 300),
(797, 389, 64, 1, 4, 160, 1, 160),
(798, 389, 44, 3, 2, 200, 2, 400),
(799, 389, 52, 1, 1, 130, 2, 260),
(800, 390, 45, 7, 3, 50, 2, 100),
(801, 390, 53, 4, 1, 300, 2, 600),
(802, 391, 64, 4, 4, 160, 1, 160),
(803, 391, 62, 1, 2, 130, 1, 130),
(804, 391, 45, 7, 1, 50, 2, 100),
(805, 392, 53, 7, 1, 300, 1, 300),
(806, 393, 65, 5, 1, 300, 1, 300),
(807, 394, 45, 1, 3, 50, 1, 50),
(808, 394, 41, 2, 5, 130, 1, 130),
(809, 395, 48, 6, 3, 150, 1, 150),
(810, 396, 64, 5, 4, 160, 1, 160),
(811, 396, 47, 3, 3, 300, 1, 300),
(812, 396, 63, 3, 4, 130, 2, 260),
(813, 397, 66, 1, 5, 300, 1, 300),
(814, 398, 46, 4, 1, 50, 2, 100),
(815, 399, 54, 4, 4, 50, 1, 50),
(816, 399, 46, 4, 1, 50, 3, 150),
(817, 399, 60, 3, 4, 300, 1, 300),
(818, 399, 65, 5, 1, 300, 2, 600),
(819, 400, 66, 5, 2, 300, 1, 300),
(820, 401, 56, 7, 3, 300, 1, 300),
(821, 401, 61, 4, 1, 145, 2, 290),
(822, 401, 47, 1, 1, 300, 1, 300),
(823, 401, 46, 4, 5, 50, 1, 50),
(824, 402, 56, 4, 1, 300, 3, 900),
(825, 402, 65, 5, 1, 300, 1, 300),
(826, 403, 54, 1, 4, 50, 1, 50),
(827, 403, 41, 5, 5, 130, 1, 130),
(828, 404, 43, 7, 3, 300, 1, 300),
(829, 404, 52, 1, 1, 130, 1, 130),
(830, 404, 51, 3, 3, 50, 1, 50),
(831, 405, 41, 5, 4, 130, 1, 130),
(832, 406, 58, 5, 5, 120, 1, 120),
(833, 407, 46, 5, 1, 50, 1, 50),
(834, 407, 44, 2, 2, 200, 2, 400),
(835, 408, 45, 7, 1, 50, 1, 50),
(836, 409, 64, 5, 4, 160, 2, 320),
(837, 410, 56, 7, 5, 300, 1, 300),
(838, 411, 52, 1, 1, 130, 3, 390),
(839, 412, 55, 4, 3, 300, 2, 600),
(840, 413, 52, 1, 1, 130, 3, 390),
(841, 414, 49, 3, 1, 100, 1, 100),
(842, 415, 66, 1, 2, 300, 1, 300),
(843, 415, 46, 5, 2, 50, 2, 100),
(844, 416, 47, 3, 1, 300, 1, 300),
(845, 416, 41, 2, 4, 130, 1, 130),
(846, 416, 57, 2, 5, 130, 1, 130),
(847, 417, 56, 3, 3, 300, 1, 300),
(848, 418, 53, 1, 1, 300, 2, 600),
(849, 419, 62, 1, 5, 130, 1, 130),
(850, 419, 60, 3, 2, 300, 1, 300),
(851, 419, 48, 2, 3, 150, 1, 150);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `pro_id` int(10) NOT NULL,
  `pro_name` varchar(255) NOT NULL,
  `pro_img` varchar(255) NOT NULL,
  `pro_price` decimal(38,2) NOT NULL,
  `pro_desc` text NOT NULL,
  `pro_brand` varchar(55) NOT NULL,
  `pro_stock` int(11) NOT NULL DEFAULT 0,
  `cate_id` int(10) NOT NULL,
  `ncc_id` int(10) NOT NULL DEFAULT 1 COMMENT 'ID nhà cung cấp',
  `trangthai` int(1) NOT NULL DEFAULT 0,
  `pro_viewer` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`pro_id`, `pro_name`, `pro_img`, `pro_price`, `pro_desc`, `pro_brand`, `pro_stock`, `cate_id`, `ncc_id`, `trangthai`, `pro_viewer`) VALUES
(41, 'Fleece Hoodies For Men and Women Patchwork Stripe Hooded', 'products/1', 134.00, 'Usually We Will Send Out the Goods Within 3 - 7 Days by Fast and Reliable Shipping Methods. As different computers display colors differently, the color of the actual item may very slightly from the above images.', 'Lining', 600, 10, 1, 0, 1),
(43, 'School Leavers Hoodie - School Leavers 2023 Hoodie - Class Of 2023 Hoodie', 'products/2', 300.00, 'Usually We Will Send Out the Goods Within 3 - 7 Days by Fast and Reliable Shipping Methods. Your Happy Shopping Experience and Five Star Feedback is Very Important to Us.', 'Nice', 177, 10, 2, 0, 0),
(44, 'Branded Premium Basic Mesh Shorts', 'products/3', 200.00, 'Branded Premium Basic Mesh Shorts Mesh 2 Pockets Eric Emanuel New York City Skyline Basketball shorts', 'Lining', 493, 14, 3, 0, 0),
(45, 'High Waist Trousers Pants Palazzo Bottoms Casual Loose Wide Leg Long Harem', 'products/4', 50.00, 'Usually We Will Send Out the Goods Within 3 - 7 Days by Fast and Reliable Shipping Methods. Please Feel Free to Contact Us if You Have Any Questions.', 'Lining', 252, 12, 4, 0, 0),
(46, 'Wizard Frog Corduroy Hat, Handmade Embroidered Corduroy Dad Cap', 'products/5', 50.00, 'All clothes are tailored and handmade with love and attention to details. Pure natural linen materials, comfortable, breathable, refreshing and soft fabric', 'Nice', 265, 11, 5, 0, 1),
(47, 'Women Linen Blazer, Linen Jacket Linen Blazer 3/4 Sleeves Coat', 'products/6', 300.00, 'All clothes are tailored and handmade with love and attention to details. Pure natural linen materials, comfortable, breathable, refreshing and soft fabric', 'Nice', 657, 9, 6, 0, 2),
(48, 'Masculine-cut Blazer with Contrasting Details', 'products/7', 150.00, 'An oversize blazer with masculine cut and contrasting seam details. An avant-garde piece with linen and tulle elements. Bold addition to any elegant outfit.', 'Need', 328, 9, 7, 0, 0),
(49, 'Sorrows, Sorrows, Prayers Tshirt, Queen Charlotte Fan Shirt, Bridgerton T-Shirt', 'products/8', 100.00, 'Unisex Heavy Cotton Tee Gildan 5000: Medium fabric - Classic fit - Runs true to size - 100% cotton - Tear-away label.', 'Lining', 274, 13, 8, 0, 2),
(51, 'Hat Embroidered Hat Dad Hat Womens Baseball Cap Man Hat', 'products/9', 50.00, 'Usually We Will Send Out the Goods Within 3 - 7 Days by Fast and Reliable Shipping Methods. Your Happy Shopping Experience is Very Important to Us.', 'Lining', 276, 11, 9, 0, 0),
(52, 'Chicken Sweatshirt, Farm Life Sweater, Chicken Lover Sweater, Easter Retro Sweater', 'products/10', 130.00, 'Welcome to Prime Tee Lab. Your all-inclusive stop for all custom sweatshirt, hoodie, and gift needs. We print on the highest quality garments!', 'Nice', 388, 15, 10, 0, 0),
(53, 'Have A Good Day Hoodie- Trendy sweatshirt, hoodie', 'products/11', 300.00, 'Have A Good Day Hoodie- Trendy sweatshirt, hoodie, Sweatshirt and hoodies with words on back. Hey there, welcome to my store!', 'Thosc', 522, 10, 1, 0, 5),
(54, 'Floral Embroidered Cap, Baseball Cap, Custom Embroidery Hat, Hand Embroidery Hat', 'products/12', 50.00, 'A pretty hand embroidered cap that is perfect for everyday wear! Available in different colors and designs.', 'Lining', 351, 11, 2, 0, 2),
(55, 'Drawstring Pants - Spring Summer Trousers - Pockets', 'products/13', 300.00, 'Stay comfortable and stylish with these cotton and linen drawstring pants for women. Designed with a basic style and loose fit, these mid-waist trousers have a nine-point length.', 'Nice', 654, 12, 3, 0, 1),
(56, 'Loose linen blazer PLACID Long sleeve light linen jacket Linen womens clothing', 'products/14', 300.00, 'Womens linen blazer PLACID in lightweight linen with front button closure is an autumn wardrobes essential', 'Need', 759, 9, 4, 0, 2),
(57, 'Baseball Hat with Embroidery - Embroidered & Cap Color of your choice! Dad Hat', 'products/15', 130.00, 'Please let us know if you have a specific date that you would like to receive by. Orders that have been shipped out are non-refundable.', 'Thosc', 370, 11, 5, 0, 1),
(58, 'Embroidered Silly Goose Sweatshirt, Embroidered Goose Crewneck Sweats', 'products/16', 120.00, 'This embroidered Silly Goose sweatshirt is super soft and cozy. Perfect to lounge around, run errands, or walk your dog.', 'Densnis', 280, 15, 6, 0, 2),
(59, 'Embroidered Hat Initial cap Personalized Ball cap Custom Hat Mens Hat 90s Vintage', 'products/17', 100.00, 'Personalize these comfortable and stylish dad hats to say whatever you like. A traditional baseball cap is a great way to show off your style.', 'Need', 336, 11, 7, 0, 4),
(60, 'Thin Cotton Blazer Loose Linen Jackets Pockets Soft Linen Coats Three Quarter Single Button', 'products/18', 300.00, 'Cotton Blazer Loose Linen Jackets Pockets Soft Linen Coats Three Quarter Single Button. Please refer to the final image for size chart before ordering.', 'Densnis', 675, 9, 8, 0, 3),
(61, 'Fashion Sweaters Men Autumn Solid Color Wool Sweaters Slim Fit', 'products/19', 145.00, 'Fashion Sweaters Men Autumn Solid Color Wool Sweaters Slim Fit Men Street Wear Mens Clothes Knitted Sweater Men Pullovers', 'Lining', 589, 15, 9, 0, 2),
(62, 'portrait from photo to shirt, outline photo sweatshirt, Custom Photo, Couple Hoodie', 'products/20', 130.00, 'Set-in sleeve 1x1 rib at neck collar Inside back neck tape in self fabric Tubular construction Comfortable crew neckline', 'Need', 596, 10, 10, 0, 5),
(63, 'Cotton Corduroy Pant Elastic Waist Pants Womens Soft Warm trousers baggy pants Casual', 'products/21', 130.00, 'All our items are Tailored and Handmade and Made to Order, I can make Any Size. I design new styles every week, please collect my store.', 'Densnis', 416, 12, 1, 0, 5),
(64, 'Knitted Sweater Little Dinosaur, Unisex, Winter Harajuku', 'products/22', 160.00, 'Super soft comfortable knitted sweater. A perfect gift for streetwear style lovers and a perfect harajuku outfit.', 'Thosc', 446, 15, 2, 0, 4),
(65, 'Japanese Harajuku Style Hoodies, Streetwear Oversized Hoodie, Thick Winter Autumn Pullover', 'products/23', 300.00, 'Recommend ordering two sizes up. MATERIAL: Cotton, Polyester. It is printed with eco-friendly ink. Soft and comfy hoodie with a print of Japanese graphic art.', 'Densnis', 350, 10, 3, 0, 11),
(66, 'Unleash Your Summer Swag with High-Quality Fear Of God Shorts for Men and Women', 'products/24', 300.00, 'Upgrade your summer wardrobe with our exclusive collection of Essentials Shorts for both men and women! Discover the perfect blend of comfort and style.', 'Lining', 684, 14, 4, 0, 40);

-- --------------------------------------------------------

--
-- Table structure for table `products_favourite`
--

CREATE TABLE `products_favourite` (
  `pro_favourite_id` int(10) NOT NULL,
  `kh_id` int(10) NOT NULL,
  `pro_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `products_favourite`
--

INSERT INTO `products_favourite` (`pro_favourite_id`, `kh_id`, `pro_id`) VALUES
(1, 11, 48),
(2, 11, 52),
(3, 11, 66),
(4, 12, 57),
(5, 12, 61),
(6, 12, 45),
(7, 41, 46),
(8, 41, 54),
(9, 41, 51),
(10, 41, 48),
(11, 41, 59),
(12, 42, 43),
(13, 42, 62),
(14, 42, 47),
(15, 42, 66),
(16, 42, 63),
(17, 42, 44),
(18, 43, 48),
(19, 43, 59),
(20, 43, 45),
(21, 44, 53),
(22, 44, 49),
(23, 44, 58),
(24, 44, 62),
(25, 44, 47),
(26, 44, 52),
(27, 44, 66),
(28, 45, 64),
(29, 45, 53),
(30, 45, 60),
(31, 46, 55),
(32, 46, 63),
(33, 46, 54),
(34, 46, 57),
(35, 46, 46),
(36, 46, 51),
(37, 46, 60),
(38, 47, 55),
(39, 47, 61),
(40, 47, 57),
(41, 47, 63),
(42, 48, 60),
(43, 48, 63),
(44, 49, 60),
(45, 49, 61),
(46, 49, 63),
(47, 49, 52),
(48, 50, 66),
(49, 50, 45),
(50, 50, 60),
(51, 50, 53),
(52, 51, 54),
(53, 51, 62),
(54, 52, 61),
(55, 52, 56),
(56, 52, 47),
(57, 53, 54),
(58, 53, 51),
(59, 53, 57);

-- --------------------------------------------------------

--
-- Table structure for table `pro_chitiet`
--

CREATE TABLE `pro_chitiet` (
  `ctiet_pro_id` int(10) NOT NULL COMMENT 'ID chi tiết sản phẩm',
  `pro_id` int(10) NOT NULL COMMENT 'ID sản phẩm',
  `color_id` int(10) NOT NULL COMMENT 'ID màu',
  `size_id` int(10) NOT NULL COMMENT 'ID size',
  `sku` varchar(100) NOT NULL,
  `soluong` int(10) NOT NULL DEFAULT 0 COMMENT 'Số lượng',
  `gia_ban` decimal(12,2) DEFAULT NULL,
  `trangthai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `pro_chitiet`
--

INSERT INTO `pro_chitiet` (`ctiet_pro_id`, `pro_id`, `color_id`, `size_id`, `sku`, `soluong`, `gia_ban`, `trangthai`) VALUES
(10, 43, 7, 3, 'SKU-43-7-3', 99, NULL, 0),
(11, 43, 5, 3, 'SKU-43-5-3', 26, NULL, 0),
(12, 43, 5, 4, 'SKU-43-5-4', 52, NULL, 0),
(13, 44, 2, 3, 'SKU-44-2-3', 43, NULL, 0),
(14, 44, 2, 2, 'SKU-44-2-2', 25, NULL, 0),
(15, 44, 7, 3, 'SKU-44-7-3', 45, NULL, 0),
(16, 44, 7, 2, 'SKU-44-7-2', 56, NULL, 0),
(17, 44, 7, 5, 'SKU-44-7-5', 51, NULL, 0),
(18, 44, 3, 3, 'SKU-44-3-3', 117, NULL, 0),
(19, 44, 3, 2, 'SKU-44-3-2', 57, NULL, 0),
(20, 44, 3, 5, 'SKU-44-3-5', 99, NULL, 0),
(21, 45, 1, 3, 'SKU-45-1-3', 117, NULL, 0),
(22, 45, 1, 1, 'SKU-45-1-1', 69, NULL, 0),
(23, 45, 7, 3, 'SKU-45-7-3', 48, NULL, 0),
(24, 45, 7, 1, 'SKU-45-7-1', 18, NULL, 0),
(25, 46, 5, 1, 'SKU-46-5-1', 44, NULL, 0),
(26, 46, 5, 2, 'SKU-46-5-2', 98, NULL, 0),
(27, 46, 4, 5, 'SKU-46-4-5', 94, NULL, 0),
(28, 46, 4, 1, 'SKU-46-4-1', 29, NULL, 0),
(29, 47, 1, 1, 'SKU-47-1-1', 119, NULL, 0),
(30, 47, 1, 3, 'SKU-47-1-3', 95, NULL, 0),
(31, 47, 3, 1, 'SKU-47-3-1', 96, NULL, 0),
(32, 47, 3, 4, 'SKU-47-3-4', 66, NULL, 0),
(33, 47, 3, 3, 'SKU-47-3-3', 59, NULL, 0),
(34, 47, 5, 1, 'SKU-47-5-1', 77, NULL, 0),
(35, 47, 5, 4, 'SKU-47-5-4', 48, NULL, 0),
(36, 47, 5, 3, 'SKU-47-5-3', 97, NULL, 0),
(37, 48, 3, 5, 'SKU-48-3-5', 112, NULL, 0),
(38, 48, 2, 3, 'SKU-48-2-3', 111, NULL, 0),
(39, 48, 6, 3, 'SKU-48-6-3', 105, NULL, 0),
(40, 49, 6, 5, 'SKU-49-6-5', 109, NULL, 0),
(41, 49, 3, 1, 'SKU-49-3-1', 74, NULL, 0),
(42, 49, 2, 5, 'SKU-49-2-5', 16, NULL, 0),
(43, 49, 2, 1, 'SKU-49-2-1', 75, NULL, 0),
(44, 51, 3, 3, 'SKU-51-3-3', 71, NULL, 0),
(45, 51, 7, 3, 'SKU-51-7-3', 116, NULL, 0),
(46, 51, 1, 3, 'SKU-51-1-3', 89, NULL, 0),
(47, 52, 3, 2, 'SKU-52-3-2', 31, NULL, 0),
(48, 52, 3, 1, 'SKU-52-3-1', 62, NULL, 0),
(49, 52, 3, 3, 'SKU-52-3-3', 20, NULL, 0),
(50, 52, 4, 3, 'SKU-52-4-3', 70, NULL, 0),
(51, 52, 1, 2, 'SKU-52-1-2', 43, NULL, 0),
(52, 52, 1, 1, 'SKU-52-1-1', 73, NULL, 0),
(53, 52, 1, 3, 'SKU-52-1-3', 89, NULL, 0),
(54, 53, 1, 1, 'SKU-53-1-1', 33, NULL, 0),
(55, 53, 1, 3, 'SKU-53-1-3', 26, NULL, 0),
(56, 53, 1, 5, 'SKU-53-1-5', 60, NULL, 0),
(57, 53, 5, 1, 'SKU-53-5-1', 78, NULL, 0),
(58, 53, 5, 3, 'SKU-53-5-3', 66, NULL, 0),
(59, 53, 4, 1, 'SKU-53-4-1', 108, NULL, 0),
(60, 53, 7, 1, 'SKU-53-7-1', 23, NULL, 0),
(61, 53, 7, 3, 'SKU-53-7-3', 84, NULL, 0),
(62, 53, 7, 5, 'SKU-53-7-5', 44, NULL, 0),
(63, 54, 1, 3, 'SKU-54-1-3', 107, NULL, 0),
(64, 54, 1, 4, 'SKU-54-1-4', 116, NULL, 0),
(65, 54, 4, 3, 'SKU-54-4-3', 53, NULL, 0),
(66, 54, 4, 4, 'SKU-54-4-4', 75, NULL, 0),
(67, 55, 4, 3, 'SKU-55-4-3', 108, NULL, 0),
(68, 55, 2, 1, 'SKU-55-2-1', 43, NULL, 0),
(69, 55, 2, 3, 'SKU-55-2-3', 63, NULL, 0),
(70, 55, 2, 4, 'SKU-55-2-4', 99, NULL, 0),
(71, 55, 1, 1, 'SKU-55-1-1', 15, NULL, 0),
(72, 55, 1, 3, 'SKU-55-1-3', 64, NULL, 0),
(73, 55, 1, 4, 'SKU-55-1-4', 81, NULL, 0),
(74, 55, 7, 1, 'SKU-55-7-1', 75, NULL, 0),
(75, 55, 7, 3, 'SKU-55-7-3', 106, NULL, 0),
(76, 56, 5, 5, 'SKU-56-5-5', 79, NULL, 0),
(77, 56, 5, 3, 'SKU-56-5-3', 71, NULL, 0),
(78, 56, 5, 1, 'SKU-56-5-1', 83, NULL, 0),
(79, 56, 3, 5, 'SKU-56-3-5', 28, NULL, 0),
(80, 56, 3, 3, 'SKU-56-3-3', 82, NULL, 0),
(81, 56, 4, 5, 'SKU-56-4-5', 19, NULL, 0),
(82, 56, 4, 3, 'SKU-56-4-3', 77, NULL, 0),
(83, 56, 4, 1, 'SKU-56-4-1', 108, NULL, 0),
(84, 56, 7, 5, 'SKU-56-7-5', 35, NULL, 0),
(85, 56, 7, 3, 'SKU-56-7-3', 86, NULL, 0),
(86, 56, 7, 1, 'SKU-56-7-1', 91, NULL, 0),
(87, 57, 7, 3, 'SKU-57-7-3', 105, NULL, 0),
(88, 57, 2, 3, 'SKU-57-2-3', 34, NULL, 0),
(89, 57, 2, 5, 'SKU-57-2-5', 108, NULL, 0),
(90, 57, 6, 3, 'SKU-57-6-3', 97, NULL, 0),
(91, 57, 6, 5, 'SKU-57-6-5', 26, NULL, 0),
(92, 58, 1, 4, 'SKU-58-1-4', 58, NULL, 0),
(93, 58, 4, 5, 'SKU-58-4-5', 33, NULL, 0),
(94, 58, 6, 4, 'SKU-58-6-4', 89, NULL, 0),
(95, 58, 5, 5, 'SKU-58-5-5', 31, NULL, 0),
(96, 58, 5, 4, 'SKU-58-5-4', 69, NULL, 0),
(97, 59, 3, 3, 'SKU-59-3-3', 110, NULL, 0),
(98, 59, 5, 1, 'SKU-59-5-1', 25, NULL, 0),
(99, 59, 6, 3, 'SKU-59-6-3', 97, NULL, 0),
(100, 59, 7, 3, 'SKU-59-7-3', 62, NULL, 0),
(101, 59, 7, 1, 'SKU-59-7-1', 42, NULL, 0),
(102, 60, 2, 4, 'SKU-60-2-4', 60, NULL, 0),
(103, 60, 2, 5, 'SKU-60-2-5', 52, NULL, 0),
(104, 60, 6, 2, 'SKU-60-6-2', 61, NULL, 0),
(105, 60, 6, 4, 'SKU-60-6-4', 114, NULL, 0),
(106, 60, 6, 5, 'SKU-60-6-5', 63, NULL, 0),
(107, 60, 1, 2, 'SKU-60-1-2', 26, NULL, 0),
(108, 60, 1, 4, 'SKU-60-1-4', 57, NULL, 0),
(109, 60, 1, 5, 'SKU-60-1-5', 70, NULL, 0),
(110, 60, 3, 2, 'SKU-60-3-2', 89, NULL, 0),
(111, 60, 3, 4, 'SKU-60-3-4', 20, NULL, 0),
(112, 60, 3, 5, 'SKU-60-3-5', 63, NULL, 0),
(113, 61, 5, 4, 'SKU-61-5-4', 79, NULL, 0),
(114, 61, 5, 1, 'SKU-61-5-1', 22, NULL, 0),
(115, 61, 4, 4, 'SKU-61-4-4', 38, NULL, 0),
(116, 61, 4, 1, 'SKU-61-4-1', 30, NULL, 0),
(117, 61, 4, 3, 'SKU-61-4-3', 98, NULL, 0),
(118, 61, 1, 4, 'SKU-61-1-4', 106, NULL, 0),
(119, 61, 1, 1, 'SKU-61-1-1', 114, NULL, 0),
(120, 61, 1, 3, 'SKU-61-1-3', 102, NULL, 0),
(121, 62, 7, 3, 'SKU-62-7-3', 77, NULL, 0),
(122, 62, 3, 5, 'SKU-62-3-5', 44, NULL, 0),
(123, 62, 3, 3, 'SKU-62-3-3', 36, NULL, 0),
(124, 62, 3, 2, 'SKU-62-3-2', 49, NULL, 0),
(125, 62, 5, 5, 'SKU-62-5-5', 16, NULL, 0),
(126, 62, 5, 3, 'SKU-62-5-3', 37, NULL, 0),
(127, 62, 5, 2, 'SKU-62-5-2', 102, NULL, 0),
(128, 62, 1, 5, 'SKU-62-1-5', 40, NULL, 0),
(129, 62, 1, 3, 'SKU-62-1-3', 118, NULL, 0),
(130, 62, 1, 2, 'SKU-62-1-2', 77, NULL, 0),
(131, 63, 1, 3, 'SKU-63-1-3', 39, NULL, 0),
(132, 63, 1, 4, 'SKU-63-1-4', 19, NULL, 0),
(133, 63, 3, 3, 'SKU-63-3-3', 70, NULL, 0),
(134, 63, 3, 4, 'SKU-63-3-4', 89, NULL, 0),
(135, 63, 2, 3, 'SKU-63-2-3', 114, NULL, 0),
(136, 63, 2, 4, 'SKU-63-2-4', 85, NULL, 0),
(137, 64, 1, 1, 'SKU-64-1-1', 94, NULL, 0),
(138, 64, 1, 4, 'SKU-64-1-4', 114, NULL, 0),
(139, 64, 4, 1, 'SKU-64-4-1', 74, NULL, 0),
(140, 64, 4, 4, 'SKU-64-4-4', 33, NULL, 0),
(141, 64, 7, 4, 'SKU-64-7-4', 40, NULL, 0),
(142, 64, 5, 1, 'SKU-64-5-1', 62, NULL, 0),
(143, 64, 5, 4, 'SKU-64-5-4', 29, NULL, 0),
(144, 65, 7, 3, 'SKU-65-7-3', 64, NULL, 0),
(145, 65, 7, 1, 'SKU-65-7-1', 75, NULL, 0),
(146, 65, 2, 3, 'SKU-65-2-3', 71, NULL, 0),
(147, 65, 2, 1, 'SKU-65-2-1', 50, NULL, 0),
(148, 65, 5, 3, 'SKU-65-5-3', 43, NULL, 0),
(149, 65, 5, 1, 'SKU-65-5-1', 47, NULL, 0),
(150, 66, 1, 1, 'SKU-66-1-1', 38, NULL, 0),
(151, 66, 1, 2, 'SKU-66-1-2', 100, NULL, 0),
(152, 66, 1, 5, 'SKU-66-1-5', 110, NULL, 0),
(153, 66, 4, 1, 'SKU-66-4-1', 91, NULL, 0),
(154, 66, 4, 2, 'SKU-66-4-2', 90, NULL, 0),
(155, 66, 5, 1, 'SKU-66-5-1', 79, NULL, 0),
(156, 66, 5, 2, 'SKU-66-5-2', 67, NULL, 0),
(157, 66, 5, 5, 'SKU-66-5-5', 109, NULL, 0),
(158, 41, 3, 1, 'SKU-41-3-1', 10, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ql_nhom_quyen`
--

CREATE TABLE `ql_nhom_quyen` (
  `id` int(11) NOT NULL,
  `ten_nhom_quyen` varchar(100) NOT NULL,
  `mo_ta` text DEFAULT NULL,
  `ngay_tao` datetime DEFAULT current_timestamp(),
  `ngay_cap_nhat` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `trang_thai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `ql_nhom_quyen`
--

INSERT INTO `ql_nhom_quyen` (`id`, `ten_nhom_quyen`, `mo_ta`, `ngay_tao`, `ngay_cap_nhat`, `trang_thai`) VALUES
(1, 'Quản trị hệ thống', 'Toàn quyền trên hệ thống', '2026-09-19 06:36:35', '2026-09-19 06:36:35', 1),
(2, 'Nhân viên bán hàng', 'Xử lý đơn hàng, bình luận, đổi trả', '2026-09-19 06:36:35', '2026-09-19 06:36:35', 1),
(3, 'Nhân viên kho', 'Quản lý nhập hàng, tồn kho, kiểm kê', '2026-09-19 06:36:35', '2026-09-19 06:36:35', 1),
(4, 'Khách hàng', 'Chỉ sử dụng chức năng phía người dùng', '2026-09-19 06:36:35', '2026-09-19 06:36:35', 1);

-- --------------------------------------------------------

--
-- Table structure for table `quyen`
--

CREATE TABLE `quyen` (
  `permission_id` varchar(10) NOT NULL,
  `permission_name` varchar(255) NOT NULL,
  `trang_thai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `quyen`
--

INSERT INTO `quyen` (`permission_id`, `permission_name`, `trang_thai`) VALUES
('Q1', 'Truy cập quản lý', 1),
('Q10', 'Quản lý thương hiệu', 1),
('Q11', 'Quản lý phiếu nhập', 1),
('Q12', 'Quản lý phiếu bảo hành', 1),
('Q13', 'Quản lý phiếu đổi/trả', 1),
('Q14', 'Quản lý phân quyền', 1),
('Q15', 'Quản lý vai trò', 1),
('Q2', 'Quản lý danh mục', 1),
('Q3', 'Quản lý sản phẩm', 1),
('Q4', 'Quản lý người dùng', 1),
('Q5', 'Quản lý bình luận', 1),
('Q6', 'Quản lý thống kê', 1),
('Q7', 'Quản lý đơn hàng', 1),
('Q8', 'Quản lý nhà cung cấp', 1),
('Q9', 'Quản lý màu', 1);

-- --------------------------------------------------------

--
-- Table structure for table `size`
--

CREATE TABLE `size` (
  `size_id` int(10) NOT NULL COMMENT 'ID size',
  `size_name` varchar(50) NOT NULL COMMENT 'Tên size'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `size`
--

INSERT INTO `size` (`size_id`, `size_name`) VALUES
(1, 'M'),
(2, 'L'),
(3, 'XL'),
(4, 'S'),
(5, 'XXL');

-- --------------------------------------------------------

--
-- Table structure for table `thuong_hieu`
--

CREATE TABLE `thuong_hieu` (
  `id` int(11) NOT NULL,
  `ten_thuong_hieu` varchar(255) NOT NULL,
  `mo_ta` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `thuong_hieu`
--

INSERT INTO `thuong_hieu` (`id`, `ten_thuong_hieu`, `mo_ta`) VALUES
(1, 'Lining', 'Thương hiệu thời trang thể thao cao cấp'),
(2, 'Nice', 'Phong cách trẻ trung, năng động'),
(3, 'Need', 'Chuyên các sản phẩm thời trang linen'),
(4, 'Thosc', 'Thương hiệu cá tính, độc đáo'),
(5, 'Densnis', 'Đường nét thêu tay tinh xảo, thủ công');

-- --------------------------------------------------------

--
-- Table structure for table `ton_kho`
--

CREATE TABLE `ton_kho` (
  `id` int(11) NOT NULL,
  `pro_id` int(11) NOT NULL,
  `color_id` int(11) NOT NULL,
  `size_id` int(11) NOT NULL,
  `soluong` int(11) NOT NULL DEFAULT 0,
  `ngay_cap_nhat` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `ton_kho`
--

INSERT INTO `ton_kho` (`id`, `pro_id`, `color_id`, `size_id`, `soluong`, `ngay_cap_nhat`) VALUES
(128, 41, 3, 5, 74, '2026-09-19 06:36:35'),
(129, 41, 3, 1, 82, '2026-09-19 06:36:35'),
(130, 41, 2, 4, 84, '2026-09-19 06:36:35'),
(131, 41, 2, 5, 6, '2026-09-19 06:36:35'),
(132, 41, 6, 4, 48, '2026-09-19 06:36:35'),
(133, 41, 6, 5, 106, '2026-09-19 06:36:35'),
(134, 41, 5, 4, 69, '2026-09-19 06:36:35'),
(135, 41, 5, 5, 53, '2026-09-19 06:36:35'),
(136, 41, 5, 1, 30, '2026-09-19 06:36:35'),
(137, 43, 7, 3, 88, '2026-09-19 06:36:35'),
(138, 43, 5, 3, 15, '2026-09-19 06:36:35'),
(139, 43, 5, 4, 43, '2026-09-19 06:36:35'),
(140, 44, 2, 3, 31, '2026-09-19 06:36:35'),
(141, 44, 2, 2, 19, '2026-09-19 06:36:35'),
(142, 44, 7, 3, 40, '2026-09-19 06:36:35'),
(143, 44, 7, 2, 52, '2026-09-19 06:36:35'),
(144, 44, 7, 5, 41, '2026-09-19 06:36:35'),
(145, 44, 3, 3, 109, '2026-09-19 06:36:35'),
(146, 44, 3, 2, 51, '2026-09-19 06:36:35'),
(147, 44, 3, 5, 99, '2026-09-19 06:36:35'),
(148, 45, 1, 3, 108, '2026-09-19 06:36:35'),
(149, 45, 1, 1, 55, '2026-09-19 06:36:35'),
(150, 45, 7, 3, 37, '2026-09-19 06:36:35'),
(151, 45, 7, 1, 2, '2026-09-19 06:36:35'),
(152, 46, 5, 1, 38, '2026-09-19 06:36:35'),
(153, 46, 5, 2, 88, '2026-09-19 06:36:35'),
(154, 46, 4, 5, 84, '2026-09-19 06:36:35'),
(155, 46, 4, 1, 13, '2026-09-19 06:36:35'),
(156, 47, 1, 1, 110, '2026-09-19 06:36:35'),
(157, 47, 1, 3, 93, '2026-09-19 06:36:35'),
(158, 47, 3, 1, 94, '2026-09-19 06:36:35'),
(159, 47, 3, 4, 61, '2026-09-19 06:36:35'),
(160, 47, 3, 3, 56, '2026-09-19 06:36:35'),
(161, 47, 5, 1, 76, '2026-09-19 06:36:35'),
(162, 47, 5, 4, 43, '2026-09-19 06:36:35'),
(163, 47, 5, 3, 95, '2026-09-19 06:36:35'),
(164, 48, 3, 5, 99, '2026-09-19 06:36:35'),
(165, 48, 2, 3, 91, '2026-09-19 06:36:35'),
(166, 48, 6, 3, 91, '2026-09-19 06:36:35'),
(167, 49, 6, 5, 101, '2026-09-19 06:36:35'),
(168, 49, 3, 1, 57, '2026-09-19 06:36:35'),
(169, 49, 2, 5, 7, '2026-09-19 06:36:35'),
(170, 49, 2, 1, 61, '2026-09-19 06:36:35'),
(171, 51, 3, 3, 51, '2026-09-19 06:36:35'),
(172, 51, 7, 3, 107, '2026-09-19 06:36:35'),
(173, 51, 1, 3, 80, '2026-09-19 06:36:35'),
(174, 52, 3, 2, 26, '2026-09-19 06:36:35'),
(175, 52, 3, 1, 48, '2026-09-19 06:36:35'),
(176, 52, 3, 3, 19, '2026-09-19 06:36:35'),
(177, 52, 4, 3, 62, '2026-09-19 06:36:35'),
(178, 52, 1, 2, 32, '2026-09-19 06:36:35'),
(179, 52, 1, 1, 60, '2026-09-19 06:36:35'),
(180, 52, 1, 3, 85, '2026-09-19 06:36:35'),
(181, 53, 1, 1, 32, '2026-09-19 06:36:35'),
(182, 53, 1, 3, 21, '2026-09-19 06:36:35'),
(183, 53, 1, 5, 56, '2026-09-19 06:36:35'),
(184, 53, 5, 1, 74, '2026-09-19 06:36:35'),
(185, 53, 5, 3, 62, '2026-09-19 06:36:35'),
(186, 53, 4, 1, 101, '2026-09-19 06:36:35'),
(187, 53, 7, 1, 22, '2026-09-19 06:36:35'),
(188, 53, 7, 3, 83, '2026-09-19 06:36:35'),
(189, 53, 7, 5, 32, '2026-09-19 06:36:35'),
(190, 54, 1, 3, 99, '2026-09-19 06:36:35'),
(191, 54, 1, 4, 106, '2026-09-19 06:36:35'),
(192, 54, 4, 3, 42, '2026-09-19 06:36:35'),
(193, 54, 4, 4, 56, '2026-09-19 06:36:35'),
(194, 55, 4, 3, 101, '2026-09-19 06:36:35'),
(195, 55, 2, 1, 40, '2026-09-19 06:36:35'),
(196, 55, 2, 3, 59, '2026-09-19 06:36:35'),
(197, 55, 2, 4, 93, '2026-09-19 06:36:35'),
(198, 55, 1, 1, 10, '2026-09-19 06:36:35'),
(199, 55, 1, 3, 62, '2026-09-19 06:36:35'),
(200, 55, 1, 4, 69, '2026-09-19 06:36:35'),
(201, 55, 7, 1, 71, '2026-09-19 06:36:35'),
(202, 55, 7, 3, 100, '2026-09-19 06:36:35'),
(203, 56, 5, 5, 76, '2026-09-19 06:36:35'),
(204, 56, 5, 3, 67, '2026-09-19 06:36:35'),
(205, 56, 5, 1, 81, '2026-09-19 06:36:35'),
(206, 56, 3, 5, 25, '2026-09-19 06:36:35'),
(207, 56, 3, 3, 76, '2026-09-19 06:36:35'),
(208, 56, 4, 5, 15, '2026-09-19 06:36:35'),
(209, 56, 4, 3, 72, '2026-09-19 06:36:35'),
(210, 56, 4, 1, 107, '2026-09-19 06:36:35'),
(211, 56, 7, 5, 29, '2026-09-19 06:36:35'),
(212, 56, 7, 3, 83, '2026-09-19 06:36:35'),
(213, 56, 7, 1, 87, '2026-09-19 06:36:35'),
(214, 57, 7, 3, 98, '2026-09-19 06:36:35'),
(215, 57, 2, 3, 22, '2026-09-19 06:36:35'),
(216, 57, 2, 5, 104, '2026-09-19 06:36:35'),
(217, 57, 6, 3, 93, '2026-09-19 06:36:35'),
(218, 57, 6, 5, 21, '2026-09-19 06:36:35'),
(219, 58, 1, 4, 50, '2026-09-19 06:36:35'),
(220, 58, 4, 5, 28, '2026-09-19 06:36:35'),
(221, 58, 6, 4, 80, '2026-09-19 06:36:35'),
(222, 58, 5, 5, 22, '2026-09-19 06:36:35'),
(223, 58, 5, 4, 59, '2026-09-19 06:36:35'),
(224, 59, 3, 3, 101, '2026-09-19 06:36:35'),
(225, 59, 5, 1, 23, '2026-09-19 06:36:35'),
(226, 59, 6, 3, 85, '2026-09-19 06:36:35'),
(227, 59, 7, 3, 58, '2026-09-19 06:36:35'),
(228, 59, 7, 1, 25, '2026-09-19 06:36:35'),
(229, 60, 2, 4, 53, '2026-09-19 06:36:35'),
(230, 60, 2, 5, 45, '2026-09-19 06:36:35'),
(231, 60, 6, 2, 60, '2026-09-19 06:36:35'),
(232, 60, 6, 4, 111, '2026-09-19 06:36:35'),
(233, 60, 6, 5, 59, '2026-09-19 06:36:35'),
(234, 60, 1, 2, 23, '2026-09-19 06:36:35'),
(235, 60, 1, 4, 52, '2026-09-19 06:36:35'),
(236, 60, 1, 5, 64, '2026-09-19 06:36:35'),
(237, 60, 3, 2, 89, '2026-09-19 06:36:35'),
(238, 60, 3, 4, 19, '2026-09-19 06:36:35'),
(239, 60, 3, 5, 60, '2026-09-19 06:36:35'),
(240, 61, 5, 4, 72, '2026-09-19 06:36:35'),
(241, 61, 5, 1, 19, '2026-09-19 06:36:35'),
(242, 61, 4, 4, 29, '2026-09-19 06:36:35'),
(243, 61, 4, 1, 19, '2026-09-19 06:36:35'),
(244, 61, 4, 3, 92, '2026-09-19 06:36:35'),
(245, 61, 1, 4, 97, '2026-09-19 06:36:35'),
(246, 61, 1, 1, 105, '2026-09-19 06:36:35'),
(247, 61, 1, 3, 98, '2026-09-19 06:36:35'),
(248, 62, 7, 3, 74, '2026-09-19 06:36:35'),
(249, 62, 3, 5, 37, '2026-09-19 06:36:35'),
(250, 62, 3, 3, 33, '2026-09-19 06:36:35'),
(251, 62, 3, 2, 44, '2026-09-19 06:36:35'),
(252, 62, 5, 5, 11, '2026-09-19 06:36:35'),
(253, 62, 5, 3, 29, '2026-09-19 06:36:35'),
(254, 62, 5, 2, 102, '2026-09-19 06:36:35'),
(255, 62, 1, 5, 37, '2026-09-19 06:36:35'),
(256, 62, 1, 3, 115, '2026-09-19 06:36:35'),
(257, 62, 1, 2, 70, '2026-09-19 06:36:35'),
(258, 63, 1, 3, 35, '2026-09-19 06:36:35'),
(259, 63, 1, 4, 15, '2026-09-19 06:36:35'),
(260, 63, 3, 3, 64, '2026-09-19 06:36:35'),
(261, 63, 3, 4, 82, '2026-09-19 06:36:35'),
(262, 63, 2, 3, 109, '2026-09-19 06:36:35'),
(263, 63, 2, 4, 80, '2026-09-19 06:36:35'),
(264, 64, 1, 1, 87, '2026-09-19 06:36:35'),
(265, 64, 1, 4, 97, '2026-09-19 06:36:35'),
(266, 64, 4, 1, 65, '2026-09-19 06:36:35'),
(267, 64, 4, 4, 28, '2026-09-19 06:36:35'),
(268, 64, 7, 4, 39, '2026-09-19 06:36:35'),
(269, 64, 5, 1, 61, '2026-09-19 06:36:35'),
(270, 64, 5, 4, 22, '2026-09-19 06:36:35'),
(271, 65, 7, 3, 59, '2026-09-19 06:36:35'),
(272, 65, 7, 1, 71, '2026-09-19 06:36:35'),
(273, 65, 2, 3, 64, '2026-09-19 06:36:35'),
(274, 65, 2, 1, 45, '2026-09-19 06:36:35'),
(275, 65, 5, 3, 35, '2026-09-19 06:36:35'),
(276, 65, 5, 1, 38, '2026-09-19 06:36:35'),
(277, 66, 1, 1, 32, '2026-09-19 06:36:35'),
(278, 66, 1, 2, 99, '2026-09-19 06:36:35'),
(279, 66, 1, 5, 107, '2026-09-19 06:36:35'),
(280, 66, 4, 1, 90, '2026-09-19 06:36:35'),
(281, 66, 4, 2, 85, '2026-09-19 06:36:35'),
(282, 66, 5, 1, 75, '2026-09-19 06:36:35'),
(283, 66, 5, 2, 62, '2026-09-19 06:36:35'),
(284, 66, 5, 5, 106, '2026-09-19 06:36:35');

-- --------------------------------------------------------

--
-- Table structure for table `vaitro`
--

CREATE TABLE `vaitro` (
  `vaitro_id` int(10) NOT NULL COMMENT 'ID vai trò',
  `vaitro_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `vaitro`
--

INSERT INTO `vaitro` (`vaitro_id`, `vaitro_name`) VALUES
(1, 'Admin'),
(2, 'User'),
(3, 'Nhân Viên'),
(4, 'Nhân Viên Kho');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `lk_cart_kh` (`kh_id`);

--
-- Indexes for table `cart_chitiet`
--
ALTER TABLE `cart_chitiet`
  ADD PRIMARY KEY (`cart_chitiet_id`),
  ADD UNIQUE KEY `uk_cart_variant` (`cart_id`,`ctiet_pro_id`),
  ADD KEY `lk_chitetcart_cart` (`cart_id`),
  ADD KEY `lk_cartchitiet_variant` (`ctiet_pro_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cate_id`);

--
-- Indexes for table `chi_tiet_nhom_quyen`
--
ALTER TABLE `chi_tiet_nhom_quyen`
  ADD PRIMARY KEY (`role_id`,`permission_id`),
  ADD KEY `permission_id` (`permission_id`);

--
-- Indexes for table `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`color_id`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`kh_id`),
  ADD KEY `lk_kh_vaitro` (`vaitro_id`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`ncc_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `lk_order_khs` (`kh_id`);

--
-- Indexes for table `order_chitiet`
--
ALTER TABLE `order_chitiet`
  ADD PRIMARY KEY (`order_chitiet_id`),
  ADD KEY `lk_orderchitiet_order` (`order_id`),
  ADD KEY `lk_orderchitiet_color` (`color_id`),
  ADD KEY `lk_orderchitiet_size` (`size_id`),
  ADD KEY `lk_orderchitiet_pro` (`pro_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`pro_id`),
  ADD KEY `cate_id` (`cate_id`),
  ADD KEY `ncc_id` (`ncc_id`);

--
-- Indexes for table `products_favourite`
--
ALTER TABLE `products_favourite`
  ADD PRIMARY KEY (`pro_favourite_id`),
  ADD KEY `FK_kh` (`kh_id`),
  ADD KEY `Fk_pro` (`pro_id`);

--
-- Indexes for table `pro_chitiet`
--
ALTER TABLE `pro_chitiet`
  ADD PRIMARY KEY (`ctiet_pro_id`),
  ADD UNIQUE KEY `uq_pro_sku` (`sku`),
  ADD UNIQUE KEY `uq_pro_color_size` (`pro_id`,`color_id`,`size_id`),
  ADD UNIQUE KEY `uk_pro_chitiet_sku` (`sku`),
  ADD UNIQUE KEY `uk_pro_color_size` (`pro_id`,`color_id`,`size_id`),
  ADD KEY `lk_pro_color` (`color_id`),
  ADD KEY `lk_pro_size` (`size_id`);

--
-- Indexes for table `ql_nhom_quyen`
--
ALTER TABLE `ql_nhom_quyen`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`permission_id`);

--
-- Indexes for table `size`
--
ALTER TABLE `size`
  ADD PRIMARY KEY (`size_id`);

--
-- Indexes for table `thuong_hieu`
--
ALTER TABLE `thuong_hieu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ton_kho`
--
ALTER TABLE `ton_kho`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_kho` (`pro_id`,`color_id`,`size_id`),
  ADD KEY `color_id` (`color_id`),
  ADD KEY `size_id` (`size_id`);

--
-- Indexes for table `vaitro`
--
ALTER TABLE `vaitro`
  ADD PRIMARY KEY (`vaitro_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID giỏ hàng', AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `cart_chitiet`
--
ALTER TABLE `cart_chitiet`
  MODIFY `cart_chitiet_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID chi tiết giỏ hàng', AUTO_INCREMENT=87;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `cate_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `color`
--
ALTER TABLE `color`
  MODIFY `color_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID màu', AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `kh_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `ncc_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID nhà cung cấp', AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID hóa đơn', AUTO_INCREMENT=420;

--
-- AUTO_INCREMENT for table `order_chitiet`
--
ALTER TABLE `order_chitiet`
  MODIFY `order_chitiet_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID chi tiết hóa đơn', AUTO_INCREMENT=852;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `pro_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `products_favourite`
--
ALTER TABLE `products_favourite`
  MODIFY `pro_favourite_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=132;

--
-- AUTO_INCREMENT for table `pro_chitiet`
--
ALTER TABLE `pro_chitiet`
  MODIFY `ctiet_pro_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID chi tiết sản phẩm', AUTO_INCREMENT=159;

--
-- AUTO_INCREMENT for table `ql_nhom_quyen`
--
ALTER TABLE `ql_nhom_quyen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `size`
--
ALTER TABLE `size`
  MODIFY `size_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID size', AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `thuong_hieu`
--
ALTER TABLE `thuong_hieu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `ton_kho`
--
ALTER TABLE `ton_kho`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=383;

--
-- AUTO_INCREMENT for table `vaitro`
--
ALTER TABLE `vaitro`
  MODIFY `vaitro_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID vai trò', AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `lk_cart_kh` FOREIGN KEY (`kh_id`) REFERENCES `khachhang` (`kh_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cart_chitiet`
--
ALTER TABLE `cart_chitiet`
  ADD CONSTRAINT `fk_cartitem_variant` FOREIGN KEY (`ctiet_pro_id`) REFERENCES `pro_chitiet` (`ctiet_pro_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lk_chitetcart_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chi_tiet_nhom_quyen`
--
ALTER TABLE `chi_tiet_nhom_quyen`
  ADD CONSTRAINT `fk_ctnq_quyen` FOREIGN KEY (`permission_id`) REFERENCES `quyen` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ctnq_vaitro` FOREIGN KEY (`role_id`) REFERENCES `vaitro` (`vaitro_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD CONSTRAINT `lk_kh_vaitro` FOREIGN KEY (`vaitro_id`) REFERENCES `vaitro` (`vaitro_id`) ON UPDATE CASCADE;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `lk_order_khs` FOREIGN KEY (`kh_id`) REFERENCES `khachhang` (`kh_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `order_chitiet`
--
ALTER TABLE `order_chitiet`
  ADD CONSTRAINT `lk_orderchitiet_color` FOREIGN KEY (`color_id`) REFERENCES `color` (`color_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lk_orderchitiet_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lk_orderchitiet_pro` FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lk_orderchitiet_size` FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_products_category` FOREIGN KEY (`cate_id`) REFERENCES `category` (`cate_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_products_nhacungcap` FOREIGN KEY (`ncc_id`) REFERENCES `nhacungcap` (`ncc_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products_favourite`
--
ALTER TABLE `products_favourite`
  ADD CONSTRAINT `fk_fav_kh` FOREIGN KEY (`kh_id`) REFERENCES `khachhang` (`kh_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_fav_pro` FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pro_chitiet`
--
ALTER TABLE `pro_chitiet`
  ADD CONSTRAINT `lk_pro_color` FOREIGN KEY (`color_id`) REFERENCES `color` (`color_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lk_pro_size` FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lk_proctiet_pro` FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ton_kho`
--
ALTER TABLE `ton_kho`
  ADD CONSTRAINT `fk_tonkho_color` FOREIGN KEY (`color_id`) REFERENCES `color` (`color_id`),
  ADD CONSTRAINT `fk_tonkho_pro` FOREIGN KEY (`pro_id`) REFERENCES `products` (`pro_id`),
  ADD CONSTRAINT `fk_tonkho_size` FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
