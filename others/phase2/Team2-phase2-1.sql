--part A
DROP TABLE work CASCADE CONSTRAINT;
DROP TABLE creator CASCADE CONSTRAINT;
DROP TABLE made CASCADE CONSTRAINT;
DROP TABLE sites CASCADE CONSTRAINT;
DROP TABLE keyword CASCADE CONSTRAINT;
DROP TABLE writing_info CASCADE CONSTRAINT;
DROP TABLE webtoon_info CASCADE CONSTRAINT;
DROP TABLE video_info CASCADE CONSTRAINT;


CREATE TABLE work (
    SSN char(3) not null,
    WorkTitle varchar(65) ,
    Media varchar(15) ,
    IsAdult char,
    Introduction varchar(1500),
    Language varchar(15),
    NumOfEpisode int,
    Status varchar(15),
    OriginSSN char(3),
    SeriesNo int,
    ConnectType varchar(15),
    primary key (SSN)
);
CREATE TABLE creator (
    CreatorID char(5) not null,
    CrName varchar(100) not null,
    CrGender char,
    Job varchar(100),
    primary key (CreatorID)
);
CREATE TABLE made (
    Wssn char(3) not null,
    CreatorID char(5) not null,
    primary key (Wssn, CreatorID),
    foreign key (Wssn) references work(SSN),
    foreign key (CreatorID) references creator(CreatorID)
);
CREATE TABLE sites (
    Wssn char(3) not null,
    Sitelink varchar(300) not null,
    primary key (Wssn, Sitelink),
    foreign key (Wssn) references work(SSN)
);
CREATE TABLE keyword (
    Wssn char(3) not null,
    Keyword varchar(30) not null,
    primary key (Wssn, Keyword),
    foreign key (Wssn) references work(SSN)
);
CREATE TABLE writing_info (
    Wssn char(3) not null,
    WritSSN char(2) not null,
    pagesCount int,
    publisher char(65),
    primary key (Wssn, WritSSN),
    foreign key (Wssn) references work(SSN)
);
CREATE TABLE webtoon_info (
    Wssn char(3) not null,
    ToonSSN char(2) not null,
    IsColored char,
    ToonIsFree char,
    primary key (Wssn, ToonSSN),
    foreign key (Wssn) references work(SSN)
);
CREATE TABLE video_info (
    Wssn char(3) not null,
    VideoSSN char(3) not null,
    ProductionYear int,
    Country varchar(30),
    VideoCategory varchar(15),
    primary key (Wssn, VideoSSN),
    foreign key (Wssn) references work(SSN)
);

--part B
DROP TABLE pjUSER CASCADE CONSTRAINT;
DROP TABLE GENRE CASCADE CONSTRAINT;
DROP TABLE pjLOG CASCADE CONSTRAINT;
DROP TABLE pjCOMMENT CASCADE CONSTRAINT;
DROP TABLE FOLLOW CASCADE CONSTRAINT;
DROP TABLE INTERESTED CASCADE CONSTRAINT;
DROP TABLE HAVE CASCADE CONSTRAINT;

CREATE TABLE pjUSER (
    pjUSERID        VARCHAR(20)     NOT NULL,
    Password        VARCHAR(20)     NOT NULL,
    pjUSERName        VARCHAR(20)     NOT NULL,
    pjUSERGender      CHAR,            
    Birth_date      DATE,
    PRIMARY KEY(pjUSERID)
);
CREATE TABLE GENRE (
    GenreID         INT             NOT NULL,
    GenreName       VARCHAR(30)     NOT NULL,
    PRIMARY KEY(GenreID)
);
CREATE TABLE pjLOG (
    pjLOGID           INT             NOT NULL,
    pjLOGTitle        VARCHAR(30)     NOT NULL,
    pjPublic          CHAR            NOT NULL,
    Likes           INT,
    pjContents        VARCHAR(500),
    pjLOGDate         DATE,
    WriterID        VARCHAR(20) not null, 
    WriteAbout      char(3) not null,
    PRIMARY KEY(pjLOGID),
    FOREIGN KEY (WriterID) REFERENCES pjUSER(pjUSERID),
    FOREIGN KEY (WriteAbout) REFERENCES work(SSN)
);
CREATE TABLE pjCOMMENT (
    pjCOMMENTID      INT             NOT NULL,
    pjText            VARCHAR(320)     NOT NULL,
    CommDate        DATE,
    WriterID        VARCHAR(20) not null,  
    targetPosting   INT,
    PRIMARY KEY(pjCOMMENTID),
    FOREIGN KEY (WriterID) REFERENCES pjUSER(pjUSERID),
    FOREIGN KEY (targetPosting) REFERENCES pjLOG(pjLOGID)
);
CREATE TABLE FOLLOW (
    FollowerID      VARCHAR(20)             NOT NULL,
    pjUSERID          VARCHAR(20)             NOT NULL,
    PRIMARY KEY(FollowerID,pjUSERID),
    FOREIGN KEY (FollowerID) REFERENCES pjUSER(pjUSERID),
    FOREIGN KEY (pjUSERID) REFERENCES pjUSER(pjUSERID)
);
CREATE TABLE INTERESTED (
    pjUSERID          VARCHAR(20)            NOT NULL,
    GenreID         INT             NOT NULL,
    PRIMARY KEY (pjUSERID, GenreID),
    FOREIGN KEY (pjUSERID) REFERENCES pjUSER(pjUSERID),
    FOREIGN KEY (GenreID) REFERENCES GENRE(GenreID)
);
CREATE TABLE HAVE (
    Wssn            char(3)             NOT NULL,
    GenreID         INT             NOT NULL,
    PRIMARY KEY (Wssn, GenreID),
    FOREIGN KEY (Wssn) REFERENCES work(SSN),
    FOREIGN KEY (GenreID) REFERENCES GENRE(GenreID)
);
