FROM openjdk:8-jdk-alpine
#ENV ROOT_PATH="/tmp"
#CMD mvn clean install
#COPY /target $ROOT_PATH
#WORKDIR /home/work/data/www
#RUN /bin/chown -R work:work /tmp
#EXPOSE 9080



# copy the packaged jar file into our docker image
COPY target/bookseller.jar /bookseller.jar
EXPOSE 9080
# set the startup command to execute the jar
#CMD ["java", "-jar", "/bookseller.jar"]
ENTRYPOINT ["java","-jar","bookseller.jar"]