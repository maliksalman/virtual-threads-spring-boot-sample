#!/bin/bash

if [[ -z $1 ]]; then
  export SPRING_PROFILES_ACTIVE=prometheus
else
  export SPRING_PROFILES_ACTIVE=prometheus,$1
fi

java -Xmx2G -Xms2G -jar stock-broker/target/stock-broker-1.0.jar