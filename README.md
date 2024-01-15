## Organizer

 Jedná se o aplikaci psanou za použití frameworku JavaFX, kde bylo hlavním cílem programování složitějších komponent
 jako jsou TableView a TreeView. Tato aplikace je má za úkol ukládat základní údaje o zaměstnancích a pacientech nemocnice.

Před spuštěním aplikace je nutné mít v počítači nainstalován JRE (Java Runtime Environment);

poté použít databázi MySQL - 
	- doporučuji použít XAMPP a spustit v něm server Apache a databázi MySQL
	- poté na stránce 'http://localhost/phpmyadmin' spustit tento SQL skript:

---------------------------------------------------------------------------------	
	create database uursp1;
	use uursp1;

	CREATE TABLE `uzivatel` (
  	`id` bigint primary key auto_increment,
  	`username` varchar(50) NOT NULL,
  	`password` varchar(60) NOT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

	CREATE TABLE `zamestnanec` (
  	`id` bigint primary key auto_increment,	
  	`jmeno` varchar(30) NOT NULL,
  	`prijmeni` varchar(30) NOT NULL,
  	`email` varchar(50) NOT NULL,
  	`datum` varchar(30) NOT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
---------------------------------------------------------------------------------

Pro spuštění aplikace je nutné jít do adresáře
	
	out\artifacts\UUR_SP1_jar

, v něm následně spustit příkazovou řádku a použít skript:

	java --module-path "cesta k javafx knihovně (je uložena ve složce knihovna\javafx-17)" --add-modules javafx.controls,javafx.fxml -jar UUR-SP1.jar
