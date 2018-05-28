#!/usr/bin/env bash
export AWS_ACCESS_KEY_ID=mock-key
export AWS_SECRET_ACCESS_KEY=mock-secret
export PYTHONHTTPSVERIFY=0

nohup dynalite --port 4570 &
nohup kinesalite --port 4568 --createStreamMs 100 --updateStreamMs 100 &

aws kinesis create-stream --stream-name ${STREAM_NAME} --shard-coun 1 --endpoint http://localhost:4568 --region us-east-1 --no-verify-ssl

#exec bash