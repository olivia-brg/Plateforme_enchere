DROP TABLE IF EXISTS bids;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS auctionUsers;
DROP TABLE IF EXISTS deliveryAddress;
DROP TABLE IF EXISTS categories;


CREATE TABLE auctionUsers (
                              id INT IDENTITY NOT NULL,
                              userName VARCHAR(25) NOT NULL,
                              firstName VARCHAR(25) NOT NULL,
                              lastName VARCHAR(50) NOT NULL,
                              email VARCHAR(60) NOT NULL,
                              phoneNumber VARCHAR(12),
                              street VARCHAR(100),
                              city VARCHAR(50),
                              postalCode VARCHAR(6),
                              password VARCHAR(100) NOT NULL,
                              credit NUMERIC(15) NOT NULL,
                              Role VARCHAR(5) DEFAULT 'USER' NOT NULL,
							  isActive BIT DEFAULT 1 NOT NULL,
                              CONSTRAINT pk_auctionUser PRIMARY KEY(id)

);

CREATE TABLE deliveryAddress (
                                 id INT IDENTITY NOT NULL,
                                 street VARCHAR(100),
                                 postalCode VARCHAR(6),
                                 city VARCHAR(50),
                                 CONSTRAINT pk_deliveryLocation PRIMARY KEY(id),

);

CREATE TABLE categories (
                            id INT IDENTITY NOT NULL,
                            name VARCHAR(30) NOT NULL,
                            CONSTRAINT pk_Category PRIMARY KEY(id),
);

CREATE TABLE articles (
                          id INT IDENTITY NOT NULL,
                          deliveryAddressId INT NOT NULL,
                          userId INT NOT NULL,
                          categoryId INT NOT NULL,
                          name VARCHAR(50),
                          description VARCHAR(200),
                          auctionStartDate DATETIME2,
                          auctionEndDate DATETIME2,
                          startingPrice NUMERIC(10),
                          soldPrice NUMERIC(10),
                          imageURL VARCHAR(100),
                          isOnSale bit,
                          CONSTRAINT pk_article PRIMARY KEY(id),
                          CONSTRAINT fk_article_user FOREIGN KEY(userId) REFERENCES auctionUsers(id),
                          CONSTRAINT fk_article_category FOREIGN KEY(categoryId) REFERENCES categories(id),
                          CONSTRAINT fk_article_delivery FOREIGN KEY(deliveryAddressId) REFERENCES deliveryAddress(id) ON DELETE CASCADE,


);

CREATE TABLE bids (
                      bidId INT IDENTITY ,
                      bidDate DATETIME2,
                      bidAmount NUMERIC(10),
                      userId int,
                      articleId int,
                      CONSTRAINT pk_bid PRIMARY KEY (bidId),
                      CONSTRAINT fk_bid_user FOREIGN KEY(userId) REFERENCES auctionUsers(id),
                      CONSTRAINT fk_bid_article FOREIGN KEY(articleId) REFERENCES articles(id),
);





DELETE FROM auctionUsers WHERE id > 0;

INSERT INTO auctionUsers(userName,firstName,LastName,email,phoneNumber,street,city,postalCode,password,credit,Role)
VALUES('makusu','maxime','jeannin','max.jeannin@hotmail.com','0245856335','boulevard du massacre','Nantes','44100','$2a$10$dm804LiUruKfdoS5BXFZLeIzIPnCTDWgXBwQb2Z4RM9t6cFsMNjbS',100,'ADMIN');

INSERT INTO auctionUsers(userName,firstName,LastName,email,phoneNumber,street,city,postalCode,password,credit)
VALUES('HappyBeer','pierrick','rouxel','pierrick.rouxel@hotmail.com','0245858435','rue du houblon','Nantes','44100','$2a$10$fsvFQmKlShqro1GXRh9/9eeG/9EMjo/xc1NfYMlginstKJ93Zpc3O',10000);


INSERT INTO categories(name) VALUES('bière')
INSERT INTO categories(name) VALUES('Informatique')
INSERT INTO categories(name) VALUES('Ameublement')
INSERT INTO categories(name) VALUES('Vêtement')
INSERT INTO categories(name) VALUES('Sports & loisirs')

INSERT INTO deliveryAddress(street, postalCode, city) VALUES ('boulevard du massacre','44100','Nantes');

INSERT INTO articles(userID,deliveryAddressId,categoryId,name,description,auctionStartDate,auctionEndDate,startingPrice,isOnSale,imageURL)
VALUES(1,1,1,'Fût de Cantillon','Bière, assemblage de lambic','2025-07-02 10:00:00','2025-07-09 10:00:00',450,1,'/img/cantillon-gueuze-bio_800x-191546988.jpg');

INSERT INTO articles(userID,deliveryAddressId,categoryId,name,description,auctionStartDate,auctionEndDate,startingPrice,isOnSale,imageURL)
VALUES(1,1,1,'Fût de Dremwell','Bière bretonne, se vend bien chez les chauvins','2025-08-02 10:00:00','2025-08-09 10:00:00',400,1,'/img/3760010132661_A1L1_5193220_S12-770217217.png');

INSERT INTO bids(bidDate, bidAmount, userId, articleId) VALUES('2025-07-04 10:58',1200.1,1,2)
INSERT INTO bids(bidDate, bidAmount, userId, articleId) VALUES('2025-07-08 11:58',100000,2,2)
