#!/bin/bash

set -e

cd "$(dirname $0)"
BASE_PATH="$(pwd)" docker-compose $@
