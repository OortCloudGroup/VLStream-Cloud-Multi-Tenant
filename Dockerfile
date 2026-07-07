# FROM registry.cn-hangzhou.aliyuncs.com/bladex-repo/alpine-java:openjdk17_cn_slim
#docker pull swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/library/eclipse-temurin:17-jre-jammy
#docker tag  swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/library/eclipse-temurin:17-jre-jammy  docker.io/library/eclipse-temurin:17-jre-jammy
FROM docker.io/library/eclipse-temurin:17-jre-jammy

LABEL maintainer="zhonghuixiong"

WORKDIR /root

RUN apt-get update \
    && apt-get install -y --no-install-recommends ffmpeg \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ADD ./doc/smartjavaai_cache ./smartjavaai_cache

COPY ./target/apaas-vls-server.jar app.jar

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-Duser.timezone=GMT+8", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
