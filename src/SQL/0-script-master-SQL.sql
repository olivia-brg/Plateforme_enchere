-- 🔁 RESET COMPLET
DROP TABLE IF EXISTS bids;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS auctionUsers;
DROP TABLE IF EXISTS deliveryAddress;
DROP TABLE IF EXISTS categories;

-- 👤 UTILISATEURS
CREATE TABLE auctionUsers
(
    id          INT IDENTITY PRIMARY KEY,
    userName    VARCHAR(25)               NOT NULL,
    firstName   VARCHAR(25)               NOT NULL,
    lastName    VARCHAR(50)               NOT NULL,
    email       VARCHAR(60)               NOT NULL,
    phoneNumber VARCHAR(12),
    street      VARCHAR(100),
    city        VARCHAR(50),
    postalCode  VARCHAR(6),
    password    VARCHAR(100)              NOT NULL,
    credit      NUMERIC(15)               NOT NULL,
    Role        VARCHAR(5) DEFAULT 'USER' NOT NULL,
    isActive    BIT        DEFAULT 1      NOT NULL
);

-- 🏠 ADRESSES DE LIVRAISON
CREATE TABLE deliveryAddress
(
    id         INT IDENTITY PRIMARY KEY,
    street     VARCHAR(100),
    postalCode VARCHAR(6),
    city       VARCHAR(50)
);

-- 📁 CATÉGORIES
CREATE TABLE categories
(
    id   INT IDENTITY PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

-- 📦 ARTICLES
CREATE TABLE articles
(
    id                INT IDENTITY PRIMARY KEY,
    deliveryAddressId INT NOT NULL,
    userId            INT NOT NULL,
    categoryId        INT NOT NULL,
    name              VARCHAR(50),
    description       VARCHAR(200),
    auctionStartDate  DATETIME2,
    auctionEndDate    DATETIME2,
    startingPrice     NUMERIC(10),
    soldPrice         NUMERIC(10),
    imageURL          VARCHAR(110),
    isOnSale          BIT,
    CONSTRAINT fk_article_user FOREIGN KEY (userId) REFERENCES auctionUsers (id),
    CONSTRAINT fk_article_category FOREIGN KEY (categoryId) REFERENCES categories (id),
    CONSTRAINT fk_article_delivery FOREIGN KEY (deliveryAddressId) REFERENCES deliveryAddress (id) ON DELETE CASCADE
);

-- 💸 ENCHÈRES
CREATE TABLE bids
(
    bidId     INT IDENTITY PRIMARY KEY,
    bidDate   DATETIME2,
    bidAmount NUMERIC(10),
    userId    INT,
    articleId INT,
    CONSTRAINT fk_bid_user FOREIGN KEY (userId) REFERENCES auctionUsers (id),
    CONSTRAINT fk_bid_article FOREIGN KEY (articleId) REFERENCES articles (id)
);

-- 🔍 INDEXES RECOMMANDÉS
CREATE INDEX idx_articles_name ON articles (name);
CREATE INDEX idx_articles_category ON articles (categoryId);
CREATE INDEX idx_articles_user ON articles (userId);
CREATE INDEX idx_articles_start_date ON articles (auctionStartDate);
CREATE INDEX idx_articles_end_date ON articles (auctionEndDate);
CREATE INDEX idx_articles_isOnSale ON articles (isOnSale);
CREATE INDEX idx_bids_user ON bids (userId);
CREATE INDEX idx_bids_article ON bids (articleId);

-- 👥 UTILISATEURS DE TEST
INSERT INTO auctionUsers(userName, firstName, lastName, email, phoneNumber, street, city, postalCode, password, credit,
                         Role)
VALUES ('makusu', 'maxime', 'jeannin', 'max.jeannin@hotmail.com', '0245856335', 'boulevard du massacre', 'Nantes',
        '44100', '$2a$10$dm804LiUruKfdoS5BXFZLeIzIPnCTDWgXBwQb2Z4RM9t6cFsMNjbS', 100, 'ADMIN'),
       ('HappyBeer', 'pierrick', 'rouxel', 'pierrick.rouxel@hotmail.com', '0245858435', 'rue du houblon', 'Nantes',
        '44100', '$2a$10$fsvFQmKlShqro1GXRh9/9eeG/9EMjo/xc1NfYMlginstKJ93Zpc3O', 100, 'USER'),
       ('olv', 'Olivia', 'Bergaglia', 'olv@mail.com', '0245858435', 'rue de la pisse', 'Nantes',
        '44000', '$2a$10$IoSm.e1uzc9mjQq1uW6A/.T/wPqWkGNwROyNeEcFJbTATYb9LRQAq', 9999, 'ADMIN');

-- 📂 CATÉGORIES
INSERT INTO categories(name)
VALUES ('Bière'),
       ('Informatique'),
       ('Ameublement'),
       ('Vêtement'),
       ('Sports & Loisirs');

-- 📦 ADRESSES DE LIVRAISON
INSERT INTO deliveryAddress(street, postalCode, city)
VALUES ('boulevard du massacre', '44100', 'Nantes'),
       ('rue de la soif', '44000', 'Nantes'),
       ('avenue des brasseurs', '44100', 'Nantes'),
       ('impasse du malt', '44200', 'Nantes');

-- 🚀 ARTICLES DE DÉMO (x100)
DECLARE @i INT = 0;

WHILE @i < 100
    BEGIN
        INSERT INTO articles (userId, deliveryAddressId, categoryId, name, description,
                              auctionStartDate, auctionEndDate, startingPrice, soldPrice, isOnSale, imageURL)
        VALUES (1 + (@i % 3),
                1 + (@i % 4),
                1 + (@i % 5),
                CONCAT('Article numéro ', @i),
                CONCAT('Description de larticle numéro ', @i),
                DATEADD(DAY, -@i, GETDATE()),
                DATEADD(DAY, 7 - @i, GETDATE()),
                10 + (@i * 2),
                NULL,
                1,
                'https://cdn.pratico-pratiques.com/app/uploads/sites/4/2018/08/30162023/les-differentes-varietes-de-biere.jpeg');
        SET @i = @i + 1;
    END

-- 💰 ENCHÈRES DE DÉMO SUR LES 30 PREMIERS ARTICLES
DECLARE @j INT = 1;
WHILE @j <= 30
    BEGIN
        INSERT INTO bids(bidDate, bidAmount, userId, articleId)
        VALUES (DATEADD(HOUR, -@j, GETDATE()),
                100 + (@j * 10),
                1 + (@j % 2),
                @j);
        SET @j = @j + 1;
    END
