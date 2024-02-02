#!/bin/bash

local_registry=127.0.0.1:5001

docker build -t ${local_registry}/virtual-threads-guide-stuff:1.0 .
docker push ${local_registry}/virtual-threads-guide-stuff:1.0

