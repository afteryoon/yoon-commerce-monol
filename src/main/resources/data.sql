INSERT INTO member (name, password, email, phone, address, created_At, role)
VALUES ('홍길동', 'password123', 'hong@example.com', '01012345678', '서울특별시 강남구', '2024-04-17', 'ROLE_MEMBER'),
       ('이순신', 'pass456', 'lee@example.com', '01098765432', '서울특별시 종로구', '2024-04-17', 'ROLE_MEMBER'),
       ('유관순', 'pw789', 'you@example.com', '01011112222', '서울특별시 영등포구', '2024-04-17', 'ROLE_ADMIN'),
       ('신사임당', 'p@ssw0rd', 'shin@example.com', '01033334444', '서울특별시 강서구', '2024-04-17', 'ROLE_MEMBER');

INSERT INTO category (category_id, category_title)
VALUES (1, '남성 의류'),
       (2, '여성 의류'),
       (3, '아동 의류'),
       (4, '액세서리');

INSERT INTO product (product_id, category_id, name, description, price, inventory)
VALUES (1, 1, '남성 반팔 티셔츠', '시원한 여름 반팔 티셔츠', 25000, 100),
       (2, 1, '남성 슬림핏 청바지', '활동성이 좋은 슬림핏 청바지', 50000, 80),
       (3, 2, '여성 원피스', '봄, 여름 시즌 원피스', 35000, 120),
       (4, 2, '여성 블라우스', '사무실 착용 가능한 블라우스', 45000, 90),
       (5, 3, '아동 운동화', '아이들을 위한 편안한 운동화', 30000, 150),
       (6, 3, '아동 후드 티셔츠', '귀여운 프린트 후드 티셔츠', 20000, 200),
       (7, 4, '남성 벨트', '고급 가죽 벨트', 60000, 50),
       (8, 4, '여성 토트백', '실용적인 토트백', 80000, 70);