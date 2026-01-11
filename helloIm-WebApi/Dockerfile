# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloIm-WebApi.jar helloIm-WebApi.jar

# 设置环境变量
ENV ZK_ADDRESS=""
ENV JAVA_OPTS=""

# 暴露端口号
EXPOSE 8087 28807

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloIm-WebApi.jar"]
