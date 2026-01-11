# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloIm-store.jar helloIm-store.jar

# 设置环境变量
# zookeeper的地址, 用于dubbo的注册中心
ENV ZK_ADDRESS=""
# mysql的jdbc连接信息
ENV DB1_URL=""
ENV DB1_USERNAME=""
ENV DB1_PASSWORD=""

ENV DB2_URL=""
ENV DB2_USERNAME=""
ENV DB2_PASSWORD=""

ENV DB3_URL=""
ENV DB3_USERNAME=""
ENV DB3_PASSWORD=""

ENV DB4_URL=""
ENV DB4_USERNAME=""
ENV DB4_PASSWORD=""

ENV JAVA_OPTS=""

# 暴露端口号
EXPOSE 8086 28806

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloIm-store.jar"]
