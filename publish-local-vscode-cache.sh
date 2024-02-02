#!/bin/bash

platform_arch=arm64
vscode_ver=0.25.14
local_registry=127.0.0.1:5001

docker pull ghcr.io/vmware-tanzu-learning/vscode-java-tools-${platform_arch}-files:${vscode_ver}
docker tag ghcr.io/vmware-tanzu-learning/vscode-java-tools-${platform_arch}-files:${vscode_ver} ${local_registry}/vscode-java-tools-${platform_arch}-files:${vscode_ver}
docker 
docker push ${local_registry}/vscode-java-tools-${platform_arch}-files:${vscode_ver}

