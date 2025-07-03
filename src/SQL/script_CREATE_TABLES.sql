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
                              password VARCHAR(20) NOT NULL,
                              credit NUMERIC(10,2) NOT NULL,
                              isAdmin BIT DEFAULT 0 NOT NULL,
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
                          deliveryAddressId INT NULL,
                          userId INT NOT NULL,
                          categoryId INT NOT NULL,
                          name VARCHAR(50),
                          description VARCHAR(200),
                          auctionStartDate DATETIME2,
                          auctionEndDate DATETIME2,
                          startingPrice NUMERIC(8,2),
                          soldPrice NUMERIC(8,2),
                          ImageData VARBINARY(MAX),
    --state VARCHAR DEFAULT 'saved' CONSTRAINT chk_state CHECK (state IN ('saved','in_auction','sold')),
                          isOnSale bit,
                          CONSTRAINT pk_article PRIMARY KEY(id),
                          CONSTRAINT fk_article_user FOREIGN KEY(userId) REFERENCES auctionUsers(id) ON DELETE CASCADE,
                          CONSTRAINT fk_article_category FOREIGN KEY(categoryId) REFERENCES categories(id),
                          CONSTRAINT fk_article_delivery FOREIGN KEY(deliveryAddressId) REFERENCES deliveryAddress(id) ON DELETE CASCADE,


);

CREATE TABLE bids (
                      bidId INT IDENTITY ,
                      bidDate DATETIME2,
                      bidAmount NUMERIC(5,2),
                      userId int,
                      articleId int,
                      CONSTRAINT pk_bid PRIMARY KEY (bidId),
                      CONSTRAINT fk_bid_user FOREIGN KEY(userId) REFERENCES auctionUsers(id) ON DELETE CASCADE,
                      CONSTRAINT fk_bid_article FOREIGN KEY(articleId) REFERENCES articles(id),
);






