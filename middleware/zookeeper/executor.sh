#!/usr/bin/env bash
. /usr/local/Cellar/zookeeper/3.4.13/libexec/bin/zkEnv.sh

export CLASSPATH="target/classes/com/demo/watcher:$CLASSPATH"
mkdir -p data
java com.demo.watcher.Executor "$@"
