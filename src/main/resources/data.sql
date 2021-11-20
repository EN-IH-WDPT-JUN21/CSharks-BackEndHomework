-- CREATE TABLES IF NOT EXISTS
CREATE TABLE IF NOT EXISTS `users`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `bio` varchar(255),
    `email_address` varchar(255) UNIQUE,
    `password` varchar(255),
    `picture_url` varchar(255),
    `username` varchar(255) UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `roles`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `users_roles`(
    `users_id` bigint(20) NOT NULL,
    `roles_id` int(11) NOT NULL,
    PRIMARY KEY (`users_id`,`roles_id`),
    UNIQUE KEY `user_role` (`users_id`,`roles_id`),
    FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
    FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
);



CREATE TABLE IF NOT EXISTS `playlists`(
    `playlist_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255),
    `visible` bit(1) NOT NULL,
    `user_id` bigint(20),
    PRIMARY KEY (`playlist_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE IF NOT EXISTS `movies`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title_id` varchar(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `playlists_titles`(
    `playlist_id` bigint(20) NOT NULL,
    `title_id` bigint(20) NOT NULL,
    PRIMARY KEY (`playlist_id`,`title_id`),
    UNIQUE KEY `playlist_title` (`playlist_id`,`title_id`),
    FOREIGN KEY (`title_id`) REFERENCES `movies` (`id`),
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`playlist_id`)
);


-- POPULATE TABLES IF NOT POPULATED
INSERT IGNORE into users(id, bio, email_address, password, picture_url, username)
values
    ('1', 'Your bio here...', 'admin@sharkvision.com', '$2a$10$TrmBG111Bf9YKKHFS7xMyu6.V7xcol7SYYpn0EG3of0NeY2Gjlrje', 'https://petbcc.ufscar.br/static/site2016/images/user.png', 'admin'),
    ('2', 'Your bio here...', 'user@sharkvision.com', '$2a$10$uxFpSffU6zvxsPiqG7imrOj5JG9srioU3p1FjgX5zmYMTtt.GIFme', 'https://petbcc.ufscar.br/static/site2016/images/user.png', 'user');

insert IGNORE into roles(id, name)
values
    ('1', 'ADMIN'),
    ('2', 'USER');


insert IGNORE into users_roles(users_id, roles_id)
values
    ('1', '1'),
    ('2', '2');


insert IGNORE into playlists(playlist_id, name, visible, user_id)
values
    ('1', 'To Watch', '0', '2'),
    ('2', 'Favorites', '0', '2'),
    ('3', 'Marvel stuff', '1', '2');

insert IGNORE into movies(id, title_id)
values
    ('1', 'tt0816692'),
    ('2', 'tt1160419'),
    ('3', 'tt0468569'),
    ('4', 'tt1375666'),
    ('5', 'tt4154796'),
    ('6', 'tt9376612'),
    ('7', 'tt4154756'),
    ('8', 'tt8946378'),
    ('9', 'tt2250912'),
    ('10', 'tt0307453');

insert IGNORE into playlists_titles(playlist_id, title_id)
values
    ('2', '1'),
    ('1', '2'),
    ('2', '3'),
    ('2', '4'),
    ('2', '5'),
    ('3', '5'),
    ('1', '6'),
    ('3', '6'),
    ('2', '7'),
    ('3', '7'),
    ('2', '8'),
    ('3', '9'),
    ('1', '10'),
    ('2', '10'),
    ('3', '10');
