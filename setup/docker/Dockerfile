FROM debian:wheezy
MAINTAINER zemiak@gmail.com

RUN apt-get update && apt-get -yq install dialog openssh-server postfix bzip2 locales \
    postgresql cron postgresql-client ffmpeg mp4v2-utils git ffmpegthumbnailer

# SSH Server
RUN groupadd -r vasko -g 434 && useradd -u 432 -r -g vasko -d /home/vasko -s /bin/bash -c "Miroslav Vasko" vasko
RUN mkdir /home/vasko
RUN chown vasko.vasko /home/vasko
RUN echo 'root:docker' | chpasswd
RUN echo 'vasko:docker' | chpasswd
RUN mkdir /home/vasko/.ssh && chown vasko.vasko /home/vasko/.ssh && chmod 700 /home/vasko/.ssh

# Mail
ADD mailname /etc/
ADD main.cf /etc/postfix/
ADD sasl_passwd /etc/postfix/
RUN postmap /etc/postfix/sasl_passwd
RUN chmod 600 /etc/postfix/sasl_passwd*
RUN chown -RR root:postdrop /usr/sbin/postqueue
RUN chown -R root:postdrop /usr/sbin/postdrop


# PostgreSQL
USER root
RUN localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
ENV LANG en_US.utf8
ADD online-db.sql /tmp/

USER postgres

# Create a PostgreSQL role named ``docker`` with ``docker`` as the password and
# then create a database `docker` owned by the ``docker`` role.

RUN    /etc/init.d/postgresql start &&\
    psql --command "CREATE USER online_user WITH SUPERUSER PASSWORD 'online/0';" &&\
    createdb --owner=online_user online &&\
    cat /tmp/online-db.sql | psql online >/dev/null &&\
    /etc/init.d/postgresql stop

ADD pg_hba.conf /etc/postgresql/9.1/main/
RUN echo "listen_addresses='*'" >> /etc/postgresql/9.1/main/postgresql.conf

# Database Backup
USER root
ADD postgresql-backup /etc/cron.d/
ADD postgresql-backup.sh /usr/local/bin/
RUN mkdir -p /mnt/backup

# Wildfly Application Server and Online Application
ENV ONLINE_VERSION 1_0_2

USER root
ADD jdk.tar.gz /opt/
RUN ln -s /opt/jdk* /opt/jdk
ENV JAVA_HOME /opt/jdk
ENV PATH $PATH:/opt/jdk/bin

RUN apt-get -q clean

ENV WILDFLY_VERSION 8.2.0.Final

# Add the WildFly distribution to /opt
ADD wildfly-$WILDFLY_VERSION.tar.gz /opt/

# Make sure the distribution is available from a well-known place
RUN ln -s /opt/wildfly-$WILDFLY_VERSION /opt/wildfly

# Certificate
ADD zemiakbox.keystore /opt/wildfly/standalone/configuration/

# Set the JBOSS_HOME env variable
ENV JBOSS_HOME /opt/wildfly

# Create the wildfly user and group
RUN groupadd -r wildfly -g 433 && useradd -u 431 -r -g wildfly -d /opt/wildfly -s /bin/false -c "WildFly user" wildfly

# Change the owner of the /opt/wildfly directory
RUN chown -R wildfly:wildfly /opt/wildfly*

# Run everything below as the wildfly user
USER wildfly

############################################################ Deploying Online: start
RUN mkdir -p /opt/wildfly/projects/
WORKDIR /opt/wildfly/projects/
RUN git clone https://github.com/zemiak/online.git
RUN cd online && git checkout tags/$ONLINE_VERSION
ADD online.war /tmp/

WORKDIR /opt/wildfly
RUN /opt/wildfly/bin/add-user.sh admin admin --silent

USER root
ADD run-wildfly-for-setup.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/run-wildfly-for-setup.sh
RUN /usr/local/bin/run-wildfly-for-setup.sh \
    && sleep 15s \
    && cd /opt/wildfly/projects/online/setup/wildfly \
    && bash setup.sh prod \
    && /opt/wildfly/bin/jboss-cli.sh --connect --command="deploy /tmp/online.war" \
    && killall java
RUN chmod -R +s *
RUN chmod -R +t *
RUN chown -R wildfly.wildfly *
RUN rm -rf /opt/wildfly/standalone/configuration/standalone_xml_history/current
############################################################ Deploying Online: end

EXPOSE 8080 9990 22 5432

RUN echo "export LC_ALL=en_US.utf8" >>/etc/profile

ADD emacs /usr/bin/
ADD start.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/start.sh

CMD "/usr/local/bin/start.sh"

