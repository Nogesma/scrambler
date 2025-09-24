FROM amazoncorretto:21-alpine

COPY scrambler.jar /scrambler.jar
COPY scrambles.sh /etc/periodic/daily/scrambles

CMD ["crond", "-f"]
