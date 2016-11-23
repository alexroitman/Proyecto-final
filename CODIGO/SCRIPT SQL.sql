
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 23-11-2016 a las 16:03:16
-- Versión del servidor: 10.0.22-MariaDB
-- Versión de PHP: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `u241352082_bdfin`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Lineas`
--

CREATE TABLE IF NOT EXISTS `Lineas` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Numero` int(10) NOT NULL,
  `Imagen` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Numero`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=35 ;

--
-- Volcado de datos para la tabla `Lineas`
--

INSERT INTO `Lineas` (`Id`, `Numero`, `Imagen`) VALUES
(20, 102, 'a102'),
(19, 99, 'a99'),
(18, 92, 'a92'),
(17, 90, 'a90'),
(16, 84, 'a84'),
(15, 76, 'a76'),
(14, 71, 'a71'),
(13, 63, 'a63'),
(12, 60, 'a60'),
(11, 55, 'a55'),
(10, 53, 'a53'),
(9, 36, 'a36'),
(8, 34, 'a34'),
(7, 29, 'a29'),
(6, 28, 'a28'),
(5, 24, 'a24'),
(4, 17, 'a17'),
(3, 15, 'a15'),
(2, 12, 'a12'),
(21, 106, 'a106'),
(22, 109, 'a109'),
(23, 110, 'a110'),
(24, 111, 'a111'),
(25, 124, 'a124'),
(26, 132, 'a132'),
(27, 133, 'a133'),
(28, 134, 'a134'),
(29, 140, 'a140'),
(30, 141, 'a141'),
(31, 160, 'a160'),
(32, 166, 'a166'),
(33, 181, 'a181'),
(34, 194, 'a194'),
(1, 9, 'a9');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Paradas`
--

CREATE TABLE IF NOT EXISTS `Paradas` (
  `IdParada` int(11) NOT NULL AUTO_INCREMENT,
  `LatLong` varchar(50) COLLATE utf8_bin NOT NULL,
  `IdLinea` int(11) NOT NULL,
  PRIMARY KEY (`IdParada`),
  KEY `IdLinea` (`IdLinea`),
  KEY `IdLinea_2` (`IdLinea`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=19 ;

--
-- Volcado de datos para la tabla `Paradas`
--

INSERT INTO `Paradas` (`IdParada`, `LatLong`, `IdLinea`) VALUES
(1, '-34.608999, -58.436086', 15),
(2, '-34.608185, -58.433395', 15),
(3, '-34.606622, -58.432690', 15),
(4, '-34.605011, -58.433711', 15),
(5, '-34.604313, -58.435956', 15),
(6, '-34.603883, -58.438549', 15),
(7, '-34.602875, -58.440848', 15),
(8, '-34.602285, -58.442465', 15),
(9, '-34.600079, -58.439060', 15),
(10, '-34.598554, -58.436842', 15),
(11, '-34.596644, -58.433528', 15),
(12, '-34.594436, -58.430202', 15),
(13, '-34.590977, -58.425078', 15),
(14, '-34.589847, -58.423437', 15),
(15, '-34.587839, -58.420228', 15),
(16, '-34.586541, -58.418115', 15),
(17, '-34.61021802002395,-58.42940093646154', 9),
(18, '-34.609083, -58.430501', 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Recorridos`
--

CREATE TABLE IF NOT EXISTS `Recorridos` (
  `IdRecorrido` int(11) NOT NULL AUTO_INCREMENT,
  `LatLong` text COLLATE utf8_unicode_ci NOT NULL,
  `IdLinea` int(11) NOT NULL,
  PRIMARY KEY (`IdRecorrido`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=53 ;

--
-- Volcado de datos para la tabla `Recorridos`
--

INSERT INTO `Recorridos` (`IdRecorrido`, `LatLong`, `IdLinea`) VALUES
(1, '-34.417003, -58.698964', 15),
(2, '-34.413476, -58.701770', 15),
(3, '-34.411086, -58.713068 ', 15),
(4, '-34.415583, -58.721340 ', 15),
(5, '-34.416565, -58.722123 ', 15),
(6, '-34.417857, -58.722327 ', 15),
(7, '-34.427831, -58.714720', 15),
(8, '-34.433539, -58.712735 ', 15),
(9, '-34.441136, -58.704767 ', 15),
(10, ' -34.463970, -58.687000 ', 15),
(11, '-34.468985, -58.681861', 15),
(12, '-34.470984, -58.677870', 15),
(13, '-34.485895, -58.609992', 15),
(14, '-34.488716, -58.572805', 15),
(15, '-34.494765, -58.556324', 15),
(16, '-34.498541, -58.550820 ', 15),
(17, '-34.509381, -58.527206', 15),
(18, '-34.545777, -58.494147', 15),
(19, '-34.546148, -58.493149', 15),
(20, '-34.535130, -58.467203', 15),
(21, '-34.555744, -58.448517', 15),
(22, ' -34.557790, -58.452020 ', 15),
(23, '-34.557816, -58.452020', 15),
(24, '-34.558408, -58.450625', 15),
(25, '-34.561492, -58.444177', 15),
(26, '-34.562000, -58.444622', 15),
(27, '-34.567646, -58.437895', 15),
(28, '-34.577451, -58.428378', 15),
(29, '-34.578237, -58.425803', 15),
(30, '-34.580799, -58.421779', 15),
(31, '-34.580720, -58.421061', 15),
(32, '-34.581320, -58.420277', 15),
(33, '-34.581753, -58.420395', 15),
(34, '-34.582062, -58.420374', 15),
(35, '-34.585068, -58.415933', 15),
(36, '-34.602255, -58.442583', 15),
(37, '-34.604516, -58.437144', 15),
(38, '-34.604410, -58.434912', 15),
(39, '-34.605593, -58.433110', 15),
(40, '-34.607218, -58.432755', 15),
(41, '-34.608269, -58.433839', 15),
(42, '-34.608605, -58.434719', 15),
(43, '-34.608799, -58.436146', 15),
(44, '-34.616715, -58.432888', 15),
(45, '-34.616729, -58.432910', 15),
(46, '-34.615204, -58.429452', 15),
(47, '-34.642780, -58.422840', 15),
(48, '-34.646337, -58.419890', 15),
(49, '-34.646169, -58.416210', 15),
(50, '-34.646999, -58.415309', 15),
(51, '-34.660776, -58.416757', 15),
(52, '-34.409722, -58.685378 ', 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Subidas`
--

CREATE TABLE IF NOT EXISTS `Subidas` (
  `IdSubida` int(11) NOT NULL AUTO_INCREMENT,
  `LatLong` text COLLATE utf8_unicode_ci NOT NULL,
  `UltimaUbicacion` text COLLATE utf8_unicode_ci NOT NULL,
  `IdLinea` int(11) NOT NULL,
  `Horasubida` time NOT NULL,
  `Calle` text COLLATE utf8_unicode_ci NOT NULL,
  `Condicion` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdSubida`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=18 ;

--
-- Volcado de datos para la tabla `Subidas`
--

INSERT INTO `Subidas` (`IdSubida`, `LatLong`, `UltimaUbicacion`, `IdLinea`, `Horasubida`, `Calle`, `Condicion`) VALUES
(1, '-34.602128, -58.442499', '-34.598599,-58.4401209', 15, '22:01:00', 'Av. Corrientes 5337-5353, C1414AJG CABA, Argentina', 'BAJADO'),
(2, '-34.609474, -58.435927', '-34.5985467,-58.44021', 15, '23:52:00', 'Av. Corrientes 5353-5365, C1414AJG CABA, Argentina', 'BAJADO'),
(3, '-34.5985467,-58.44021', '', 12, '00:01:00', 'Malabia 402-416, C1414DLJ CABA, Argentina', 'BAJADO'),
(16, '-34.6099088,-58.4290622', '-34.6099088,-58.4290622', 15, '12:40:00', 'Yatay 228-250, C1184ADD CABA, Argentina', 'BAJADO'),
(17, '-34.6099088,-58.4290622', '-34.6099088,-58.4290622', 15, '13:00:00', 'Yatay 228-250, C1184ADD CABA, Argentina', 'BAJADO');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
