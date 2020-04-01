# High Data Replication
 - Hosts a High Scalable REST service of existing webpages (more specificly tables inside webpages)
 - I know that the name is bad
 
## how it works
 - The docker composition (it can be put in kubernetes, load balanced and everithing ) ups 3 containers
 - 1 Redis 
  - Stores processed data already in JSON
 - 2 Selenium
  - Hosts the page in a actual webbrowser
  - This feature can use the power of websockets and realtime changing (eg. homebrokers)
  - TODO the plan is to listen webpage changes
 - 3 Application
  - Uses a timer to update redis with the browser tables (converted to JSON)
  - TODO - multi pages using configuration files, not code.

## how i run this
 - clone this project
 - docker-compose up -d --build
 - docker-compose down (to stop and remove all containers)
 - as example try: http://localhost:8080/hdr/advfn/6