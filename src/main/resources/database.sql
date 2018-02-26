-- Table roles:
CREATE TABLE roles(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(25) NOT NULL
) ENGINE InnoDB;

-- Table users :
CREATE TABLE users (
  id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  email VARCHAR(25) NOT NULL ,
  password VARCHAR(255) NOT NULL,
  enabled INT DEFAULT 1,
  role INT NOT NULL DEFAULT 2,

  FOREIGN KEY (role) REFERENCES roles(id)
) ENGINE InnoDB;


-- Insert data

INSERT INTO roles VALUES (1, 'admin');
INSERT INTO roles VALUES (2, 'user');

-- login: admin@mail.com | password: admin
INSERT INTO users VALUES (1, 'admin@mail.com', '$2a$10$s3RMRXkgxIV6cm8sonTWd.lQV6j7onrCLfl63vEXx/vroNSLOODry',1,1);



