FROM ubuntu:jammy as builder

COPY stock-broker /src/stock-broker
COPY stock-exchange /src/stock-exchange

COPY OpenJDK21U-jdk_aarch64_linux_hotspot_21.0.2_13.tar.gz /jdk.tar.gz 
RUN tar xzf /jdk.tar.gz -C / && mv /jdk-21.0.2+13 /jdk && rm /jdk.tar.gz

RUN echo 'export JAVA_HOME=/jdk' >> ~/.bashrc
RUN echo 'export PATH=$PATH:$JAVA_HOME/bin' >> ~/.bashrc

RUN . ~/.bashrc && cd /src/stock-broker && ./mvnw clean package
RUN . ~/.bashrc && cd /src/stock-exchange && ./mvnw clean package

RUN cd ~ && tar czf /m2.tar.gz .m2/
RUN cd / && tar czf /jdk.tar.gz jdk/

FROM alpine:latest

COPY --from=builder /m2.tar.gz /m2.tar.gz
COPY --from=builder /jdk.tar.gz /jdk.tar.gz
