FROM alpine
MAINTAINER Shadi-a

ENV STREAM_NAME test-stream

WORKDIR /app

USER root

RUN apk -U upgrade && \
    apk -Uuv add --no-cache make groff less python py-pip bash nodejs nodejs-npm ca-certificates && \
	pip install awscli supervisor

RUN npm install -g dynalite kinesalite

COPY prepare-env.sh /app/prepare-env.sh
ADD supervisord.conf /etc/supervisord.conf

EXPOSE 7000 7001

#ENTRYPOINT /app/prepare-env.sh
ENTRYPOINT ["supervisord", "--nodaemon", "--configuration", "/etc/supervisord.conf"]