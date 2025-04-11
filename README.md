# 📚 Gestion de Bibliothèque - CodeCrafters

## 📋 Présentation du Projet
Bienvenue dans le projet **Gestion de Bibliothèque** développé par l'équipe **CodeCrafters**. Ce projet est conçu pour gérer efficacement les ressources d'une bibliothèque en utilisant une architecture **microservices**. Il inclut la gestion des catalogues, du stock, des livraisons, du e-commerce, des blogs informatifs et des événements.

## 👨‍💻 Équipe CodeCrafters
Nous sommes une équipe spécialisée dans le développement full-stack et les architectures distribuées :
- **Chihi Dorsaf** → Gestion du catalogue
- **MedHedi Hamdi** → Gestion du stock
- **Hiba Louhibi** → Gestion des livraisons
- **Walid Barhoumi** → E-Commerce
- **Chiheb Lahbib** → Blog 
- **Ines Dahmani** → Gestion des événements

## 🔧 Stack Technologique
Nous utilisons des technologies modernes pour assurer la **performance, la scalabilité et la sécurité** de notre application :

### Développement
- **Backend** : Spring Boot (Java) avec architecture microservices
- **Frontend** : Angular
- **Base de données** : MySQL, H2

### Outils DevOps et Sécurité
- **Gestion des microservices** : Spring Cloud (Eureka, Gateway)
- **Authentification et Sécurité** : Keycloak
- **Conteneurisation** : Docker, Docker Compose
---
## 💼 Fonctionnalités détaillées

### 📚 Gestion du Catalogue
- Ajout, modification et suppression des livres
- Classification par catégories et auteurs
- Recherche avancée par titre, auteur, catégorie
- Gestion des métadonnées des livres (ISBN, résumé, etc.)

### 📦 Gestion du Stock
- Suivi des stocks de livres en temps réel
- Vérification automatique des disponibilités


### 🚚 Gestion des Livraisons
- Suivi des commandes et livraisons
- Gestion des statuts (en cours, expédié, livré, annulé)
- Notification des utilisateurs

### 🛒 E-Commerce (Achat en ligne)
- Interface de vente des livres
- Paiements sécurisés
- Historique des commandes

### 📰 Blog (Microservice)
- Création et publication d'articles
- Gestion des commentaires et interactions
- Suivi des tendances et analyses

### 🎉 Gestion des Événements
- Création et gestion des événements de la bibliothèque
- Inscription des utilisateurs aux événements
- Suivi des participants
- Notifications et rappels des événements

---
## 📁 Structure du Projet

📁 **backend (Spring Boot - Microservices)**
```
 ├── catalogue-service/
 ├── stock-service/
 ├── livraison-service/
 ├── ecommerce-service/
 ├── blog-service/
 ├── event-service/
 ├── gateway-service/  (Spring Cloud Gateway)
 ├── eureka-server/  (Service Discovery)
 ├── keycloak/  (Authentification et Sécurité)
```

📁 **frontend (Angular)**
```
 ├── src/app/
 ├── src/assets/
 ├── src/environments/
```

---
## 🔄 Relations entre microservices

- **Catalogue ↔ Stock** : Vérification de la disponibilité des livres
- **Catalogue ↔ E-Commerce** : Achat de livres en ligne
- **Stock ↔ Livraison** : Gestion des expéditions
- **Blog ↔ E-Commerce** : Articles liés aux livres disponibles
- **Événements ↔ Utilisateurs** : Inscription et suivi des participants
- **Eureka Server** : Centralise les microservices
- **Gateway** : Point d'entrée unique pour le frontend
- **Keycloak** : Gère l'authentification et l'autorisation

---
## ⚙️ Points techniques importants

- **Utilisation de Spring Cloud** pour la gestion des microservices
- **Sécurité centralisée avec Keycloak**
- **Communication interservices via API REST et Event-Driven Architecture**
- **Requêtes optimisées avec JPQL et SQL natives**

## 🚢 Déploiement & CI/CD

1. **Cloner le projet** :
   ```bash
   git clone https://github.com/CodeCrafters/GestionBibliotheque.git
   ```
2. **Backend** :
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
3. **Frontend** :
   ```bash
   cd frontend
   npm install
   ng serve
   ```
4. **Avec Docker** :
   ```bash
   docker-compose up -d
   ```

---
## 📜 Licence
Ce projet est sous **licence MIT**.

### 🎯 Équipe CodeCrafters | Gestion de Bibliothèque 🚀

