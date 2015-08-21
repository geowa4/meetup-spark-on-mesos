#!/usr/bin/env bash

set -e

vagrant destroy -f
vagrant up --no-provision
vagrant provision master1
