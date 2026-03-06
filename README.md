# Claim Payment Processing Service

Spring Boot application for claim payment intake and batch claim payment processing.

## Features
- Submit claim payment requests.
- Validate incoming payloads (amount, currency code, and required fields).
- View all claim payments and status.
- Trigger batch processing manually.
- Scheduled batch run every 2 minutes (configurable).
- Containerized with Docker and deployable to OpenShift on-prem.

## Prerequisites
- Java 17+
- Maven 3.9+
- Docker (optional, for container image build)
- OpenShift CLI `oc` (optional, for cluster deployment)

## Build and test locally
```bash
mvn clean test
mvn clean package
```

## If you get `HTTP/1.1 403 Forbidden` during `mvn`
A `403` during dependency download usually means your proxy/repository manager is denying Maven requests (not a Java/Spring code issue).

### 1) Confirm the URL is reachable outside Maven
```bash
curl -I https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-starter-parent/3.3.2/spring-boot-starter-parent-3.3.2.pom
```
If this is `200 OK` but `mvn` returns `403`, your Maven proxy/mirror settings are the issue.

### 2) Inspect Maven settings used by your run
```bash
mvn help:effective-settings -DshowPasswords=false
```
Check these sections carefully:
- `<proxies>`: wrong host/port or missing auth can cause `403`.
- `<mirrors>`: corporate mirror URL might block unauthenticated requests.
- `<servers>`: credentials may be required for your mirror/repo ID.

### 3) Typical fix for corporate proxy (set credentials + HTTPS)
In `~/.m2/settings.xml`:
```xml
<settings>
  <proxies>
    <proxy>
      <id>corp-proxy</id>
      <active>true</active>
      <protocol>https</protocol>
      <host>YOUR_PROXY_HOST</host>
      <port>YOUR_PROXY_PORT</port>
      <username>YOUR_USER</username>
      <password>YOUR_PASSWORD</password>
      <nonProxyHosts>localhost|127.*|*.local</nonProxyHosts>
    </proxy>
  </proxies>
</settings>
```

### 4) If your company uses Nexus/Artifactory, mirror Central through it
```xml
<settings>
  <mirrors>
    <mirror>
      <id>corp-nexus</id>
      <mirrorOf>central</mirrorOf>
      <url>https://YOUR_NEXUS_OR_ARTIFACTORY/repository/maven-central/</url>
    </mirror>
  </mirrors>
  <servers>
    <server>
      <id>corp-nexus</id>
      <username>YOUR_USER</username>
      <password>YOUR_PASSWORD</password>
    </server>
  </servers>
</settings>
```

### 5) Clear stale failed cache and force re-download
```bash
rm -rf ~/.m2/repository/org/springframework/boot/spring-boot-starter-parent
mvn -U clean test
```

### 6) Check environment proxy variables
```bash
env | grep -i proxy
```
If these are set incorrectly, correct them before running Maven.

## Run locally
```bash
java -jar target/claim-processing-app-0.0.1-SNAPSHOT.jar
```

The service starts on `http://localhost:8080`.

## Quick local API test
Create a payment:
```bash
curl -X POST http://localhost:8080/api/v1/claims/payments \
  -H "Content-Type: application/json" \
  -d '{
    "claimId": "CLM-1001",
    "memberId": "MBR-44",
    "amount": 950.75,
    "currency": "USD"
  }'
```

Trigger a batch:
```bash
curl -X POST "http://localhost:8080/api/v1/claims/payments/batch?batchSize=20"
```

List payments:
```bash
curl http://localhost:8080/api/v1/claims/payments
```

## Docker image build
```bash
mvn clean package
docker build -t claim-processing-app:latest .
```

## OpenShift deployment (on-prem)
1. Push the built image to a registry accessible by OpenShift.
2. Update the image reference in `openshift/deployment.yaml`.
3. Apply the manifest:
```bash
oc apply -f openshift/deployment.yaml
```
