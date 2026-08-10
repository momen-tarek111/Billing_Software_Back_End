# ---- Build stage ----
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy wrapper and pom first for better layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

# Pre-download dependencies (cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Now copy the rest of the source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---- Run stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]