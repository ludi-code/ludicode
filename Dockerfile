from debian 
run apt-get update && \
    apt-get install -y maven openjdk-7-jdk && \
    apt-get clean 
add pom.xml /srv/ludicode/
workdir /srv/ludicode/
run mvn install
add src /srv/ludicode/src/
run mvn install
expose 8080
cmd mvn jetty:run
