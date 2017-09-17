#!/usr/bin/env bash
export AWS_ACCESS_KEY_ID=mock-key
export AWS_SECRET_ACCESS_KEY=mock-secret
export PYTHONHTTPSVERIFY=0

nohup dynalite --ssl --port 7000 &
nohup kinesalite --ssl --port 7001 --createStreamMs 100 --updateStreamMs 100 &

aws kinesis create-stream --stream-name ${STREAM_NAME} --shard-coun 1 --endpoint https://localhost:7001 --region us-east-1 --no-verify-ssl
