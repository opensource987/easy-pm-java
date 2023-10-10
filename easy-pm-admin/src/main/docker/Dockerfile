#基础镜像来源
FROM openjdk:8

LABEL app="easy-pm-admin" version="1.0.0" by="Jesse"

#同步容器和宿主机时间
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone

ENV SERVICEAPP_NAME="easy-pm-admin-1.0.0.jar"

ADD ./run.sh /
ADD ./application.yml /
ADD ./${SERVICEAPP_NAME} /

#RUN mkdir -p /data/logs
#RUN mkdir -p /data/upload-dir

RUN chmod u+x /run.sh

ENTRYPOINT [ "sh", "-c", "./run.sh start ${SERVICEAPP_NAME}" ]

#模块目录下打包命令：mvn clean package -U  -Dmaven.test.skip=true docker:build

