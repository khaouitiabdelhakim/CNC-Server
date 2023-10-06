Insert this into the DB

ALTER TABLE filieres
MODIFY COLUMN name VARCHAR(255); -- Change 255 to the desired length

INSERT INTO filieres (name)
VALUES
('MP'),
('PSI'),
('TSI'),
('ECT'),
('ECS');


INSERT INTO matieres (matiere, coefficient)
VALUES
('MATHEMATIQUES_I', 7),
('MATHEMATIQUES_II', 7),
('PHYSIQUE_I', 5),
('PHYSIQUE_II', 5),
('CHIMIE', 3),
('INFORMATIQUE', 3),
('CULTURE_ARABE_ET_TRADUCTION', 3),
('FRANCAIS', 4),
('ANGLAIS', 3),
('SCIENCES_INDUSTRIELLES', 4),
('TECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES', 4);




INSERT INTO centres (type, description, nom, nbre_salles)
VALUES
("Public", "Description courte en français 1", "Addakhla - Centre Okba Ibn Nafea", 6),
("Public", "Description courte en français 2", "Agadir - Lycée Reda Slaoui", 7),
("Public", "Description courte en français 3", "Beni Mellal - Lycée Mohamed V", 8),
("Public", "Description courte en français 4", "Casablanca - Lycée Al Khansaa", 9),
("Public", "Description courte en français 5", "Casablanca - Lycée Mohamed V", 10),
("Public", "Description courte en français 6", "El Jadida - Lycée Errazi", 11),
("Public", "Description courte en français 7", "Errachidia - Lycée Ibn Tahir", 12),
("Public", "Description courte en français 8", "Fès - Lycée Moulay Idriss", 6),
("Public", "Description courte en français 9", "Guelmim - Lycée Bab Essahra", 7),
("Public", "Description courte en français 10", "Kénitra - Lycée Mohammed VI", 8),
("Public", "Description courte en français 11", "Khouribga - Lycée Ibn Abdoun", 9),
("Public", "Description courte en français 12", "Laâyoune - Lycée Lissane Eddine Ibn Al-Khatib", 10),
("Public", "Description courte en français 13", "Marrakech - Lycée Ibn Timiya", 11),
("Public", "Description courte en français 14", "Meknès - Lycée Moulay Ismail", 12),
("Public", "Description courte en français 15", "Meknès - Lycée Omar Ibn Al-Khattab", 6),
("Public", "Description courte en français 16", "Mohammadia - Lycée Technique", 7),
("Public", "Description courte en français 17", "Nador - Lycée Abdelkarim Al Khattabi", 8),
("Public", "Description courte en français 18", "Oujda - Lycée Omar Ibn Abdelaziz", 9),
("Public", "Description courte en français 19", "Rabat - Lycée Moulay Youssef", 10),
("Public", "Description courte en français 20", "Rabat - Lycée Omar Al-Khayyam", 11),
("Public", "Description courte en français 21", "Safi - Lycée Moulay Abdellah", 12),
("Public", "Description courte en français 22", "Salé - Lycée Salmane Al Farissi", 6),
("Public", "Description courte en français 23", "Settat - Lycée Technique", 7),
("Public", "Description courte en français 24", "Tanger - Lycée Moulay Al Hassan", 8),
("Public", "Description courte en français 25", "Taza - Lycée Acharif Al Idrissi", 9),
("Public", "Description courte en français 26", "Tétouan - Centre CPGE Tétouan", 10),
("Public", "Description courte en français 27", "Ben Guerir - Lycée Mohammed VI d'excellence", 11),
("Public", "Description courte en français 28", "Casablanca - Lycée d'Excellence Mohammed VI", 12),
("Public", "Description courte en français 29", "Martil - Lycée Méditerranéen, LYMED", 6),
("Public", "Description courte en français 30", "Rabat - Lycée Al-Zahrawi", 7);



INSERT INTO roles(name)
VALUES
('ADMIN'),
('STUDENT'),
('FILE_VERIFIER'),
('SYSTEM_MANAGER'),
('GRADE_ENTRY_AGENT');

to generate a secret key

// Generate a secure key for HS512
SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

// Convert the key to a base64-encoded string
String jwtSecret = Base64.getEncoder().encodeToString(jwtSecretKey.getEncoded());

      logger.error("secret: {}", jwtSecret);
