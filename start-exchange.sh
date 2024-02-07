#!/bin/bash

if [[ -z $1 ]]; then
  export SPRING_PROFILES_ACTIVE=prometheus
else
  export SPRING_PROFILES_ACTIVE=prometheus,$1
fi

java -Xmx1G -Xms1G -jar stock-exchange/target/stock-exchange-1.0.jar