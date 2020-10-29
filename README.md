How to it Deploy on your Server
===
The latest Docker image is hosted at Dockerhub. So you just need to `docker-compose up` the following `docker-compose.yml`.
``` yaml
version: '3.7'
services:
  app:
    image: philtdr/pr-overview-for-bitbucket:latest
    restart: unless-stopped
    ports: ["8080:8080"]
    environment:
      - BITBUCKET_BASE_URL=https://bitbucket.yourdomain.com/
      - BITBUCKET_TOKEN=**your_token**
```

How to use
===
You can create a `slash command` in your favorite chat tool to directly post it there.

### Mattermost
GET on `/message?output=mattermost&repos=PROJECT1:REPOSITORY1,PROJECT2:REPOSITORY4`

How to Build
===

### Build with Gradle
```
./gradlew clean build
```
*This will build a .jar file*

### Build with Docker
```
docker build -t philtdr/pr-overview-for-bitbucket .
```
*This will build a ready to use Docker image*

### Build with Docker and directly run it
1. create a `docker-compose.yml` in the project root. *(It is ignored in the .gitignore file, so it is save to create it there)*
``` yaml
version: '3.7'
services:
  app:
    build:
      context: .
    restart: unless-stopped
    ports: ["8080:8080"]
    environment:
      - BITBUCKET_BASE_URL=https://bitbucket.yourdomain.com/
      - BITBUCKET_TOKEN=**your_token**
```
2. build and run
```
docker-compose up --build
```
