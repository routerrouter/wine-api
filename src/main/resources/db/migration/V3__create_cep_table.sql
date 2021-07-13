CREATE TABLE IF NOT EXISTS `cep` (
  `codigo` int NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `codigo_loja` int,
  `faixa_inicio` INT(8) ZEROFILL,
  `faixa_fim` INT(8) ZEROFILL,
  PRIMARY KEY (`codigo`),
  FOREIGN KEY (codigo_loja) REFERENCES loja(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
