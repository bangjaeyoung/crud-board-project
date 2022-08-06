CREATE TABLE BOARD(
                      SEQ 		int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      TITLE 	varchar(200) NOT NULL,
                      WRITER 	varchar(20) NOT NULL,
                      CONTENT 	varchar(2000) NOT NULL,
                      REG_DATE	DATETIME DEFAULT NOW() NOT NULL,
                      CNT 		int(5) DEFAULT 0
);