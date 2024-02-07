#!/bin/bash

cd stock-broker
./mvnw clean package

cd ../stock-exchange
./mvnw clean package
