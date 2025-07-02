-- A UTILISER POUR CREER UN PROFIL SUR SQL SERVER CHEZ SOI
-- en plus d'activer l'authentification SQL server
-- clic droit serveur (nom_du_pc)/SQLSEXPRESS en haut de l'arborescence
-- propriété -> sécurité -> mode d'authentification

-- remplacer tous les username par ce que vous souhaité
-- ainsi que le password

-- Étape 1 : Créer un login SQL au niveau du serveur
CREATE LOGIN username
    WITH PASSWORD = 'password';

-- Étape 2 : Aller dans la base concernée (remplacer par votre nom)
USE ici_on_vend;
GO

-- Étape 3 : Créer un utilisateur lié au login dans cette base
CREATE USER username FOR LOGIN username;
GO

-- Étape 4 : Donner tous les droits sur la base (rôle db_owner)
EXEC sp_addrolemember 'db_owner', 'username';
GO
