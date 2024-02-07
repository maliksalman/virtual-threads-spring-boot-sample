#!/bin/bash

TO_ADD=100
for i in {1..30}; do
  echo "`date` - Adding ${TO_ADD} symbols to exchange(s)"
  curl -s -X PUT http://localhost:9091/exchange/symbols/${TO_ADD}
  curl -s -X PUT http://localhost:9092/exchange/symbols/${TO_ADD}
  sleep 15
done