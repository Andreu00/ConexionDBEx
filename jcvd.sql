-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-11-2023 a las 18:12:56
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `jcvd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuegos`
--

CREATE TABLE `videojuegos` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Genero` varchar(50) DEFAULT NULL,
  `FechaLanzamiento` date DEFAULT NULL,
  `Compañia` varchar(50) DEFAULT NULL,
  `Precio` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `videojuegos`
--

INSERT INTO `videojuegos` (`Id`, `Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) VALUES
(1, 'Valorant', 'Shooter', '2023-11-20', 'Riot', 0.00),
(2, 'League of Legends', 'MOBA', '2023-11-20', 'Riot', 0.00),
(3, 'Minecraft', 'Mundo Abierto', '2023-11-20', 'Mojang', 20.00),
(4, 'Fifa23', 'Futbol', '2023-11-20', 'EASport', 60.00),
(5, 'God of War', 'Accion', '2023-11-20', 'Santa Monica Studio', 60.00),
(6, 'Call Of Duty Black Ops 2', 'Guerra', '2023-11-11', 'Activision', 70.00),
(7, 'Tetris', 'Piezas', '2020-11-21', 'Nintendo', 100.00),
(17, 'RaduGeta', 'Robos', '2023-11-04', 'RaduManitas', 100.00),
(18, 'Valheim', 'Survival', '2023-04-23', 'Steam', 12.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
