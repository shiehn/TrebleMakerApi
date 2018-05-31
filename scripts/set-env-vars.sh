#!/usr/bin/env bash

export TM_API_ROOT_DIR=/TrebleMakerApi

export TM_API_USER=treble
export TM_API_PASS=maker

export TM_DB_CONN="jdbc:mysql://localhost:3333/treblemakerdb?autoReconnect=true&maxReconnects=360&initialTimeout=2"
export TM_DB_USER=treble
export TM_DB_PASS=maker

export TM_API_PORT=7777

export TM_API_S3_BUCKET=https://s3-us-west-2.amazonaws.com/[YOUR-BUCKET]
export AWS_BUCKET_NAME=[YOUR-BUCKET]
export AWS_ACCESS_KEY_ID=[YOUR-ACCESS-KEY]
export AWS_SECRET_ACCESS_KEY=[YOUR-SECRET-KEY]

export TM_API_LOG_DIR=logs
