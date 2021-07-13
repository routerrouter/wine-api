CREATE TABLE IF NOT EXISTS `loja` (
  `codigo` int NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `is_open` bit(1) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `contacto` varchar(255) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
