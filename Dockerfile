# 基于java镜像创建新镜像
FROM adoptopenjdk/openjdk8-openj9:jdk8u412-b08_openj9-0.44.0-alpine-slim
# 作者
MAINTAINER bcrjl

EXPOSE 24803

WORKDIR /app

# 创建 bin 目录
RUN mkdir -p config \
    && mkdir -p lib \
    && mkdir -p log

COPY ./target/rss-reader.jar /app/rss-reader.jar
COPY ./target/lib/* /app/lib/
COPY ./config/* /app/config/

ENTRYPOINT ["java","-Dloader.path=/app/lib/", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/rss-reader.jar"]
