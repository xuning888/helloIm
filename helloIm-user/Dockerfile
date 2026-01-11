# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloIm-user.jar helloIm-user.jar

# 设置环境变量
# zookeeper的地址, 用于dubbo的注册中心
ENV ZK_ADDRESS=""
# mysql的jdbc连接信息
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
# redis的地址, 目前是以standalone为例
ENV REDIS_ADDR=""
ENV JAVA_OPTS=""

# 暴露端口号
EXPOSE 8088 28808

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloIm-user.jar"]
