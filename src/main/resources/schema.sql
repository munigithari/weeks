CREATE TABLE writer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    bio VARCHAR(255)
);

CREATE TABLE magazine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    publicationDate VARCHAR(255)
);

CREATE TABLE writer_magazine (
    writerId INT,
    magazineId INT,
    PRIMARY KEY(writerId, magazineId),
    FOREIGN KEY(writerId) REFERENCES writer(id),
    FOREIGN KEY(magazineId) REFERENCES magazine(id)
);