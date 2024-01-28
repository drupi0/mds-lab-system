
## Deployment

System Dependencies:

```bash
  OS Type: Ubuntu/Linux
  Recommended RAM: 4GB
  Recommended Storage: 128GB SSD (higher the better)
```

Software Dependencies:

```bash
  Docker: v20.10.22 and later
  Docker Compose: v2.15.1 and later
  Git: v2.32.1 and later
```

Deployment Instructions:

```bash
  1. Add the following line to /etc/hosts (don't include quotes)
    "127.0.0.1 mds.intranet"

  2. Open a terminal and clone this repository on the host server

  3. cd to `mds-portal-pipeline` and `git pull origin master` to pull the latest code

  4. Open docker-compose.yaml and change the lines with comments (#)

  5. Run `docker login ghcr.io`, credentials will be sent as requested

  6. Run `docker-compose up -d` to start the containers

  7. Access portal via `http://mds.intranet`
 
```

