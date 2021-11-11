FROM openjdk:8-jdk-alpine
ENV ROOT_PATH="/home/work/data/www"
COPY build_target $ROOT_PATH
WORKDIR /home/work/data/www
RUN /bin/chown -R work:work /home/work/
EXPOSE 6183
ENTRYPOINT ["java","-jar","bookseller.jar"]