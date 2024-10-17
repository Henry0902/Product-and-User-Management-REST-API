-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2020 at 10:25 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `demo`
--

-- --------------------------------------------------------

--
-- Table structure for table `spring_session`
--

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) COLLATE utf8_bin NOT NULL,
  `SESSION_ID` char(36) COLLATE utf8_bin NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `spring_session`
--

INSERT INTO `spring_session` (`PRIMARY_ID`, `SESSION_ID`, `CREATION_TIME`, `LAST_ACCESS_TIME`, `MAX_INACTIVE_INTERVAL`, `EXPIRY_TIME`, `PRINCIPAL_NAME`) VALUES
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', '4bf98beb-bd6e-464b-9dc3-7a1091a7fff1', 1592882138538, 1592900745730, 90000, 1592990745730, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `spring_session_attributes`
--

CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) COLLATE utf8_bin NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `spring_session_attributes`
--

INSERT INTO `spring_session_attributes` (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`, `ATTRIBUTE_BYTES`) VALUES
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', 'email', 0xaced00057400186368696e6876756475633230313440676d61696c2e636f6d),
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', 'fullName', 0xaced000574000161),
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', 'javax.servlet.jsp.jstl.fmt.request.charset', 0xaced00057400055554462d38),
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', 'userId', 0xaced00057372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000002),
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', 'userModuleMenus', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000037704000000037372001c636f6d2e70726f6a6563742e7461626c652e557365724d6f64756c65b95d54b4b07d84000200074a0005706c6163654c000469636f6e7400124c6a6176612f6c616e672f537472696e673b4c000269647400104c6a6176612f6c616e672f4c6f6e673b4c00046e616d6571007e00034c0008706172656e74496471007e00044c00067374617475737400134c6a6176612f6c616e672f496e74656765723b4c000375726c71007e00037870000000000000000174001e6e61762d69636f6e206661732066612d746163686f6d657465722d616c747372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000c5175e1baa36e207472e1bb8b7371007e00080000000000000000737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0009000000017400007371007e00020000000000000002707371007e000800000000000000037400144e68c3b36d206e67c6b0e1bb9d692064c3b96e6771007e000a71007e000e7400102f6e686f6d2d6e67756f692d64756e677371007e00020000000000000003707371007e0008000000000000000274000e4e67c6b0e1bb9d692064c3b96e6771007e000a71007e000e74000b2f6e67756f692d64756e6778),
('7f25219b-72da-4bf5-a70e-01de7b4c45b1', 'username', 0xaced000574000561646d696e);

-- --------------------------------------------------------

--
-- Table structure for table `user_group`
--

CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `group_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user_group`
--

INSERT INTO `user_group` (`id`, `create_by`, `create_time`, `description`, `group_name`, `status`) VALUES
(1, NULL, NULL, NULL, 'Quản trị', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_group_permission`
--

CREATE TABLE `user_group_permission` (
  `id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `module_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user_group_permission`
--

INSERT INTO `user_group_permission` (`id`, `group_id`, `module_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(5, 2, 3),
(7, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `group_id` bigint(20) NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`id`, `create_by`, `create_time`, `email`, `full_name`, `group_id`, `password`, `status`, `username`) VALUES
(2, NULL, NULL, 'chinhvuduc2014@gmail.com', 'a', 1, '7e29e1f5e672cc433af41bb28306e58', 1, 'admin'),
(5, 'admin', '2020-06-23 08:21:25', '', 'a', 1, 'ABCd1234', 0, 'admin1');

-- --------------------------------------------------------

--
-- Table structure for table `user_info_dto`
--

CREATE TABLE `user_info_dto` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `don_vi_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `group_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `ten_don_vi` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `user_module`
--

CREATE TABLE `user_module` (
  `id` bigint(20) NOT NULL,
  `icon` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `place` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user_module`
--

INSERT INTO `user_module` (`id`, `icon`, `name`, `parent_id`, `place`, `status`, `url`) VALUES
(1, 'nav-icon fas fa-tachometer-alt', 'Quản trị', 0, 1, 1, ''),
(2, NULL, 'Người dùng', 1, 3, 1, '/nguoi-dung'),
(3, NULL, 'Nhóm người dùng', 1, 2, 1, '/nhom-nguoi-dung');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `spring_session`
--
ALTER TABLE `spring_session`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  ADD KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  ADD KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`);

--
-- Indexes for table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`);

--
-- Indexes for table `user_group`
--
ALTER TABLE `user_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_group_permission`
--
ALTER TABLE `user_group_permission`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_info_dto`
--
ALTER TABLE `user_info_dto`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_module`
--
ALTER TABLE `user_module`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user_group`
--
ALTER TABLE `user_group`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user_group_permission`
--
ALTER TABLE `user_group_permission`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user_info`
--
ALTER TABLE `user_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user_module`
--
ALTER TABLE `user_module`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
