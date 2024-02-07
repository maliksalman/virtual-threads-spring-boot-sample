#!/bin/bash

local_registry=registry.lan.salmanmalik.me

docker pull prom/prometheus:v2.49.1
docker tag prom/prometheus:v2.49.1 ${local_registry}/prom/prometheus:v2.49.1
docker push ${local_registry}/prom/prometheus:v2.49.1

docker pull grafana/grafana:10.3.1
docker tag grafana/grafana:10.3.1 ${local_registry}/grafana/grafana:10.3.1
docker push ${local_registry}/grafana/grafana:10.3.1
