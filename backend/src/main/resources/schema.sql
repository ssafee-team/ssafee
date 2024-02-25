-- MySQL

DROP TABLE IF EXISTS `shop`,
    `menu_category`,
    `menu`,
    `option_category`,
    `menu_option_category`,
    `option`,
    `user`,
    `room`,
    `creator`,
    `order`,
    `participant`,
    `choice_menu`,
    `choice_option_category`,
    `choice_option`,
    `chat`;

CREATE TABLE `shop`
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(32)  NOT NULL,
    phone         VARCHAR(32)  NOT NULL,
    address       VARCHAR(255) NOT NULL,
    image_url     VARCHAR(255) NULL,
    enabled_order BOOL         NOT NULL DEFAULT FALSE,
    minimum_price BIGINT       NOT NULL,
    closed        BOOL         NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       BOOL         NOT NULL DEFAULT FALSE
);

CREATE TABLE `menu_category`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32) NOT NULL,
    shop_id    BIGINT      NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted    BOOL        NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shop_id) REFERENCES `shop` (id)
);

CREATE TABLE `menu`
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(32)  NOT NULL,
    description      VARCHAR(255) NOT NULL,
    price            INT          NOT NULL,
    image_url        VARCHAR(255) NULL,
    sold_out         BOOL         NOT NULL DEFAULT FALSE,
    shop_id          BIGINT       NOT NULL,
    menu_category_id BIGINT       NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted          BOOL         NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shop_id) REFERENCES `shop` (id),
    FOREIGN KEY (menu_category_id) REFERENCES `menu_category` (id)
);

CREATE TABLE `option_category`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32) NOT NULL,
    required   BOOL        NOT NULL,
    max_count  INT         NULL,
    shop_id    BIGINT      NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted    BOOL        NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shop_id) REFERENCES `shop` (id)
);

CREATE TABLE `menu_option_category`
(
    menu_id            BIGINT    NOT NULL,
    option_category_id BIGINT    NOT NULL,
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (menu_id, option_category_id),
    FOREIGN KEY (menu_id) REFERENCES `menu` (id),
    FOREIGN KEY (option_category_id) REFERENCES `option_category` (id)
);

CREATE TABLE `option`
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    name               VARCHAR(32) NOT NULL,
    price              INT         NOT NULL,
    shop_id            BIGINT      NOT NULL,
    option_category_id BIGINT      NOT NULL,
    created_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted            BOOL        NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shop_id) REFERENCES `shop` (id),
    FOREIGN KEY (option_category_id) REFERENCES `option_category` (id)
);

CREATE TABLE `user`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    authority  ENUM ('CREATOR', 'MANAGER') NOT NULL,
    email      VARCHAR(32)                 NULL UNIQUE,
    username   VARCHAR(32)                 NULL UNIQUE,
    password   VARCHAR(32)                 NULL,
    shop_id    BIGINT                      NULL,
    created_at TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (shop_id) REFERENCES `shop` (id)
);

CREATE TABLE `room`
(
    id              CHAR(10) PRIMARY KEY,
    name            VARCHAR(32) NOT NULL,
    generation      INT         NOT NULL,
    classroom       INT         NOT NULL,
    last_order_time TIMESTAMP   NOT NULL,
    shop_id         BIGINT      NOT NULL,
    user_id         BIGINT      NOT NULL,
    created_at      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (shop_id) REFERENCES `shop` (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE `creator`
(
    id                     BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                   VARCHAR(8)   NOT NULL,
    bank                   VARCHAR(32)  NOT NULL,
    account                VARCHAR(32)  NOT NULL,
    mattermost_webhook_url VARCHAR(255) NULL,
    room_id                CHAR(10)     NOT NULL,
    FOREIGN KEY (room_id) REFERENCES `room` (id)
);

CREATE TABLE `order`
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    confirmed_at TIMESTAMP NULL,
    rejected_at  TIMESTAMP NULL,
    made_at      TIMESTAMP NULL,
    delivered_at TIMESTAMP NULL,
    room_id      CHAR(10)  NOT NULL,
    shop_id      BIGINT    NOT NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES `room` (id),
    FOREIGN KEY (shop_id) REFERENCES `shop` (id)
);

CREATE TABLE `participant`
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(8) NULL,
    picked_carrier BOOL       NOT NULL DEFAULT FALSE,
    paid           BOOL       NOT NULL DEFAULT FALSE,
    room_id        CHAR(10)   NOT NULL,
    created_at     TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES `room` (id)
);

CREATE TABLE `choice_menu`
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id        CHAR(10)  NOT NULL,
    participant_id BIGINT    NOT NULL,
    menu_id        BIGINT    NOT NULL,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES `room` (id),
    FOREIGN KEY (participant_id) REFERENCES `participant` (id),
    FOREIGN KEY (menu_id) REFERENCES `menu` (id)
);

CREATE TABLE `choice_option_category`
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id            CHAR(10) NOT NULL,
    choice_menu_id     BIGINT   NOT NULL,
    option_category_id BIGINT   NOT NULL,
    FOREIGN KEY (room_id) REFERENCES `room` (id),
    FOREIGN KEY (choice_menu_id) REFERENCES `choice_menu` (id),
    FOREIGN KEY (option_category_id) REFERENCES `option_category` (id)
);

CREATE TABLE `choice_option`
(
    id                        BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id                   CHAR(10) NOT NULL,
    choice_option_category_id BIGINT   NOT NULL,
    option_id                 BIGINT   NOT NULL,
    FOREIGN KEY (room_id) REFERENCES `room` (id),
    FOREIGN KEY (choice_option_category_id) REFERENCES `choice_option_category` (id),
    FOREIGN KEY (option_id) REFERENCES `option` (id)
);

CREATE TABLE `chat`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(8)   NOT NULL,
    content    VARCHAR(255) NOT NULL,
    room_id    CHAR(10)     NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES `room` (id)
);
