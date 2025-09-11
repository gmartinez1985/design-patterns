# 🗄️ MongoDB Setup: In-Memory vs Docker

This project supports two MongoDB configurations:

## 1) In-Memory MongoDB (Flapdoodle)
- **Dependency:** [de.flapdoodle.embed.mongo.spring30x](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo) (see `pom.xml`).
- **Purpose:** Mainly used for **tests** and quick development slices (`@DataMongoTest`).
- **Note:** In `application.yaml` the embedded auto-configuration is **disabled**:
  ```yaml
  spring:
    autoconfigure:
      exclude:
        - de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration
  spring:
    data:
      mongodb:
        uri: mongodb://localhost:27017/testdb
  ```
  That means the **running application** connects to an **external MongoDB**, not the embedded one.  
  If you prefer to run the app with embedded Mongo, remove the `exclude` entry and clear the `spring.data.mongodb.uri`.

## 2) Docker MongoDB (recommended for running the app)
- **Why:** A real MongoDB instance makes it easy to inspect the **read model projections** with a graphical client.
- **Compose file:** `src/main/resources/docker-compose.yml`
- **Start it:**
  ```bash
  docker compose -f src/main/resources/docker-compose.yml up -d
  ```
- **Connection string (from `application.yaml`):**
  ```
  mongodb://localhost:27017/testdb
  ```

### GUI Options
- **Mongo Express** (if included in the Compose setup):  
  Open [http://localhost:8081](http://localhost:8081) in your browser.
- **MongoDB Compass** (desktop app):  
  Use the connection string:
  ```
  mongodb://localhost:27017/testdb
  ```

## Quick Run
```bash
# 1) Start external MongoDB
docker compose -f src/main/resources/docker-compose.yml up -d

# 2) Run the Spring Boot app
mvn spring-boot:run
```

---

**Summary:**
- **Embedded Mongo (Flapdoodle):** included in the project, mostly for tests; disabled for the main app by default.
- **Docker Mongo:** used by the application at runtime, supports GUI tools like Mongo Express or Compass for easy inspection.
