# bookseller
mvn clean install
docker build -t bookseller .
docker run -p 9080:9080 -t bookseller:latest
