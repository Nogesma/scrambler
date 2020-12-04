FROM openjdk:11-buster

RUN apt-get update && apt-get -y install cron
ADD crontab /etc/cron.d/daily-scrambles
RUN chmod 0644 /etc/cron.d/daily-scrambles
RUN crontab /etc/cron.d/daily-scrambles

COPY scrambler.jar /scrambler.jar

CMD ["cron", "-f"]
