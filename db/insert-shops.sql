-- 가게
INSERT INTO
  shops (
    name,
    address,
    phone,
    image,
    enabled_order,
    minimum_price
  )
VALUES
  (
    '컴포즈커피 수완장덕점',
    '광주광역시 광산구 장덕동 1043 수완지구 호반베르디움 2차아파트 213(상가)동 105호',
    '062-710-4090',
    'https://img.ssafy.coffee/shop/1/02c725dc7afdf889440688026b9f78b6253678ff41842dafe976c7beb37d0057.jpg',
    TRUE,
    12000
  ),
  (
    '공차 광주수완점',
    '광주광역시 광산구 장덕동 1657 1657 1층 105,106호',
    '062-962-6607',
    'https://img.ssafy.coffee/shop/2/b2befa3b43d6f6b7c4d10b767dba705a501aae3e5418ca829aa337b992dfa9a8.jpg',
    FALSE,
    12000
  );

-- 유저
INSERT INTO
  users (email)
VALUES
  ('koyo.ssafy@gmail.com');

-- 파티
INSERT INTO
  parties (
    party_id,
    access_code,
    name,
    generation,
    classroom,
    last_order_time,
    shop_id,
    user_id
  )
VALUES
  (
    1,
    'ACODE123',
    '컴포즈 파티모집 중',
    10,
    1,
    DATE_ADD(NOW(), INTERVAL 2 HOUR),
    1,
    1
  );

-- Participants Table Dummy Data
INSERT INTO
  participants (participant_id, name, is_carrier, paid, party_id)
VALUES
  (1, '고영훈', 0, 0, 1),
  (2, '양희승', 0, 0, 1),
  (3, '전상혁', 0, 0, 1);

INSERT INTO
  creators (email, name, bank, account, party_id)
VALUES
  (
    '',
    '김싸피',
    '싸피은행',
    '123456-78-9101112',
    1
  );

-- 매니저
INSERT INTO
  managers (manager_id, manager_pw, shop_id)
VALUES
  ('manager1', 'manager1', 1);