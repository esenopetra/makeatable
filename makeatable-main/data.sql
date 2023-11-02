-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 21, 2023 at 10:28 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `makeatable`
--

-- --------------------------------------------------------

--
-- Table structure for table `favourite`
--

CREATE TABLE `favourite` (
  `id` bigint(20) NOT NULL,
  `restaurant_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favourite`
--

INSERT INTO `favourite` (`id`, `restaurant_id`, `user_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `id` bigint(20) NOT NULL,
  `calories` bigint(20) DEFAULT NULL,
  `category` enum('BOTH','NON_VEG','VEG') DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `sub_category` varchar(255) DEFAULT NULL,
  `rating_id` bigint(20) DEFAULT NULL,
  `restaurant_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id`, `calories`, `category`, `description`, `image_url`, `name`, `price`, `sub_category`, `rating_id`, `restaurant_id`) VALUES
(1, 300, 'VEG', 'Delicious veggie burger', 'image_url_1.jpg', 'Veggie Burger', 5.99, 'Burgers', 1, 1),
(2, 450, 'NON_VEG', 'Tender grilled chicken', 'image_url_2.jpg', 'Grilled Chicken', 7.99, 'Grilled', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `seat_num` bigint(20) DEFAULT NULL,
  `type_room` enum('AC','NON_AC') DEFAULT NULL,
  `restaurant_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `date_time`, `seat_num`, `type_room`, `restaurant_id`, `user_id`) VALUES
(1, '2023-09-22 13:00:00.000000', 10, 'AC', 1, 1),
(2, '2023-09-23 19:30:00.000000', 5, 'NON_AC', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `orders_seq`
--

CREATE TABLE `orders_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders_seq`
--

INSERT INTO `orders_seq` (`next_val`) VALUES
(1),
(2);

-- --------------------------------------------------------

--
-- Table structure for table `order_seq`
--

CREATE TABLE `order_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_seq`
--

INSERT INTO `order_seq` (`next_val`) VALUES
(1),
(2),
(2);

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `id` bigint(20) NOT NULL,
  `rating` double DEFAULT NULL,
  `rate_num` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`id`, `rating`, `rate_num`) VALUES
(1, 4.5, 100),
(2, 3.8, 80);

-- --------------------------------------------------------

--
-- Table structure for table `rating_seq`
--

CREATE TABLE `rating_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rating_seq`
--

INSERT INTO `rating_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant`
--

CREATE TABLE `restaurant` (
  `id` bigint(20) NOT NULL,
  `close_time` time(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `food_type` enum('BOTH','NON_VEG','VEG') DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `mobile_num` bigint(20) DEFAULT NULL,
  `open_time` time(6) DEFAULT NULL,
  `seat_num` bigint(20) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `type_room` enum('AC','NON_AC') DEFAULT NULL,
  `rating_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`id`, `close_time`, `description`, `email`, `food_type`, `full_name`, `image_url`, `location`, `mobile_num`, `open_time`, `seat_num`, `status`, `type_room`, `rating_id`, `user_id`) VALUES
(1, '22:00:00.000000', 'Cozy restaurant serving a variety of cuisines.', 'restaurant1@example.com', 'BOTH', 'Tasty Bites', 'restaurant_image_1.jpg', '1234 Elm St', 9876543210, '10:00:00.000000', 50, b'1', 'AC', 1, 1),
(2, '23:00:00.000000', 'Fine dining experience with a view.', 'restaurant2@example.com', 'NON_VEG', 'Gourmet Grill', 'restaurant_image_2.jpg', '5678 Oak St', 1234567890, '11:30:00.000000', 40, b'1', 'NON_AC', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `mobile_num` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `preference` enum('BOTH','NON_VEG','VEG') DEFAULT NULL,
  `user_type` enum('ADMIN','CUSTOMER','MANAGER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `full_name`, `mobile_num`, `password`, `preference`, `user_type`) VALUES
(1, 'user1@example.com', 'John Doe', '1234567890', 'password123', 'BOTH', 'CUSTOMER'),
(2, 'user2@example.com', 'Jane Smith', '9876543210', 'password456', 'VEG', 'ADMIN');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq9s9bee4bl9nj9aawf9ihc2uo` (`restaurant_id`),
  ADD KEY `FK83lccer6s8bgj5jgjwan5eipk` (`user_id`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb8q5659i3cdoel2ppbhje7iqj` (`rating_id`),
  ADD KEY `FKm9xrxt95wwp1r2s7andom1l1c` (`restaurant_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi7hgjxhw21nei3xgpe4nnpenh` (`restaurant_id`),
  ADD KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn5lwg9k9jy54w9ddx9he3awjv` (`rating_id`),
  ADD KEY `FKsaxffw59e8gv87asawwx58hqp` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `favourite`
--
ALTER TABLE `favourite`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `favourite`
--
ALTER TABLE `favourite`
  ADD CONSTRAINT `FK83lccer6s8bgj5jgjwan5eipk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKq9s9bee4bl9nj9aawf9ihc2uo` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`);

--
-- Constraints for table `food`
--
ALTER TABLE `food`
  ADD CONSTRAINT `FKb8q5659i3cdoel2ppbhje7iqj` FOREIGN KEY (`rating_id`) REFERENCES `rating` (`id`),
  ADD CONSTRAINT `FKm9xrxt95wwp1r2s7andom1l1c` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKi7hgjxhw21nei3xgpe4nnpenh` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`);

--
-- Constraints for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD CONSTRAINT `FKn5lwg9k9jy54w9ddx9he3awjv` FOREIGN KEY (`rating_id`) REFERENCES `rating` (`id`),
  ADD CONSTRAINT `FKsaxffw59e8gv87asawwx58hqp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
