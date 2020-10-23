How to Build
===

```
./gradlew clean build
docker build -t philtdr/pr-overview-for-bitbucket .
```

How to Deploy
===

docker-compose.yml
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

How to use with Slash commands
===

### Mattermost
GET on `/message?output=mattermost&repos=PROJECT1:REPOSITORY1,PROJECT2:REPOSITORY4` to get the latest pr overview.
