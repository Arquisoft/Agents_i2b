FROM maven:3.5-jdk-8-alpine
WORKDIR /usr/src/Agents_i2b
COPY . /usr/src/Agents_i2b/
RUN if test ! -e target/agents_i2b-0.0.1.jar; then mvn package -Dmaven.test.skip=true; fi
EXPOSE 8080
CMD ["java", "-jar", "target/agents_i2b-0.0.1.jar", "--spring.data.mongodb.host=mongo"]
