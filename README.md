# bookseller
mvn clean install
docker build -t bookseller .
docker run -p 9080:9080 -t bookseller:latest

database url--->
http://localhost:9080/h2-console/login.jsp?jsessionid=c2618119e6c19a54f1122c605fcb2ef9

jdbc url for h2:
jdbc:h2:mem:booksellerdb

context for apis:
localhost:9080
