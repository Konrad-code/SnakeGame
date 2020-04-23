CREATE TABLE public.players
(
    id_player integer NOT NULL DEFAULT nextval('players_id_player_seq'::regclass),
    nick character varying(64) COLLATE pg_catalog."default" DEFAULT NULL::character varying,
    login character varying(64) COLLATE pg_catalog."default" DEFAULT NULL::character varying,
    password character varying(64) COLLATE pg_catalog."default" DEFAULT NULL::character varying,
    score integer DEFAULT 0,
    root boolean DEFAULT false
)

INSERT INTO players (id_player, nick, login, password, score, root) VALUES (1,'Doe','JohnDo','1234qwer', 25, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (2,'SeriousGuy','Mary69', 'wwadww211s', 38, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (3,'Queue','TinySusan','q1w2e3r4t5', 21, FALSE);

INSERT INTO players (id_player, nick, login, password, score, root) VALUES (4,'Williams','David14','123456qwerty', 122, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (5,'Johnson','TightLisa','1234abcd', 99, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (6,'Smith','Paul102','1z2x3c4v', 69, FALSE);

INSERT INTO players (id_player, nick, login, password, score, root) VALUES (7,'Adams','CarlTheMaster','1234567890q', 999, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (8,'Brown','BillyCosby','rctybz', 219, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (9,'Thomas','Su99san','q123456', 290, FALSE);

INSERT INTO players (id_player, nick, login, password, score, root) VALUES (10,'Davis','JohnnyBravo','gfhjkmgfhjkm', 0, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (11,'Fowler','BloodyMary','8J4yE3Uz', 74, FALSE);
INSERT INTO players (id_player, nick, login, password, score, root) VALUES (12,'Waters','DavidDuhovny','19871987', 123, FALSE);

INSERT INTO players (id_player, nick, login, password, score, root) VALUES (13,'mHm_MaXi','admin','1234', 9999, TRUE);

INSERT INTO players (nick, login, password) VALUES ('janusz', 'janina', 'pazdzioch');
INSERT INTO players (nick, login, password) VALUES ('pawian', 'karolek', 'ksado131');
INSERT INTO players (nick, login, password) VALUES ('Marynarz12', 'beton3', 'M%43kdmp');
INSERT INTO players (nick, login, password) VALUES ('PawiloniarzMas', 'kefir_98', '09139dk');