CREATE DATABASE  IF NOT EXISTS `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `biblioteca`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: biblioteca
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libros` (
  `id_libro` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) NOT NULL,
  `autor` varchar(150) NOT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `anio` int DEFAULT NULL,
  `editorial` varchar(150) DEFAULT NULL,
  `descripcion` text,
  `cantidad` int DEFAULT '1',
  PRIMARY KEY (`id_libro`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1,'El Principito','Antoine de Saint-Exupéry','Infantil',1943,'Reynal & Hitchcock','Un clásico',7),(4,'Cien años de soledad','Gabriel García Márquez','Ficción',1967,'Editorial Sudamericana','Recien impreso',8),(7,'Rayuela','Julio Cortázar','Novela',1963,'Editorial Sudamericana','Novela innovadora de estructura no lineal',3),(8,'Pedro Páramo','Juan Rulfo','Novela',1955,'Fondo de Cultura Económica','Clásico de la literatura mexicana',2),(9,'La ciudad y los perros','Mario Vargas Llosa','Novela',1963,'Seix Barral','Crítica social ambientada en un colegio militar',4),(10,'Los juegos del hambre','Suzanne Collins','Juvenil',2008,'Scholastic Press','Distopía juvenil sobre supervivencia y poder',5),(11,'Orgullo y prejuicio','Jane Austen','Romance',1813,'T. Egerton','Historia de amor y crítica social inglesa',3),(12,'El Hobbit','J.R.R. Tolkien','Fantasía',1937,'George Allen & Unwin','Aventura fantástica previa a El Señor de los Anillos',6),(13,'Crónica de una muerte anunciada','Gabriel García Márquez','Novela',1981,'Editorial Oveja Negra','Relato sobre un asesinato anunciado en un pueblo',4);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `id_libro` int NOT NULL,
  `id_usuario` int NOT NULL,
  `fecha_prestamo` date NOT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  `fecha_devuelto` date DEFAULT NULL,
  `observaciones` text,
  PRIMARY KEY (`id_prestamo`),
  KEY `fk_prestamo_libro` (`id_libro`),
  KEY `fk_prestamo_usuario` (`id_usuario`),
  CONSTRAINT `fk_prestamo_libro` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_prestamo_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (4,1,1,'2025-12-01','2025-12-16',NULL,'Entrega en 15 días'),(5,1,1,'2025-12-01',NULL,NULL,'Libro nuevo '),(10,4,2,'2025-12-01',NULL,NULL,'Prueba real');
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `correo` varchar(150) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Josue Alexader','xan@m.com','13131'),(2,'Gabriela ','xasa@hmai.com','61736713'),(4,'Ana Martínez','ana.martinez@example.com','7012-3456'),(5,'Carlos López','carlos.lopez@example.com','7890-1234'),(6,'María Gómez','maria.gomez@example.com','7456-7890'),(9,'Douglas','prueba1@gmail.com','124314');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-02 11:50:01
