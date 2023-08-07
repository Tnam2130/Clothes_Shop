 create database clothes_shop;
 use clothes_shop;
 drop database clothes_shop;
 
CREATE TABLE USERS (
    USER_ID INT PRIMARY KEY AUTO_INCREMENT,
    EMAIL VARCHAR(100) UNIQUE,
    FULLNAME VARCHAR(50) not null,
    PASS VARCHAR(500) NOT NULL,
    ADDRESS VARCHAR(250),
    provider varchar(15)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

CREATE TABLE roles (
    role_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE PRODUCTS (
    PRODUCT_ID INT PRIMARY KEY AUTO_INCREMENT,
    CATEGORY_ID INT NOT NULL,
    PRODUCT_NAME VARCHAR(255) NOT NULL,
    description TEXT,
    PRICE DECIMAL(10 , 3 ) NOT NULL,
    old_price DECIMAL(10 , 3 ),
    DELETED BIT
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

CREATE TABLE CATEGORIES (
    CATEGORY_ID INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

CREATE TABLE ORDERS (
    ORDER_ID INT PRIMARY KEY AUTO_INCREMENT,
    USER_ID INT,
    STATUS VARCHAR(50),
    FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

CREATE TABLE ORDER_ITEMS (
    ORDER_ITEM_ID INT PRIMARY KEY AUTO_INCREMENT,
    ORDER_ID INT,
    PRODUCT_ID INT,
    QUANTITY INT,
    PRICE DECIMAL(10 , 3 ),
    STATUS BIT DEFAULT 0,
    FOREIGN KEY (ORDER_ID)
        REFERENCES ORDERS (ORDER_ID),
    FOREIGN KEY (PRODUCT_ID)
        REFERENCES PRODUCTS (PRODUCT_ID)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

CREATE TABLE product_images (
    image_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    image_url VARCHAR(255),
    image_name VARCHAR(255),
    FOREIGN KEY (product_id)
        REFERENCES products (product_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

CREATE TABLE users_roles(
user_id int,
role_id int,
FOREIGN KEY(user_id) REFERENCES users(user_id),
FOREIGN KEY(role_id) REFERENCES roles(role_id)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_UNICODE_CI;

INSERT INTO `roles` (`name`) VALUES ('USER');
INSERT INTO `roles` (`name`) VALUES ('ADMIN');

insert into products(category_id, PRODUCT_NAME, description, PRICE, old_price, DELETED) 
values(1,'Degrey.Madmonks Tee Vietnam 84 Đỏ - DV84 Đỏ', 'Chất liệu: TRICOT', 320000,440000, 1),
	  (1, 'Degrey.Madmonks MAT Jacket Đen - MATD', 'Chưa có mô tả cho sản phẩm này', 420000,500000, 1),
      (2, 'Degrey.madmonks short Pant xám rêu - DMQR','Chất liệu: dù lót lưới', 220000,300000, 1),
      (1,'Áo khoác nam Áo khoát nam kaki chống nắng rẻ from rộng trơn cực chất siêu chất', 'Áo Khoác Kaki M550 với Chất liệu Kaki 2 lớp cực mát (lớp trong lót Dù thấm hút mồ hôi cực tốt); mang phong cách thời trang thời thượng các bạn trẻ;',114000, 149000, 1),
      (2,'Quần đùi nam,quần ngủ 100% cotton cao cấp','Bạn đang kiếm minh 1 chiếc quần ngủ cho bạn hay người thân cùa bạn thí ko nên bỏ qua mẩu quần như thế này.Mẩu quần tính tới bây giờ đang làm mưa làm gió bởi tính chất của quần,mặc mát,"mặc như ko mặc"..đưa bạn vào giấc ngủ sâu,giúp bạn ngủ đủ giấc.',45000,79000,1),
      (3, 'Đồng Hồ Nam nữ HU Cặp Đôi - Dây Hương Vani Cao Cấp - DH602 - Bảo hành 12 tháng','Dây Hương Vani Cao Cấp - DH602 - Bảo hành 12 tháng',169000,null ,1);
insert into product_images(product_id, image_url, image_name) values
														(1, 'dereymagmonks01.jpg',' Degrey.Madmonks Tee Vietnam 84 Đỏ - DV84 Đỏ '),
														(1, 'dereymagmonks.jpg',' Degrey.Madmonks Tee Vietnam 84 Đỏ - DV84 Đỏ '),
														(2,'dereyjacket01.jpg','Degrey.Madmonks MAT Jacket Đen - MATD'),
                                                        (2,'dereyjacket.jpg','Degrey.Madmonks MAT Jacket Đen - MATD'),
                                                        (3,'dereyshortpant01.jpg','Degrey.madmonks short Pant xám rêu - DMQR'),
                                                        (3,'dereyshortpant.jpg','Degrey.madmonks short Pant xám rêu - DMQR'),
                                                        (4,'aokhoacnamkaki03.png','Áo khoác nam - kaki'),
                                                        (4,'aokhoacnamkaki02.png','Áo khoác nam - kaki'),
                                                        (4,'aokhoacnamkaki01.png','Áo khoác nam - kaki'),
                                                        (4,'aokhoacnamkaki04.png','Áo khoác nam - kaki'),
                                                        (4,'aokhoacnamkaki05.png','Áo khoác nam - kaki'),
                                                        (5,'shortpantsleep04.png','Quần đùi nam,quần ngủ 100%'),
                                                        (5,'shortpantsleep05.png','Quần đùi nam,quần ngủ 100%'),
                                                        (5,'shortpantsleep03.png','Quần đùi nam,quần ngủ 100%'),
                                                        (5,'shortpantsleep02.png','Quần đùi nam,quần ngủ 100%'),
                                                        (5,'shortpantsleep01.png','Quần đùi nam,quần ngủ 100%'),
                                                        (6,'dongho01.png','Đồng Hồ Nam nữ HU Cặp Đôi'),
                                                        (6,'dongho02.png','Đồng Hồ Nam nữ HU Cặp Đôi'),
                                                        (6,'dongho03.png','Đồng Hồ Nam nữ HU Cặp Đôi'),
                                                        (6,'dongho04.png','Đồng Hồ Nam nữ HU Cặp Đôi'),
                                                        (6,'dongho05.png','Đồng Hồ Nam nữ HU Cặp Đôi');

insert into categories(category_name) values('Áo'),
											('Quần'),
                                            ('Trang sức khác');
                                            
select p1_0.product_id,p1_0.category_id,p1_0.deleted,p1_0.description,p1_0.old_price,p1_0.price,p1_0.product_name from products p1_0 where p1_0.category_id=1;
select category_name from categories ct inner join products pd on ct.category_id = pd.category_id where pd.category_id=3;
select p1_0.product_id,c1_0.category_id,c1_0.category_name,p1_0.deleted,p1_0.description,p1_0.old_price,p1_0.price,p1_0.product_name from products p1_0 left join categories c1_0 on c1_0.category_id=p1_0.category_id where p1_0.product_id=1;
SELECT pd.category.category_name FROM products pd WHERE pd.productId = 1;
select count(img.image_url) from product_images img inner join products pd on img.product_id= pd.product_id where pd.product_id=5;
select u1_0.user_id,u1_0.address,u1_0.email,u1_0.fullname,u1_0.pass from users u1_0 where u1_0.email='namndt21986@fpt.edu.vn';
select r1_0.user_id,r1_1.role_id,r1_1.name from users_roles r1_0 join roles r1_1 on r1_1.role_id=r1_0.role_id where r1_0.user_id=1;

delete from users where user_id=3;
ALTER TABLE users
RENAME COLUMN provider To auth_provider;

 update users_roles set role_id=2 where user_id=2;