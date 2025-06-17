# Exercice 
## 🎯 Objectif

Vous devez implémenter une **API web** en Java qui répond au scénario suivant :

### ✅ Routes

- `GET /client`  
  ➔ **Publique**  
  ➔ Renvoie la liste des clients enregistrés.

- `POST /client`  
  ➔ **Privée**  
  ➔ Permet d’ajouter un nouveau client. Un client est défini par un **nom** et un **prénom**.

- `DELETE /client/{id}`  
  ➔ **Privée**  
  ➔ Permet de supprimer un client par son identifiant.

### 🛠️ Outils

- **Génération du projet** : via [Spring Initializr](https://start.spring.io/)
- **Dépendances incluses** :
    - `Spring Web`
    - `Spring Boot DevTools`
    - `Lombok`
    - `Spring Security`
    - `Spring Data JPA`
    - `Validation`
    - `PostgreSQL Driver`
- **Version Java** : JDK 17
- **Build Tool** : Gradle
