-- ++++++++++++++++++ 가게정보 덤프 ++++++++++++++++++
INSERT INTO shops (name, address, phone, image, enabled_order, minimum_price, closed)
VALUES (
'컴포즈커피-수완장덕점',
'광주광역시 광산구 장덕동 1043 수완지구 호반베르디움 2차아파트 213(상가)동 105호',
'062-710-4090',
'https://img.ssafy.coffee/1/02c725dc7afdf889440688026b9f78b6253678ff41842dafe976c7beb37d0057.jpg',
FALSE,
12000,
FALSE
);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

select * from parties;
-- ++++++++++++++++++ 파티 덤프 ++++++++++++++++++
-- Users Table Dummy Data
INSERT INTO users (user_id, email) VALUES
(1, 'ssafy1@ssafy.com'),
(2, 'ssafy2@ssafy.com'),
(3, 'ssafy3@ssafy.com');

-- Managers Table Dummy Data
INSERT INTO managers (manager_id, manager_pw, shop_id) VALUES
('manager1', 'manager1', 1);

-- Parties Table Dummy Data
INSERT INTO parties (party_id, access_code, name, generation, classroom, last_order_time, confirmed_time, rejected_time, real_ordered_time, made_time, delivered_time, created_time, updated_time, shop_id, user_id) VALUES
(1, 'ACODE123', '컴포즈 파티모집 중', 10, 1, DATE_ADD(NOW(), INTERVAL 2 HOUR), NULL, NULL, NULL, NULL, NULL, NOW(), NOW(), 1, 1);

-- Participants Table Dummy Data
INSERT INTO participants (participant_id, name, is_carrier, paid, party_id) VALUES
(1, '고영훈', 1, 1, 1),
(2, '양희승', 0, 0, 1),
(3, '전상혁', 0, 1, 1);

INSERT INTO creators (email, name, bank, account, party_id) VALUES 
('ssafy1@ssafy.com', '김싸피', '싸피은행', '123456-78-9101112', 1);
