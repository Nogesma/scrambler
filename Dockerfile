FROM openjdk:17-alpine

RUN apk add busybox-initscripts

COPY scrambler.jar /scrambler.jar
COPY scrambles.sh /etc/periodic/daily/scrambles

CMD ["crond", "-f"]
