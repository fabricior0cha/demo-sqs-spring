#!/bin/sh

sleep 5

aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name demo
aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name demo_dlq

DLQ_URL=$(aws --endpoint-url=http://localstack:4566 sqs get-queue-url --queue-name demo_dlq --query QueueUrl --output text)
DLQ_ARN=$(aws --endpoint-url=http://localstack:4566 sqs get-queue-attributes --queue-url "$DLQ_URL" --attribute-names QueueArn --query Attributes.QueueArn --output text)

aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name demo_queue_with_dlq


WITH_DLQ_URL=$(aws --endpoint-url=http://localstack:4566 sqs get-queue-url --queue-name demo_queue_with_dlq --query QueueUrl --output text)

aws --endpoint-url=http://localstack:4566 sqs set-queue-attributes \
  --queue-url "$WITH_DLQ_URL" \
  --attributes '{
                  "RedrivePolicy": "{\"deadLetterTargetArn\":\"'"$DLQ_ARN"'\",\"maxReceiveCount\":\"2\",\"visibilityTimeout\":\"5\"}"
              }'
