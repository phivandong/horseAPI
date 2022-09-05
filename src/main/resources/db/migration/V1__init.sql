CREATE TABLE account (
  id INT AUTO_INCREMENT NOT NULL,
   username VARCHAR(50) NOT NULL,
   password VARCHAR(150) NOT NULL,
   status BIT(1) NOT NULL,
   CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE horse_account (
  id INT AUTO_INCREMENT NOT NULL,
   account_id INT NOT NULL,
   horse_id INT NOT NULL,
   archive BIT(1) DEFAULT 1 NOT NULL,
   CONSTRAINT pk_horse_account PRIMARY KEY (id)
);

ALTER TABLE account ADD CONSTRAINT uc_account_username UNIQUE (username);

CREATE TABLE horse (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   foaled datetime NOT NULL,
   CONSTRAINT pk_horse PRIMARY KEY (id)
);

CREATE TABLE trainer (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NULL,
   account_id INT NOT NULL,
   CONSTRAINT pk_trainer PRIMARY KEY (id)
);

ALTER TABLE trainer ADD CONSTRAINT FK_TRAINER_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);