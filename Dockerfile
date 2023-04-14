#Create MySQL Image for Histogram Application
FROM mysql
MAINTAINER b.jedlinski@bewpage.com

ENV MYSQL_ROOT_PASSWORD test1234

EXPOSE 3306