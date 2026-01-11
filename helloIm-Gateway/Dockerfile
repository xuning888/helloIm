# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloIm-Gateway.jar helloIm-Gateway.jar

# 设置环境变量
# zookeeper的地址, 用于dubbo的注册中心
ENV ZK_ADDRESS=""

ENV PROTOCOL="tcp"
ENV GATE_PORT=9300

# 暴露端口号
EXPOSE 8081 28802

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloIm-Gateway.jar"]
